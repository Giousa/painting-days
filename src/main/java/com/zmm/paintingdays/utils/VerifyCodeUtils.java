package com.zmm.paintingdays.utils;

public class VerifyCodeUtils {

	public static String getCode() {
		
		return (int)((Math.random()*9+1)*100000)+"";
	}
}
