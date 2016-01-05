package com.jason.WeiXinDev.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	
	private static String token = "WeiXinVerification";

	public static boolean checkSign(String signature,String timestamp, String nonce) {
		
		String text = null;
		
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			text = bytesToHexstr(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text != null?text.equals(signature.toUpperCase()):false;
	}

	private static String bytesToHexstr(byte[] digest) {
		String Hexstr = "";
		
		for(int i = 0 ; i < digest.length;i++){
			Hexstr += byteToHex(digest[i]);
		}
		return Hexstr;
	}

	private static String byteToHex(byte b) {
		char[] num = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 
		
		char[] temp = new char[2];
		
		temp[0] = num[ (b >>> 4) & 0X0F];
		temp[1] = num[ b & 0X0F];
		
		String hex = new String(temp);
		
		return hex;
	}

}
