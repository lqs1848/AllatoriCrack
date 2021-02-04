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
		/*
		 * param 参数大概是:
		 * c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/
		 * lang/Object; 这样的 字符串 如果版本有变动 要判断 传入的参数和传出的参数是否变动了
		 */
		// param.indexOf("AllatoriDecryptString")
		
		boolean isField = false;
		String fieldParam = "";
		//判断是加密方法还是在加密变量
		//继承的方法名称不会被加密 但是继承的变量被加密名称与父类不同会出错
		//尽量保持变量名称一致
		String[] params = param.split("&");
		if(params[2].startsWith("L")) {
			isField = true;
			fieldParam = params[1] + params[2];
		}
		
		
		String res = mark.get(isField ? fieldParam : param);
		if (res != null)
			return res;

		String className = getClassName(param);
		Set<String> nameSet = repMap.getOrDefault(className, new HashSet<>());

		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		do {
			String str = seedList.get(ran.nextInt(seedList.size()));
			for (int i = 0; i < len; i++)
				sb.append(str.charAt(ran.nextInt(str.length())));
			res = sb.toString();
		} while (nameSet.contains(res));//防止方法名或变量名称重复

		nameSet.add(res);
		repMap.put(className, nameSet);
		mark.put(isField ? fieldParam : param, res);

		return res;
	
	}// method 
}// class