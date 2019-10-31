package com.huaixuan.network.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.hundsun.bible.acctrans.util.BalanceMd5;

import java.io.StringReader;

//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.KeywordAnalyzer;
//import org.apache.lucene.analysis.SimpleAnalyzer;
//import org.apache.lucene.analysis.StopAnalyzer;
//import org.apache.lucene.analysis.Token;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.WhitespaceAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;



public class TestUtil {
//    private AdDao adDao ;
    private BalanceMd5          balanceMd5;
    Cipher ecipher;

    Cipher dcipher;
//    //�ܳ�·��
//
//
//
    private String keyPath = null;
//
//	public AdDao getAdDao() {
//		return adDao;
//	}
//
//
//	public void setAdDao(AdDao adDao) {
//		this.adDao = adDao;
//	}
//
//
//	public void testDaoNotNull() throws Exception {
//		assertNotNull(adDao);
//	}
//	
//	public void testAddAd() throws Exception {
//		BigDecimal marginBig = new BigDecimal(""+(14.0/10)).setScale(0, BigDecimal.ROUND_HALF_UP);
//		int margin = marginBig.intValue();
//    	System.out.println(margin);
//    }
//
	public void testAddsign() throws Exception {
		balanceMd5 = new BalanceMd5();
		String checkSign = balanceMd5.getBalanceMd5Value(14042, 9865432);
		System.out.println(checkSign);
	}

