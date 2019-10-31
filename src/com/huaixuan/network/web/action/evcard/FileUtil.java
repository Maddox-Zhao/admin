package com.huaixuan.network.web.action.evcard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



/**
 * @author Mr_Yang   2016-12-7 下午12:05:32
 **/

public class FileUtil
{
	public static  String path = "d:/stock_log/";//记录日志
	/**
	 * 
	 * @param fileName 文件名
	 * @param content 内容
	 * @param type zj-追加 cx-重写
	 */
	public static void writeText2File(String fileName,String content,String type)
	{
		if("zj".equals(type)) zhuijiaWenJian(fileName, content);
		else  if("cx".equals(type)) chongxieWenJian(fileName, content);
	}
	
	public static String readFileContent(String fileName)
	{
		File f = new File(path + fileName);
		if(!f.exists()) return "";
		FileReader fr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null)
			{
				sb.append(line);
				sb.append("\r\n");
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
		return sb.toString();
	}
	
	public static void zhuijiaWenJian(String fileName,String content)
	{
		FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(path + fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
	}
	
	
	public static void chongxieWenJian(String fileName,String content)
	{
		File f=new File(path + fileName);
		if(!f.exists()){ 
		   f.getParentFile().mkdirs();// 创建父目录
		   try
		{
			f.createNewFile();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 创建文件自身 
		}
		FileWriter fw=null;
		try{  
		    fw=new FileWriter(f); 
		    fw.write(content);
		    fw.close(); 
		}catch(Throwable e){e.printStackTrace();// 把异常给输出出来
		}finally{
			try
	    	{
					if(fw!=null)
		    		fw.close();
		    } 
			catch(Throwable e)
			{
				e.printStackTrace();
			} 
		 
		}

	}
}
 
