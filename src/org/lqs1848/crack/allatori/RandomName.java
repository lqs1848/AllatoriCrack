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
	private static final int len = 6;
	private static List<String> seedList = List.of("Ii", "oO", "Pp", "xX","abcdefghijklmiopqrstuvwxyzABCDEFGHIJKLMIOPQRSTUVWXYZ");

	// 同一个方法固定加密为相同的随机名称
	private static Map<String, String> mark = new HashMap<>();
	// 保证同一个类里面不会有方法名称相同  即使是重载的方法
	private static Map<String, Set<String>> repMap = new HashMap<>();

	/**
	 * 生成随机名称
	 * 
	 * @param param
	 * @return
	 */
	public static String get(String param) {
		//param.indexOf("AllatoriDecryptString")
		
		/*
		 * 参数大概是:
		 * c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/
		 * lang/Object; 这样的 字符串 如果版本有变动 要判断 传入的参数和传出的参数是否变动了
		 */
		String res = mark.get(param);
		if (res != null)
			return res;

		String className = getClassName(param);
		Set<String> methodSet = repMap.getOrDefault(className, new HashSet<>());
		
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		do {
			String str = seedList.get(ran.nextInt(seedList.size()));
			for (int i = 0; i < len; i++)
				sb.append(str.charAt(ran.nextInt(str.length())));
			res = sb.toString();
		} while (methodSet.contains(res));
		
		methodSet.add(res);
		repMap.put(className, methodSet);
		mark.put(param, res);
		return res;
	}// method

	public static String getClassName(String param) {
		try {
			int start = param.lastIndexOf(".") + 1;
			int end = param.indexOf("&");
			return param.substring(start, end);
		} catch (Exception e) {
			System.out.println("analysis class name error:" + param);
		}
		return param;
	}// method 
}// class