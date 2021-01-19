package net.moofMonkey.eye.modules;

import java.util.ArrayList;

import javassist.CtMethod;

public class Utils {
	public static boolean dump = false;
	public static boolean debug = false;

	public static ArrayList<CtMethod> get(CtMethod[] methods, String methodName, String signature) {
		ArrayList<CtMethod> mdl = new ArrayList<CtMethod>();
		boolean f = false;

		for (CtMethod md : methods) {
			if (dump)
				if (signature == null || md.getSignature().equals(signature))
					System.out.println(md.getSignature());
			if (md.getName().equals(methodName) && (signature == null || md.getSignature().equals(signature))) {
				f = true;
				mdl.add(md);
			}
		}

		if (!f)
			return null;
		else
			return mdl;
	}

	public static boolean isContains(byte[] bytes, String toSearch) {
		String s = new String(bytes);

		if (s.indexOf(toSearch) > -1)
			return true;

		return false;
	}
}
