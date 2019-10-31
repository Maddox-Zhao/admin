package com.huaixuan.network.common.util.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.huaixuan.network.common.util.Md5Util;

/**
 * ���̳Ƕ���Ӆ�
 * 
 * 
 */
public class Contants {

    /**
     * QQ�����175008924 �����1714178222 �Ƹ�ͨ���룺paipai
     * spid��17230000e903b9a7324cba623c10b412
     * seckey��17a33nt4qccnbafe4lvcpsrqyn1fh5gc
     * token��1766f6320f2776aa324c8cb0f4a832a2
     */
    // =================���Ե�QQ���Լ���Ȩ�Δ�17==========
    // �Ϸ���QQ��17
    public static final Number UIN      = 855008924;
    // token��uin�A17�A17��Ӧ������У�鵥���û�����ʵ���
    public static final String TOKEN    = "9c66f6320f2776aa324c8cb0f4a832a2";
    // spid��Ӧ�ڽ��뷽ƽ̨�������ǁA17���̼�ϵͳ��A17��������Ӧ�ã�API�ӿڵ�Ȩ�޸�spid��Ӧ�󶨣�17�Ǹ�uin��
    public static final String SPID     = "29230000e903b9a7324cba623c10b412";
    // ��17�з��ļ��ֶ�name��value��У��17����ֹ��Ҫ���ݱ���;�۔d17
    public static final String SECKEY   = "5xa33nt4qccnbafe4lvcpsrqyn1fh5gc";

    /* =================����API�ӿڵ�ַ ================= */
    public static final String URL      = "http://api.paipai.com";

    public static final String CHAR_SET = "utf-8";

    public static final String FORMAT   = "xml";

    public static final String PUREDATA = "0";

    /**
     * ��乲�õ�����17
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
     * �õ��@17�յ�URL�����ַ
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
     * ƴ��URL
     * @param url
     * @param charset
     * @param signParams
     * @param sign_str
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
     * �޸�URL��ַ
     * 
     * ���磺http://api.paipai.com/item/addItem.xhtml����requestURLPath="/item/addItem.xhtml "����Ӧ��cmdid="item.addItem"17
     * @param requestURI
     * @return
     */
    private static String getCmd(String requestURI) {
        byte[] uri = requestURI.getBytes();
        int posStart = 0;
        while (uri[posStart] == '/') { // ȥ��Ū17ͨ��'/'
            posStart++;
        }
        int posEnd = posStart;
        int index = posStart;
        while (index < uri.length) { // �滻�м��17'/'
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
     * ����SIGN
     * 
     * 	�������url��http://api.paipai.com/module/action.xhtml?xxx=%E4%B8%AD%E6%96%87&zzz=2&yyy=3&charset=utf-8
    	������xxx=���ģ�zzz=2��yyy=3��charset=utf-8����Ϊcharset=utf-8��xxx=���ģ�yyy=3��zzz=217
    	�@17��ƴװ�������ַ����͕L17"module.actioncharsetutf-8xxx����yyy3zzz2"17
    	sign=md5("module.actioncharsetutf-8xxx����yyy3zzz2" + secretKey)17
    	���secretKey="33230000e9030ghjtsfgh0564a0ce621"����sign="43cc81fd5a86131ae10052e03ce008d3"17
     * @param cmdId
     * @param signParams
     * @param secretKey
     * @param charset
     * @param debug
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static String makeSign(String cmdId, TreeMap<String, String> signParams,
                                   String secretKey, String charset, boolean debug)
                                                                                   throws UnsupportedEncodingException,
                                                                                   IOException {
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

    public static String makeSignTwo(String cmdId, TreeMap<String, String[]> signParams,
                                     String secretKey, String charset, boolean debug)
                                                                                     throws UnsupportedEncodingException,
                                                                                     IOException {
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
