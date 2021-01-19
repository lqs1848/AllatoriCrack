package net.moofMonkey.eye.modules;

import java.util.List;
import java.util.Random;

public class ClassNameGen extends Thread {
	public static List<String> list = List.of("li","Ii","iL","Ll","Oo","oO","0o","Pp","xX","0io");

	public static String get(String str) {
		if(str.indexOf("ObjectsSerializeUtil")!=-1) {
			System.out.println("ClassNameGen.get:"+str);
			getCaller();
		}
		return String.valueOf(Math.abs(str.hashCode()));
		/*
		int c = 4;
		int len = str.hashCode();
		String name = "";
		String alphabet = list.get(len % list.size());
		try {
			int i = c;
			while (true) {
				if (i == 0) break;
				name += alphabet.charAt(rand.nextInt(alphabet.length()));
				i--;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		System.gc();

		return name;
		*/
	}
	
	
	public static void getCaller()
	{
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++)
		{
			StackTraceElement s = stack[i];
			System.out.format(" ClassName:%d\t%s\n", i, s.getClassName());
			System.out.format("MethodName:%d\t%s\n", i, s.getMethodName());
			System.out.format("  FileName:%d\t%s\n", i, s.getFileName());
			System.out.format("LineNumber:%d\t%s\n\n", i, s.getLineNumber());
		}
	}

	
}
