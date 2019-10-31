package com.huaixuan.network.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5校验码生成工具
 * 
 */
public class Md5Util
{
	/**
	 * 生成md5校验码
	 * @param srcContent 需要加密的数据
	 * @return 加密后的md5校验码。出错则返回null。
	 */
	public static String makeMd5Sum(byte[] srcContent)
	{
		if (srcContent == null)
		{
			return null;
		}

		String strDes = null;

		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(srcContent);
			strDes = bytes2Hex(md5.digest()); // to HexString
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
		return strDes;
	}

	private static String bytes2Hex(byte[] byteArray)
	{
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
		{
			if(byteArray[i] >= 0 && byteArray[i] < 16)
			{
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}

	public static void main(String[] args)
	{
		System.out.println(Md5Util.makeMd5Sum("adddsfdssf".getBytes()));
	}
}
