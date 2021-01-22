package org.lqs1848.crack.allatori;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机名称
 */
public class RandomName {
	private static final int len = 6;
	private static List<String> seedList = List.of("Ii", "oO", "Pp", "xX");
	
	//同一个字符串固定加密为相同的随机名称
	private static Map<String,String> mark = new HashMap<>();

	/**
	 * 生成随机名称
	 * @param param 
	 * @return
	 */
	public static String get(String param) {
		/*
		 * 参数大概是: c.m.common.utils.MapCache&put&(Ljava/lang/Object;Ljava/lang/Object;J)Ljava/lang/Object;
		 * 这样的 字符串
		 * 如果版本有变动 要判断 传入的参数和传出的参数是否变动了
		 * 
		 */
		String res = mark.get(param);
		if(res!=null) return res;
		
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		String str = seedList.get(ran.nextInt(seedList.size()));
		for (int i = 0; i < len; i++)
			sb.append(str.charAt(ran.nextInt(str.length())));
		res = sb.toString();
		mark.put(param, res);
		return res;
	}// method
}// class