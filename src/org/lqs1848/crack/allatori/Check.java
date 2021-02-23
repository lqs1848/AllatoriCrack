package org.lqs1848.crack.allatori;

import java.io.DataInputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Check {

	public static ClassPool pool = ClassPool.getDefault();
	
	public static void main(String[] args) throws Throwable {
		//校验一下加密是否有错误
		execute();
	}// method main

	public static void execute() throws Throwable {
		ZipFile zf = new ZipFile("obf-test.jar");
		Enumeration<? extends ZipEntry> in = zf.entries();
		byte[] data;
		while (in.hasMoreElements()) {
			ZipEntry ze = in.nextElement();
			if (zf != null && ze != null) {
				DataInputStream dis = new DataInputStream(zf.getInputStream(ze));
				data = new byte[(int) ze.getSize()];
				dis.readFully(data);
				dis.close();

				Set<String> mSet = new HashSet<>();
				if (ze.getName().endsWith(".class")) {
					CtClass cl = pool.makeClass(new java.io.ByteArrayInputStream(data));
					CtMethod[] ms = cl.getDeclaredMethods();
					for (CtMethod fMethod : ms) {
						if(!mSet.add(fMethod.getLongName())) {
							System.out.println("error :" +cl.getSimpleName() + " method:" + fMethod.getLongName());
						}
					} // for
				} //
			} // if
		}//while
		zf.close();
	}// method execute
	
	public static String getParamName(CtClass[] params) {
		StringBuffer sb = new StringBuffer();
		for(CtClass param : params) {
			sb.append(param.getSimpleName());
			sb.append(",");
		}
		return sb.toString();
	}

}// class