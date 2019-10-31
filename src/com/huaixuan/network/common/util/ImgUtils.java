package com.huaixuan.network.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ***********************************************
 * 
 * @file: ImgUtils.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.core.utils
 * @class: ImgUtils
 * @description:
 * @author: huangrh
 * @since: Nov 20, 2008-1:15:57 PM
 * @history:
 */
public class ImgUtils
{
	public ImgUtils()
	{
	}

	/**
	 * ��ͼƬurl��ĩβ�����ַ���append����convertAppendToUrl("-append",
	 * "d:/img/url.jpg"),���Ϊ��d:/img/url-append.jpg
	 * 
	 * @param append
	 *            ��URL����Ҫ�ӵ��ַ���
	 * @param url
	 *            URL����
	 * @return ͼƬurl��ĩβ�����ַ���append����URL��
	 */
	public static String convertAppendToUrl(String append, String url)
	{
		if (url != null)
		{
			int index = url.lastIndexOf(".");
			if (index > 0)
			{
				StringBuffer sb = new StringBuffer();
				sb.append(url.substring(0, index));
				sb.append(append);
				sb.append(url.substring(index));
				return sb.toString();
			}
		}
		return null;
	}

	/**
	 * ת��ͼƬ����(imgType�����ǣ� Ҫת����Ŀ������ ����ΪGIF,JPG,PNG��һ��)
	 * 
	 * @param srcImgPath
	 *            ԭ�ļ�ȫ·��
	 * @param imgType
	 *            Ҫת����Ŀ������ ����ΪGIF,JPG,PNG��һ��
	 */
	public synchronized static void convertType(String srcImgPath,
			String imgType) throws Exception
	{
		if (!imgType.equals("GIF") && !imgType.equals("JPG")
				&& !imgType.equals("PNG"))
			return;
		File inputFile = new File(srcImgPath);
		BufferedImage input = ImageIO.read(inputFile);
		srcImgPath = srcImgPath.substring(0, srcImgPath.lastIndexOf("."));
		ImageIO.write(input, imgType, new File(srcImgPath + "." + imgType));
	}

