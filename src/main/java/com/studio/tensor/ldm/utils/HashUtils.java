package com.studio.tensor.ldm.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtils
{
	public static String getMD5(String str)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} 
		catch (Exception e)
		{
			System.out.println(e);
			return "";
		}
	}
}
