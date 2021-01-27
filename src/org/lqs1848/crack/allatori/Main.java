package org.lqs1848.crack.allatori;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.attribute.FileTime;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Main {

	public static ClassPool pool = ClassPool.getDefault();

	public static void main(String[] args) throws Throwable {
		// 先引用 javassist.jar lib目录下就有
		// 还有 allatori.jar 不引入这个的话 fMethod.getReturnType() 会 ClassNotFind 因为我不是直接用正则去匹配的
		
		
		//把随机名称的类导入到 javassist
		pool.importPackage("org.lqs1848.crack.allatori.RandomName");
		
		execute();
	}// method main

	public static byte[] dump(CtClass cl) throws Throwable {
		byte[] b = null;
		b = cl.toBytecode();
		cl.stopPruning(true);
		return b;
	}// method dump

	public static void execute() throws Throwable {
		File f = new File("allatori_crack.jar");
		if (f.exists())
			f.delete();
		f.createNewFile();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipFile zf = new ZipFile("allatori.jar");
		Enumeration<? extends ZipEntry> in = zf.entries();
		byte[] data;
		while (in.hasMoreElements()) {
			ZipEntry ze = in.nextElement();
			if (zf != null && ze != null) {
				DataInputStream dis = new DataInputStream(zf.getInputStream(ze));
				data = new byte[(int) ze.getSize()];
				dis.readFully(data);
				dis.close();

				ze = modifyEntry(new ZipEntry(ze.getName()));

				if (ze.getName().endsWith(".class")) {
					CtClass cl = pool.makeClass(new java.io.ByteArrayInputStream(data));
					CtMethod[] ms = cl.getDeclaredMethods();
					boolean needRewriteFlag = false;
					for (CtMethod fMethod : ms) {
						if (!fMethod.isEmpty() && fMethod.getName().equals("THIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE")) {
							if (fMethod.getLongName().endsWith("(java.lang.String)")
									&& "String".equals(fMethod.getReturnType().getSimpleName())) {
								//随机名称
								//fMethod.insertAfter("if($1.indexOf(\"AllatoriDecryptString\")==-1&&$_!=null&&!$_.isEmpty()&&$_.equals(\"ALLATORIxDEMO\")){\n" +"$_ = RandomName.get($1);\n" +"}");
								//↑↑↑暂不可用↑↑↑

								//替换掉下面 \"Lqs1848\" 这几个字  替换为 \"你想要取的名字\"
								//注意 不能以 下划线开头 最好类似 ALLATORIxDEMO 这样的结构 大写开头
								//否则生成出来的 jar 在加密某些类的时候会丢失部分逻辑

								//固定名称
								fMethod.insertAfter("if($1.indexOf(\"AllatoriDecryptString\")==-1&&$_!=null&&!$_.isEmpty()&&$_.equals(\"ALLATORIxDEMO\")){\n" + "$_ = \"Lqs1848\";\n" + "}");
								needRewriteFlag = true;
							} else if (fMethod.getLongName().endsWith("()")
									&& "String".equals(fMethod.getReturnType().getSimpleName())) {

								//这里随便替换 看你心情 爱打印什么打印什么
								String code = "String s = \"\";"
										+ "s += \"################################################\\n\"; "
										+ "s += \"#                                              #\\n\"; "
										+ "s += \"#                    替换这里                  #\\n\"; "
										+ "s += \"#                                              #\\n\"; "
										+ "s += \"#           http://blog.lqs1848.top            #\\n\"; "
										+ "s += \"#                                              #\\n\"; "
										+ "s += \"################################################\\n\"; " + "$_ = s; ";

								fMethod.insertAfter(
										"if($_!=null&&!$_.isEmpty()&&$_.indexOf(\"Obfuscation by Allatori\")!=-1){\n" + code + "}");
								needRewriteFlag = true;
							}
						}
					} // for
					if (needRewriteFlag) {
						data = dump(cl);
					} // if
				} //
				out.putNextEntry(ze);
				out.write(data, 0, data.length);
				out.closeEntry();
			} // if
		}//while
		zf.close();

		// 把自定义的随机名称类加入到jar包中
		ZipEntry ze = modifyEntry(new ZipEntry(RandomName.class.getName().replaceAll("\\.", "/") + ".class"));
		data = pool.get(RandomName.class.getName()).toBytecode();
		out.putNextEntry(ze);
		out.write(data, 0, data.length);
		out.closeEntry();

		out.setLevel(9);
		out.close();
	}// method execute

	public static ZipEntry modifyEntry(ZipEntry ze) {
		final long time = 0;
		ze.setTime(time);
		ze = ze.setCreationTime(FileTime.fromMillis(time)).setLastAccessTime(FileTime.fromMillis(time))
				.setLastModifiedTime(FileTime.fromMillis(time));
		return ze;
	}// method modifyEntry

}// class