	/**
	 * �ü�ͼƬ
	 * 
	 * @param srcImgPath
	 *            ԭ�ļ�ȫ·��
	 * @param targetImgPath
	 *            �ü�����ļ�����ȫ·��
	 * @param startX
	 *            ��ʼ�ü���x����
	 * @param stratY
	 *            ��ʼ�ü���y����
	 * @param targetWidth
	 *            �ü��Ŀ��
	 * @param targetHeight
	 *            �ü��ĸ߶�
	 */
	public synchronized static void cutOutImg(String srcImgPath,
			String targetImgPath, int startX, int stratY, int targetWidth,
			int targetHeight) throws Exception
	{
		// ȡ��ͼƬ������
		Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) readers.next();
		// ȡ��ͼƬ������
		InputStream source = null;
		ImageInputStream iis = null;
		try
		{
			source = new FileInputStream(srcImgPath);
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			// ����ͼƬ���� Rectangle(���϶���x����, y����, ���ο��, ���θ߶�)
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(stratY, stratY,
					stratY + targetWidth, stratY + targetHeight);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", new File(targetImgPath));
		}
		finally
		{
			// added by yanghb20090116
			try
			{
				if (source != null)
				{
					source.close();
				}
			}
			catch (Exception e)
			{
				throw new Exception("ImgUtils:cutOutImg()source  �رմ���");
			}
			try
			{
				if (iis != null)
				{
					iis.close();
				}
			}
			catch (Exception e)
			{
				throw new Exception("ImgUtils:cutOutImg()iis �رմ���");
			}
		}
	}

	/**
	 * �ü�ͼƬ�������Ͻ�(0,0)��ʼ
	 * 
	 * @param srcImgPath
	 *            ԭ�ļ�ȫ·��
	 * @param targetImgPath
	 *            �ü�����ļ�����ȫ·��
	 * @param endX
	 *            �ü���Ŀ��
	 * @param endY
	 *            �ü���ĸ߶�
	 */

	public synchronized static void cutOutImg(String srcImgPath,
			String targetImgPath, int endX, int endY) throws Exception
	{

		cutOutImg(srcImgPath, targetImgPath, 0, 0, endX, endY);
	}

	/**
	 * ѹ��ͼƬ
	 * 
	 * @param srcImgPath
	 *            ԭ�ļ�ȫ·��
	 * @param targetImgPath
	 *            ѹ������ļ�����ȫ·��
	 * @param targetWidth
	 *            ѹ����Ŀ��
	 * @param targetHeight
	 *            ѹ����ĸ߶�
	 */
	public synchronized static void reduceImg(String srcImgPath,
			String targetImgPath, int targetWidth, int targetHeight)
			throws Exception
	{

		File srcfile = new File(srcImgPath);
		if (!srcfile.exists())
			return;
		Image src = ImageIO.read(srcfile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);

		BufferedImage tag = new BufferedImage(targetWidth, targetHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics gra = tag.getGraphics();
		// ���ñ���ɫ
		gra.setColor(Color.white);
		gra.fillRect(0, 0, targetWidth, targetHeight);
		if (width <= targetWidth && height <= targetHeight)
		{
			//do nothing  20140910
//			gra.drawImage(src.getScaledInstance(width, height,
//					Image.SCALE_SMOOTH), (targetWidth - width) / 2,
//					(targetHeight - height) / 2, null);
			float wh = (float) width / (float) height;
			if (wh > 1)
			{
				float tmp_heigth = (float) targetWidth / wh;
				float y = ((float) targetHeight - tmp_heigth) / 2;
				// tag = new BufferedImage(targetWidth,(int) tmp_heigth,
				// BufferedImage.TYPE_INT_RGB);
				gra
						.drawImage(src.getScaledInstance(targetWidth,
								(int) tmp_heigth, Image.SCALE_SMOOTH), 0,
								(int) y, null);
			}
			else
			{
				float tmp_width = (float) targetHeight * wh;
				float x = ((float) targetWidth - tmp_width) / 2;
				// tag = new BufferedImage((int) tmp_width,targetHeight,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance((int) tmp_width,
						targetHeight, Image.SCALE_SMOOTH), (int) x, 0, null);
			}
		}
		else
		{
			float wh = (float) width / (float) height;
			if (wh > 1)
			{
				float tmp_heigth = (float) targetWidth / wh;
				float y = ((float) targetHeight - tmp_heigth) / 2;
				// tag = new BufferedImage(targetWidth,(int) tmp_heigth,
				// BufferedImage.TYPE_INT_RGB);
				gra
						.drawImage(src.getScaledInstance(targetWidth,
								(int) tmp_heigth, Image.SCALE_SMOOTH), 0,
								(int) y, null);
			}
			else
			{
				float tmp_width = (float) targetHeight * wh;
				float x = ((float) targetWidth - tmp_width) / 2;
				// tag = new BufferedImage((int) tmp_width,targetHeight,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance((int) tmp_width,
						targetHeight, Image.SCALE_SMOOTH), (int) x, 0, null);
			}
		}

		FileOutputStream out = new FileOutputStream(targetImgPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();

		// ImageIO.write(tag, getFileType(targetImgPath), new
		// File(targetImgPath));
	}

	/**
	 * ���ͼƬˮӡ
	 * 
	 * @param targetImgPath
	 *            Ŀ���ļ�ȫ·��
	 * @param markImgPath
	 *            ˮӡ�ļ�ȫ·��
	 */
	public synchronized static void imgMark(String targetImgPath,
			String markImgPath) throws Exception
	{

		File srcfile = new File(targetImgPath);
		if (!srcfile.exists())
			return;
		Image targetImg = ImageIO.read(srcfile);
		int wideth = targetImg.getWidth(null);
		int height = targetImg.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.drawImage(targetImg, 0, 0, wideth, height, null);
		File _filebiao = new File(markImgPath);
		Image markImg = ImageIO.read(_filebiao);
		int markImgWidth = markImg.getWidth(null);
		int markImgHeight = markImg.getHeight(null);
		Double pointWidth = DoubleUtil.mul(wideth, 0.05);
		Double pointHeight = DoubleUtil.mul(height, 0.05);
		g.drawImage(markImg, wideth - markImgWidth - pointWidth.intValue(),
				height - markImgHeight - pointHeight.intValue(), markImgWidth,
				markImgHeight, null);
		g.dispose();
		/*
		 * FileOutputStream out = new FileOutputStream(targetImgPath);
		 * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		 * encoder.encode(image); out.close();
		 */

		ImageIO.write(image, getFileType(targetImgPath),
				new File(targetImgPath));
	}

	/**
	 * �������ˮӡ
	 * 
	 * @param targetImgPath
	 *            Ŀ��ͼƬȫ·�� markText ˮӡ����
	 */
	public synchronized static void textMark(String targetImgPath,
			String markText) throws Exception
	{
		File _file = new File(targetImgPath);
		Image targetImg = ImageIO.read(_file);
		int wideth = targetImg.getWidth(null);
		int height = targetImg.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		Double pointWidth = DoubleUtil.mul(wideth, 0.05);
		Double pointHeight = DoubleUtil.mul(height, 0.95);
		g.drawImage(targetImg, 0, 0, wideth, height, null);
		g.setColor(Color.GREEN);
		g.setFont(new Font("����", Font.BOLD, 20));
		g.drawString(markText, pointWidth.intValue(), pointHeight.intValue());
		g.dispose();

		/*
		 * FileOutputStream out = new FileOutputStream(targetImgPath);
		 * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		 * encoder.encode(image); out.close();
		 */
		ImageIO.write(image, getFileType(targetImgPath),
				new File(targetImgPath));
	}

	public synchronized static String getFileType(String fileUri)
	{
		File file = new File(fileUri);
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		return fileType;
	}

	public static String changeLargeImgToSmal(String largeImg)
	{
		if(null == largeImg)
			return null;
		return largeImg.replace(".", "_s.");
	}

}
