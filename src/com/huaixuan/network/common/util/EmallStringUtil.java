package com.huaixuan.network.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hundsun.network.melody.common.util.StringUtil;

public class EmallStringUtil extends StringUtil {
    private static Random randGen           = null;
    private static char[] numbersAndLetters = null;
    private static Object initLock          = new Object();

    /**
     *
     * @param length 随机字符串长度
     * @return
     */

    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                                         + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);

    }

    /**
     * 替换字符串的最后几位
     * @param text 查找的字符串
     * @param with 替换的字符串
     * @param lastLength text被替换的最后几位的长度
     * @return
     */
    public static String replace(String text, String with,int lastLength) {
        StringBuffer buf = new StringBuffer();
        if ((text == null)|| (with == null)||(lastLength<=0)) {
            return text;
        }
        if (text.length() > lastLength) {
            buf.append(text.substring(0,text.length() - lastLength));
            buf.append(with);
        }else{
            buf.append(with);
        }
        return buf.toString();
    }

    public static String abbreviateByBlank(String str,int maxWidth) {
        if (str == null) {
            return null;
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        return str.substring(0,maxWidth);
    }

    public static String escapeSqlFilter(String str) {
        if(str==null)
            return null;
        int length = str.length();

        StringBuffer sql=new StringBuffer();
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '%':
                    sql.append("\\%");
                    break;
                case '?':
                    sql.append("\\?");
                    break;
                case '\'':
                    sql.append("''");
                    break;
                default:
                    sql.append(ch);

            }
        }
        return sql.toString();
    }
    public static String escapeGoodsAttrFilter(String str) {
        if(str==null)
            return null;
        int length = str.length();

        StringBuffer sql=new StringBuffer();
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case ':':
                    break;
                case ';':
                    break;
                default:
                    sql.append(ch);

            }
        }
        return sql.toString();
    }

    public static String encodeUrl(String url){
        return encodeUrl(url,"utf-8");
     }

    public static String encodeUrl(String url,String enc){

        if(!url.contains("?")){
            return url;
        }else {

            StringBuffer urlFinal = new StringBuffer();

            String urlPrefix = substringBefore(url, "?");
            urlFinal.append(urlPrefix);
            urlFinal.append("?");

            String urlParams = substringAfter(url, "?");
            String[] params = urlParams.split("&");

            for(String param : params){

                param = encodeParam(param,enc);

                urlFinal.append(param);
                int count = 1;
                if(count < params.length){
                    urlFinal.append("&");
                    count++;
                }
            }
            return urlFinal.toString();
        }


     }
    
    public static boolean isMobilePhone(String iptStr){
    	String reg = "^((((13|15|18)[0-9]{1}))+\\d{8})$";
    	Pattern p=Pattern.compile(reg); 
    	Matcher match=p.matcher(iptStr);
    	return match.matches();
    }
    
    public static void main(String args[]) {
        String sql="s % ? '";
        System.out.println(EmallStringUtil.escapeSqlFilter(sql));
    }

    private static String encodeParam(String param,String enc){
        try {
            return  substringBefore(param, "=") +"="+ java.net.URLEncoder.encode(substringAfter(param, "="),enc);
        } catch (UnsupportedEncodingException e) {
           return null;
        }
    }
    
    /**
     * UNICODE转换为GBK
     * @param dataStr
     * @return
     */
    public static String decodeUnicode(final String dataStr) {  
        int start = 0;  
        int end = 0;  
        final StringBuffer buffer = new StringBuffer();  
        while (start > -1) {  
            end = dataStr.indexOf("\\u", start + 2);  
            String charStr = "";  
            if (end == -1) {  
                charStr = dataStr.substring(start + 2, dataStr.length());  
            } else {  
                charStr = dataStr.substring(start + 2, end);  
            }  
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。  
            buffer.append(new Character(letter).toString());  
            start = end;  
        }  
        return buffer.toString();  
    }  
}
