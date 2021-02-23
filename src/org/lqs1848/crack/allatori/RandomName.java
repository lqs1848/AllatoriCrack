package org.lqs1848.crack.allatori;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 随机名称
 */
public class RandomName {
	private static final int chaosLen = 6;
	private static final String alp = "abcdefghijklmiopqrstuvwxyzABCDEFGHIJKLMIOPQRSTUVWXYZ";
	private static List<String> seedList = List.of("Ii", "oO", "Pp", "xX", alp);

	// 同一个方法固定加密为相同的随机名称
	private static Map<String, String> mark = new HashMap<>();
	// 保证同一个类里面不会有方法名称相同 即使是重载的方法
	private static Map<String, Set<String>> repMap = new HashMap<>();
	// 保证类名不重复 即使类不在同一个包下
	private static Set<String> classNameSet = new HashSet<>();

	/**
	 * 生成随机名称
	 * 
	 * @param pendingStr
	 * @param originalEncryptStr
	 * @return
	 */
	public static String get(String pendingStr, String originalEncryptStr) {
		/*
		 * param 参数大概是:
		 * c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/
		 * lang/Object; 这样的 字符串 如果版本有变动 要判断 传入的参数和传出的参数是否变动了
		 */
		// param.indexOf("AllatoriDecryptString")

		// 忽略加密
		if (pendingStr.equals(originalEncryptStr)) {
			//System.out.println("忽略:"+pendingStr);
			//这里应该要生成一个忽略列表
			//otherAllatori 中对忽略列表 包名前缀相同的项目 加密时 前缀包名不能加密
			return originalEncryptStr;
		}

		return "ALLATORIxDEMO".equals(originalEncryptStr) ? ALLATORIxDEMO(pendingStr, originalEncryptStr)
				: otherAllatori(pendingStr, originalEncryptStr);
	}// method

	/**
	 * 修改类名和包名
	 */
	private static String otherAllatori(String pendingStr, String originalEncryptStr) {
		
		//Bean 名称如果可以重复就不要调用这个方法
		//调用此处已知问题 会导致
		//return originalEncryptStr;
		
		// 改变包名和类名的动作
		if (pendingStr.indexOf("&") == -1 && pendingStr.indexOf("$") == -1) {
			/*
			 * 原始加密内容 
			 * org.lqs1848.allatori.test.config.ReportConfig -> org.lqs1848.allatori.test.y.D
			 * org.lqs1848.allatori.test.interceptor.ReplaceTableInterceptor -> org.lqs1848.allatori.test.h.H
			 * 
			 * 这个不能加密 加密了在boot项目中 会导致 加密类存放位置错误 比如原始路径为 BOOT-INF.classess.org.xxx.xxx 变到
			 * org.xxx.xxx 丢失 BOOT-INF.classess 路径
			 * org.lqs1848.allatori.test.interceptor.ReplaceTableInterceptor$1 -> 不加密
			 */
			// 尾部的 $1 是 Allatori用于解密混淆字符串的解密类
			if (originalEncryptStr.matches("[a-z0-9\\.]*\\.[a-z]{1}\\.[a-zA-Z]{1}") 
					|| (originalEncryptStr.matches("[a-z0-9\\.]*\\.[a-zA-Z]{1}") && isClassName(getClassName(pendingStr)))) {

				String res = mark.get(pendingStr);
				if (res != null) return res;
				String className;
				do {
					className = chaos();
				} while (classNameSet.contains(className));
				res = originalEncryptStr.substring(0, originalEncryptStr.length() - 1) + className;
				mark.put(pendingStr, res);
				classNameSet.add(className);

				return res;
			}
		} // if 是否加密包名和类名
		return originalEncryptStr;
	}// method

	/**
	 * 修改 allatori 中 被加密为 ALLATORIxDEMO 的加密字符串
	 * 
	 * 主要修改方法和变量名称
	 */
	private static String ALLATORIxDEMO(String pendingStr, String originalEncryptStr) {
		// 将所有
		// 方法名相同 参数相同的方法 加密为相同的字符串
		// 变量名相同 变量类型相同 也加密为相同的字符串
		String[] params = pendingStr.split("&");
		pendingStr = params[1] + params[2];

		String res = mark.get(pendingStr);
		if (res != null) return res;

		String className = getClassName(pendingStr);
		Set<String> nameSet = repMap.getOrDefault(className, new HashSet<>());

		do {
			res = chaos();
		} while (nameSet.contains(res));

		nameSet.add(res);
		repMap.put(className, nameSet);
		mark.put(pendingStr, res);

		return res;
	}// method

	public static String chaos() {
		Random ran = new Random();
		String str = seedList.get(ran.nextInt(seedList.size()));
		return chaos(str, chaosLen);
	}// method chaos
	
	public static String chaos(String str, int len) {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++)
			sb.append(str.charAt(ran.nextInt(str.length())));
		return sb.toString();
	}// method chaos

	public static String getClassName(String param) {
		try {
			int x = param.indexOf("&");
			int start = param.lastIndexOf(".") + 1;
			int end = x != -1 ? x : param.length();
			return param.substring(start, end);
		} catch (Exception e) {
			System.out.println("analysis class name error:" + param);
		}
		return param;
	}// method
	
	/**
	 * 判断 包名 和 类名 类名一般都会有大写字母 包名没有大写字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isClassName(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				return true;
			}
		}
		return false;
	}// method
}// class