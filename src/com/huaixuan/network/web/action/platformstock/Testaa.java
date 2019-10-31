package com.huaixuan.network.web.action.platformstock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.web.action.evcard.StringUtil;




/**
 * @author Mr_Yang   2017-1-11 下午04:29:37
 **/

public class Testaa
{
	public static List<String> main(String[] args)
	{
		List<String> hkList = getSku("hk");
		List<String> shList = getSku("sh");
		
		Map<String,String> shMap = new HashMap<String, String>();
		for(String sku : shList)
		{
			shMap.put(sku, sku);
		}
		List<String> list =new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for(String sku : hkList)
		{
			if(shMap.get(sku) == null)
			{
				list.add(sku.trim());
			}
		}
		
		return list;
	}
	
	public static List<String> getSku(String type)
	{
		List<String> list = new ArrayList<String>();
		File f = new File("e:/"+type+".txt");
		if(!f.exists()) return list;
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null)
			{
				if(StringUtil.isNotBlank(line))
				{
					 list.add(line);
				}
			}
		}
		catch (Exception e)
		{
			 
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
					if(fr != null) fr.close();
					if(br != null) br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
 