	public  void testGain( ) {

        String str = "zhong�й���%";
        try {
        	TestUtil des = new TestUtil();
            des.setKeyPath("F:\\desKey.key");
            SecretKey se = des.generateKey();
            des.saveKey(des.getKeyPath(),se);
            System.out.println("MD5 KEY���");
            des.init(des.loadKey(des.getKeyPath()));
            byte[] utf8 = str.getBytes();
            byte[] enc = des.encode(utf8);
            String s = des.byte2hex(enc);
            System.out.println("���ܽ��:" + s);
            System.out.println("���ܽ��:" +   new String(enc,"UTF-8"));

//            byte[] enc =   "02BB6C".getBytes();
//            byte[] utf = des.decode(enc);
//            System.out.println("���:" + new String(utf,"UTF-8"));


            String str1 = encryptToDES(des.loadKey(des.getKeyPath()), "zhong�������갡�Ǵ���ٷ֣�%����");
            System.out.println("ʹ��des������ϢHelloΪ:" + str1);
            // ʹ������ܳ׽���
            String str2 = decryptByDES(des.loadKey(des.getKeyPath()), str1);
            System.out.println("���ܺ�Ϊ��" + str2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


	public String encryptToDES(SecretKey key, String info) {
        // ���� �����㷨,���� DES,DESede,Blowfish
        String Algorithm = "DES";
        // ��������������� (RNG),(���Բ�д)
        SecureRandom sr = new SecureRandom();
        // ����Ҫ���ɵ�����
        byte[] cipherByte = null;
        try {
            // �õ�����/������
            Cipher c1 = Cipher.getInstance(Algorithm);
            // ��ָ������Կ��ģʽ��ʼ��Cipher����
            // ����:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
            c1.init(Cipher.ENCRYPT_MODE, key, sr);
            // ��Ҫ���ܵ����ݽ��б��봦��,
            cipherByte = c1.doFinal(info.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // �������ĵ�ʮ��������ʽ
        return byte2hex(cipherByte);
    }


	 public String decryptByDES(SecretKey key, String sInfo) {
	        // ���� �����㷨,
	        String Algorithm = "DES";
	        // ��������������� (RNG)
	        SecureRandom sr = new SecureRandom();
	        byte[] cipherByte = null;
	        try {
	            // �õ�����/������
	            Cipher c1 = Cipher.getInstance(Algorithm);
	            // ��ָ������Կ��ģʽ��ʼ��Cipher����
	            c1.init(Cipher.DECRYPT_MODE, key, sr);
	            // ��Ҫ���ܵ����ݽ��б��봦��
	            cipherByte = c1.doFinal(hex2byte(sInfo));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        // return byte2hex(cipherByte);
	        return new String(cipherByte);
	    }

	 public String encryptToDES(String info) {
			try {
				SecretKey key = loadKey();
				  // ���� �����㷨,���� DES,DESede,Blowfish
		        String Algorithm = "DES";
		        // ��������������� (RNG),(���Բ�д)
		        SecureRandom sr = new SecureRandom();
		        // ����Ҫ���ɵ�����
		        byte[] cipherByte = null;
		        try {
		            // �õ�����/������
		            Cipher c1 = Cipher.getInstance(Algorithm);
		            // ��ָ������Կ��ģʽ��ʼ��Cipher����
		            // ����:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
		            c1.init(Cipher.ENCRYPT_MODE, key, sr);
		            // ��Ҫ���ܵ����ݽ��б��봦��,
		            cipherByte = c1.doFinal(info.getBytes());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        // �������ĵ�ʮ��������ʽ
		        return byte2hex(cipherByte);
			} catch (Exception e1) {
//				log.error("������ܳ���",e1);
			}
			return null;
	    }


		 public String decryptByDES(String sInfo) {
			 	try {
					SecretKey key = loadKey();
			        // ���� �����㷨,
			        String Algorithm = "DES";
			        // ��������������� (RNG)
			        SecureRandom sr = new SecureRandom();
			        byte[] cipherByte = null;
			        try {
			            // �õ�����/������
			            Cipher c1 = Cipher.getInstance(Algorithm);
			            // ��ָ������Կ��ģʽ��ʼ��Cipher����
			            c1.init(Cipher.DECRYPT_MODE, key, sr);
			            // ��Ҫ���ܵ����ݽ��б��봦��
			            cipherByte = c1.doFinal(hex2byte(sInfo));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        // return byte2hex(cipherByte);
			        return new String(cipherByte);
			 	} catch (Exception e1) {
//					log.error("������ܳ���",e1);
				}
				return null;
		    }



		/**
	     * ��������ת��Ϊ16�����ַ���
	     *
	     * @param b
	     *            �������ֽ�����
	     * @return String
	     */
		public String byte2hex(byte[] b) {
	        String hs = "";
	        String stmp = "";
	        for (int n = 0; n < b.length; n++) {
	            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	            if (stmp.length() == 1) {
	                hs = hs + "0" + stmp;
	            } else {
	                hs = hs + stmp;
	            }
	        }
	        return hs.toUpperCase();
	    }


		public byte uniteBytes(String src0, String src1) {
	        byte b0 = Byte.decode("0x" + src0).byteValue();
	        b0 = (byte) (b0 << 4);
	        byte b1 = Byte.decode("0x" + src1).byteValue();
	        byte ret = (byte) (b0 | b1);
	        return ret;
	    }

	    public byte[] hex2byte(String hs) // �ַ���ת������
	    {
	        int m = 0, n = 0;
	        int l = hs.length() / 2;
	        System.out.println(l);
	        byte[] ret = new byte[l];
	        for (int i = 0; i < l; i++) {
	            m = i * 2 + 1;
	            n = m + 1;
	            ret[i] = uniteBytes(hs.substring(i * 2, m), hs.substring(m, n));
	        }
	        return ret;

	    }

	    /**
	     * ���ܳ��ļ�����KEY
	     * @param filename
	     * @return
	     * @throws Exception
	     */
	    public SecretKey loadKey() throws Exception {
	    	//System.out.println("filename:" + filename);
	        //ʵ�����ļ�����

//	        this.setKeyPath("F:\\desKey.key");
//	        File file = new File(this.getKeyPath());
//	        if (this.getKeyPath() == null || this.getKeyPath().equals("")) {
//	            throw new NullPointerException("��Ч���ļ�·��");
//	        }
//	        long len = file.length();

//	        byte[] bytes = new byte[(int) len];
	        byte[] bytes = {-113,-73,17,112,111,-2,-47,-83,74,46,36,70,44,-45,-52,-29};
//	        BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream(file));
//	        //��ȡ�ܳ��ļ�
//	        int r = bufferedInputStream.read(bytes);
//	        if (r != len)
//	            throw new IOException("��ȡ�ļ�����ȷ");
//	        bufferedInputStream.close();
	        byte rawKeyData[] = bytes;

	        // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
	        DESKeySpec dks = new DESKeySpec(rawKeyData);

	        // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����
	        // һ��SecretKey����
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey key = keyFactory.generateSecret(dks);

	        return key;
	    }


	public void init(SecretKey key) {
        try {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }

	/**
     * ����KEY
     * @return
     * @throws Exception
     */
    public SecretKey generateKey() throws Exception {
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        this.saveKey(this.getKeyPath(), key);
        return key;
    }
    /**
     * ����
     */
    public byte[] decode(byte[] dec) throws Exception {
        byte[] utf8 = dcipher.doFinal(dec);
        return utf8;
    }
    /**
     * ����
     */
    public byte[] encode(byte[] utf8) throws Exception {
        byte[] enc = ecipher.doFinal(utf8);
        return enc;
    }
    /**
     * ���ܳ��ļ�����KEY
     * @param filename
     * @return
     * @throws Exception
     */
    public SecretKey loadKey(String filename) throws Exception {
        System.out.println("filename:" + filename);
        //ʵ�����ļ�����
//        File file = new File(filename);
//        if (filename == null || filename.equals("")) {
//            throw new NullPointerException("��Ч���ļ�·��");
//        }
//        long len = file.length();

        byte[] bytes = {-113,-73,17,112,111,-2,-47,-83,74,46,36,70,44,-45,-52,-29};
//        BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream(file));
        //��ȡ�ܳ��ļ�
//        int r = bufferedInputStream.read(bytes);
//        if (r != len)
//            throw new IOException("��ȡ�ļ�����ȷ");
//        bufferedInputStream.close();
//        String keydd ="A";
//        byte[] bytes = hex2byte(keydd);
        byte rawKeyData[] = bytes;

        // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
        DESKeySpec dks = new DESKeySpec(rawKeyData);

        // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����
        // һ��SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);

        return key;
    }
    /**
     * ���ܳ״浽�ܳ��ļ���
     * @param filename
     * @param key
     * @throws Exception
     */
    public void saveKey(String filename, Key key) throws Exception {
        System.out.println("����filename:" + filename);
        File file = new File(filename);
        BufferedOutputStream pubout = new BufferedOutputStream(new FileOutputStream(filename));
        byte[] keyt = key.getEncoded();

        java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
        md.update( keyt );
        byte tmp[] = md.digest();
       // System.out.println(Converts.);
        pubout.write(tmp);
        pubout.close();
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

//    public void testGetAd() throws Exception {
//    	Ad ad =  adDao.getAd(10003L);
//        assertNotNull(ad);
//    }
//
//    
//    public void testGetAds() throws Exception {
//    	List<Ad> adList =  adDao.getAds();
//    	log.info("adList.size()"+adList.size());
//        assertTrue(adList.size()>0);
//
//    }
//
//   
//    public void testEditAd() throws Exception {
//    	Ad ad = adDao.getAd(10003L);
//
//    	 ad.setAdPositionId(111);
//    	 adDao.editAd(ad);
//    	 setComplete();
//         endTransaction();
//         startNewTransaction();
//
//         ad = null;
//    	 ad = adDao.getAd(10003L);
//    	 assertNotNull(ad);
//         assertEquals(111, ad.getAdPositionId());
//
//    }
//
//   
//    public void testAddAndRemoveAd() throws Exception {
//    	Ad ad = new Ad();
//    	ad.setAdName("nihao");
//    	ad.setAdPositionId(3);
//    	ad.setEndTime(new Date());
////    	ad.setGmtCreate(new Date());
////    	ad.setGmtModify(new Date());
//    	ad.setLinkEmail("163@163.com");
//    	ad.setLinkMan("hello");
//    	ad.setLinkPhone("0571-52233333");
//    	ad.setStartTime(new Date());
//    	ad.setStatus(Ad.Status_open);
//
//    	Long id=adDao.addAd(ad);
//    	System.out.println(id);
//    	Ad ad2 =  adDao.getAd(id);
//        assertNotNull(ad2);
//
//        setComplete();
//        endTransaction();
//
////        ad2.setStatus(Ad.Status_delete);
////        dao.removeAd(ad2);
////        Ad ad3 =  dao.getAd(id);
////        assertNotNull(ad3);
//    }

//    public static void analyze(Analyzer analyzer, String text) throws Exception {
//    	   System.out.println("�ִ�����" + analyzer.getClass());
//    	   TokenStream tokenStream = analyzer.reusableTokenStream("", new StringReader(text));
//    	   
//	   		Token reuse = new Token();
//	   		Token t = null;
//		   while ((t = tokenStream.next(reuse)) != null)
//		   {
//			   System.out.println(t.term());
//		   }
//		   tokenStream.close();
//
//    	   
//    	}

    	public static void testAddAndRemoveAd() throws Exception {
//    	   String enText = "China is a great country!";
//    	   String chText = "���죬һ��������󣬿����쳣�����£�";
//    	   SimpleAnalyzer analyzer1 = new SimpleAnalyzer();
//    	   analyze(analyzer1,enText);
//    	   analyze(analyzer1,chText);
//    	   StopAnalyzer analyzer2 = new StopAnalyzer();
//    	   analyze(analyzer2,enText);
//    	   analyze(analyzer2,chText);
//    	   StandardAnalyzer analyzer3 = new StandardAnalyzer();
//    	   analyze(analyzer3,enText);
//    	   analyze(analyzer3,chText);//������з�
//    	   WhitespaceAnalyzer analyzer4 = new WhitespaceAnalyzer();
//    	   analyze(analyzer4,enText);
//    	   analyze(analyzer4,chText);
//    	   KeywordAnalyzer analyzer5 = new KeywordAnalyzer();
//    	   analyze(analyzer5,enText);
//    	   analyze(analyzer5,chText);
    		

//    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    	    Calendar calender = Calendar.getInstance();
//    	    calender.setTime(new Date());
//    	    calender.add(Calendar.MONTH, 12);
//    	    simpleDateFormat.format(calender.getTime());
//    	    String ss = simpleDateFormat.format(calender.getTime()).toString(); 
//    	    System.out.println(ss);
//    	    System.out.println(calender.getTime());
    		
    		
//    		InetAddress localhost = InetAddress.getLocalHost();
//            String hostName = localhost.getHostName();
//            String hostAddress = localhost.getHostAddress();
//    		
//            System.out.println(hostName);
//            System.out.println(hostAddress);
    	   }

}


