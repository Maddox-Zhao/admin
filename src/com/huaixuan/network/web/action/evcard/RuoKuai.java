package com.huaixuan.network.web.action.evcard;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * @author Mr_Yang 2016-11-14 下午03:22:00 http://www.ruokuai.com/ 打码平台
 **/

public class RuoKuai
{
	private String userName = "xt_yangjie";
	private String password = "jieshuai!";;
	public int typeid = 3040; //类型选择 http://www.ruokuai.com/home/pricetype
	private int timeout = 95;
	private String softid = "70738";
	private String softkey = "7bcb7291d28e41fda74cae9ff34d3e5c";
	
	/**
	 * 通过URL获取验证码
	 * @param imageurl
	 * @return
	 */
	public  String getVerCodeByUrl(String imageurl)
	{

		String param = String.format("username=%s&password=%s&typeid=%s&timeout=%s&softid=%s&softkey=%s",userName, password, typeid, timeout, softid, softkey);
		ByteArrayOutputStream baos = null;
		String result = "";
		try
		{
			URL u = new URL(imageurl);
			BufferedImage image = ImageIO.read(u);

			baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			baos.flush();
			byte[] data = baos.toByteArray();
			baos.close();
			result = httpPostImage("http://api.ruokuai.com/create.xml",param, data);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = "未知问题";
		}
		return result;
	}
	
	
	/**
	 * 通过本地文件获取验证码
	 * @param imageurl
	 * @return
	 */
	public  String getVerCodeByFile(String filePath)
	{

		String param = String.format("username=%s&password=%s&typeid=%s&timeout=%s&softid=%s&softkey=%s",userName, password, typeid, timeout, softid, softkey);
		String result = "";
		try
		{
			File file= new File(filePath);
			FileInputStream in = new FileInputStream(file);
			int length = (int)file.length();
			byte[] data = new byte[length];
			in.read(data, 0, length);	 
			result = httpPostImage("http://api.ruokuai.com/create.xml",param, data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = "未知问题";
		}
		return result;
	}
	
	/**
	 * 通过二进制流获取验证码
	 * @param imageurl
	 * @return
	 */
	public  String getVerCodeByByteArray(byte[] data)
	{

		String param = String.format("username=%s&password=%s&typeid=%s&timeout=%s&softid=%s&softkey=%s",userName, password, typeid, timeout, softid, softkey);
		String result = "";
		try
		{
			result = httpPostImage("http://api.ruokuai.com/create.xml",param, data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = "未知问题";
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param url 			请求URL，不带参数 如：http://api.ruokuai.com/register.xml
	 * @param param			请求参数，如：username=test&password=1
	 * @param data			图片二进制流
	 * @return				平台返回结果XML样式 
	 * @throws IOException
	 */
	public  String httpPostImage(String url, String param,
			byte[] data) throws IOException {
		long time = (new Date()).getTime();
		URL u = null;
		HttpURLConnection con = null;
		String boundary = "----------" + MD5(String.valueOf(time));
		String boundarybytesString = "\r\n--" + boundary + "\r\n";
		OutputStream out = null;
		
		u = new URL(url);
		
		con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("POST");
		//con.setReadTimeout(95000);   
		con.setConnectTimeout(95000); //此值与timeout参数相关，如果timeout参数是90秒，这里就是95000，建议多5秒
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(true);
		con.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
		
		out = con.getOutputStream();
			
		for (String paramValue : param.split("[&]")) {
			out.write(boundarybytesString.getBytes("UTF-8"));
			String paramString = "Content-Disposition: form-data; name=\""
					+ paramValue.split("[=]")[0] + "\"\r\n\r\n" + paramValue.split("[=]")[1];
			out.write(paramString.getBytes("UTF-8"));
		}
		out.write(boundarybytesString.getBytes("UTF-8"));

		String paramString = "Content-Disposition: form-data; name=\"image\"; filename=\""
				+ "sample.gif" + "\"\r\nContent-Type: image/gif\r\n\r\n";
		out.write(paramString.getBytes("UTF-8"));
		
		out.write(data);
		
		String tailer = "\r\n--" + boundary + "--\r\n";
		out.write(tailer.getBytes("UTF-8"));

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(con
					.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		
		String resultXml = buffer.toString();
		if(resultXml.contains("充值"))
		{
			System.out.println("如果余额不足请充值");
			System.exit(0);
		}
		//成功获取
		if(resultXml != null && resultXml.contains("<Result>"))
		{
			Pattern pattern = Pattern.compile("(<Result>)(.+?)(</Result>)");
			Matcher matcher = pattern.matcher(resultXml);
			while (matcher.find()) {
				resultXml= matcher.group(2);
			}
  
		}
		return resultXml;
	}
	
	
	
	/**
	 * 字符串MD5加密
	 * @param s 原始字符串
	 * @return  加密后字符串
	 */
	private String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
 
	

}
