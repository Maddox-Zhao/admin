package com.huaixuan.network.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 拍怕商城对外接口
 * 
 * @author fanyj
 * 
 */
public class Contants {

	/**
	 * QQ号码：855008924 密码：2314178222 财付通密码：paipai spid：29230000e903b9a7324cba623c10b412
	 * seckey：5xa33nt4qccnbafe4lvcpsrqyn1fh5gc token：9c66f6320f2776aa324c8cb0f4a832a2
	 */
	// =================测试的QQ号以及鉴权参数===========
	// 合法的QQ号
	public static final Number UIN = 855008924;
	// token与uin一一对应，用于校验单个用户的真实身份
	public static final String TOKEN = "9c66f6320f2776aa324c8cb0f4a832a2";
	// spid对应于接入方平台，可能是一个商家系统或一个第三方应用，API接口的权限跟spid对应绑定，而非跟uin绑定
	public static final String SPID = "29230000e903b9a7324cba623c10b412";
	// 所有非文件字段name和value的校验值，防止重要数据被中途篡改
	public static final String SECKEY = "5xa33nt4qccnbafe4lvcpsrqyn1fh5gc";

	/* =================订单API接口地址 ================= */
	public static final String URL = "http://api.paipai.com";

	public static final String CHAR_SET = "utf-8";

	public static final String FORMAT = "xml";

	public static final String PUREDATA = "0";

	/**
	 * 填充共用的属性
	 * 
	 * @param signParams
	 * @return
	 */
	public static TreeMap<String, String> setPublicParams(TreeMap<String, String> signParams) {
		signParams.put("format", Contants.FORMAT);
		signParams.put("pureData", Contants.PUREDATA);
		signParams.put("charset", Contants.CHAR_SET);
		return signParams;
	}

	/**
	 * 得到最终的URL请求地址
	 * 
	 * @param url
	 * @param signParams
	 * @return
	 */
	public static String createNewUrl(String url, TreeMap<String, String> signParams) {
		String sign_str = "";
		try {
			sign_str = makeSign(getCmd(url), signParams, signParams.get("seckey"), CHAR_SET, false);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		signParams.put("sign", sign_str);
		return getUrl(url, CHAR_SET, signParams);
	}

	/*
	 * 拼凑URL
	 * 
	 * @param url
	 * 
	 * @param charset
	 * 
	 * @param signParams
	 * 
	 * @param sign_str
	 * 
	 * @return
	 */
	private static String getUrl(String url, String charset, TreeMap<String, String> signParams) {
		String parameter = "";
		parameter = parameter + URL + url + "?";

		List keys = new ArrayList(signParams.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String value = (String) signParams.get(keys.get(i));
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			try {
				parameter = parameter + keys.get(i) + "=" + URLEncoder.encode(value, charset);
				if (i + 1 < keys.size()) {
					parameter += "&";
				}
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}
		return parameter;
	}

	/*
	 * 修改URL地址
	 * 
	 * 例如：http://api.paipai.com/item/addItem.xhtml，其requestURLPath="/item/addItem.xhtml "，对应的cmdid="item.addItem"。
	 * 
	 * @param requestURI
	 * 
	 * @return
	 */
	private static String getCmd(String requestURI) {
		byte[] uri = requestURI.getBytes();
		int posStart = 0;
		while (uri[posStart] == '/') { // 去掉开通的'/'
			posStart++;
		}
		int posEnd = posStart;
		int index = posStart;
		while (index < uri.length) { // 替换中间的'/'
			if (uri[index] == '/') {
				uri[index] = '.';
			} else if (uri[index] == '.') {
				posEnd = index;
			}
			index++;
		}
		return new String(uri, posStart, posEnd - posStart);
	}

	/*
	 * 生成SIGN
	 * 
	 * 例如访问url：http://api.paipai.com/module/action.xhtml?xxx=%E4%B8%AD%E6%96%87&zzz=2&yyy=3&charset=utf-8
	 * 将参数xxx=中文，zzz=2，yyy=3，charset=utf-8排序为charset=utf-8，xxx=中文，yyy=3，zzz=2。
	 * 最终拼装出来的字符串就是"module.actioncharsetutf-8xxx中文yyy3zzz2"。 sign=md5("module.actioncharsetutf-8xxx中文yyy3zzz2" +
	 * secretKey)。 如果secretKey="33230000e9030ghjtsfgh0564a0ce621"，则sign="43cc81fd5a86131ae10052e03ce008d3"。
	 * 
	 * @param cmdId
	 * 
	 * @param signParams
	 * 
	 * @param secretKey
	 * 
	 * @param charset
	 * 
	 * @param debug
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 * @throws IOException
	 */
	private static String makeSign(String cmdId, TreeMap<String, String> signParams, String secretKey, String charset,
			boolean debug) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byteStream.write(cmdId.getBytes(charset));
		for (String key : signParams.keySet()) {
			byteStream.write(key.getBytes(charset));
			byteStream.write(signParams.get(key).getBytes(charset));
		}
		byteStream.write(secretKey.getBytes(charset));
		byte[] array = byteStream.toByteArray();
		if (debug) {
			System.out.println("Prepare content to encrypt:" + byteStream.toString(charset));
			System.out.print("Hex dump:");
			hexDump(array);
		}

		return Md5Util.makeMd5Sum(array);
	}

	public static String makeSignTwo(String cmdId, TreeMap<String, String[]> signParams, String secretKey,
			String charset, boolean debug) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byteStream.write(cmdId.getBytes(charset));
		for (String key : signParams.keySet()) {
			byteStream.write(key.getBytes(charset));
			for (String value : (String[]) signParams.get(key)) {
				byteStream.write(value.getBytes(charset));
			}
		}
		byteStream.write(secretKey.getBytes(charset));
		byte[] array = byteStream.toByteArray();
		if (debug) {
			System.out.println("Prepare content to encrypt:" + byteStream.toString(charset));
			System.out.print("Hex dump:");
			hexDump(array);
		}

		return Md5Util.makeMd5Sum(array);
	}

	private static void hexDump(byte[] array) {
		StringBuilder output = new StringBuilder();
		for (byte value : array) {
			if (value >= 0 && value < 16) {
				output.append("0");
			}
			output.append(Integer.toHexString(value & 0xFF)).append(' ');
		}
		System.out.println(output.toString());
	}
}
