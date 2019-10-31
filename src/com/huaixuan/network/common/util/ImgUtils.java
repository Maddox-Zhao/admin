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
	 * 在图片url的末尾加上字符串append，如convertAppendToUrl("-append",
	 * "d:/img/url.jpg"),结果为：d:/img/url-append.jpg
	 * 
	 * @param append
	 *            在URL后面要加的字符串
	 * @param url
	 *            URL名称
	 * @return 图片url的末尾加上字符串append的新URL串
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
	 * 转换图片类型(imgType参数是： 要转化的目标类型 必须为GIF,JPG,PNG的一种)
	 * 
	 * @param srcImgPath
	 *            原文件全路径
	 * @param imgType
	 *            要转化的目标类型 必须为GIF,JPG,PNG的一种
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
	 * 裁剪图片
	 * 
	 * @param srcImgPath
	 *            原文件全路径
	 * @param targetImgPath
	 *            裁剪后的文件保存全路径
	 * @param startX
	 *            开始裁剪的x坐标
	 * @param stratY
	 *            开始裁剪的y坐标
	 * @param targetWidth
	 *            裁剪的宽度
	 * @param targetHeight
	 *            裁剪的高度
	 */
	public synchronized static void cutOutImg(String srcImgPath,
			String targetImgPath, int startX, int stratY, int targetWidth,
			int targetHeight) throws Exception
	{
		// 取得图片读入器
		Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) readers.next();
		// 取得图片读入流
		InputStream source = null;
		ImageInputStream iis = null;
		try
		{
			source = new FileInputStream(srcImgPath);
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			// 设置图片参数 Rectangle(左上顶点x坐标, y坐标, 矩形宽度, 矩形高度)
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
				throw new Exception("ImgUtils:cutOutImg()source  关闭错误");
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
				throw new Exception("ImgUtils:cutOutImg()iis 关闭错误");
			}
		}
	}

	/**
	 * 裁剪图片，从左上角(0,0)开始
	 * 
	 * @param srcImgPath
	 *            原文件全路径
	 * @param targetImgPath
	 *            裁剪后的文件保存全路径
	 * @param endX
	 *            裁剪后的宽度
	 * @param endY
	 *            裁剪后的高度
	 */

	public synchronized static void cutOutImg(String srcImgPath,
			String targetImgPath, int endX, int endY) throws Exception
	{

		cutOutImg(srcImgPath, targetImgPath, 0, 0, endX, endY);
	}

	/**
	 * 压缩图片
	 * 
	 * @param srcImgPath
	 *            原文件全路径
	 * @param targetImgPath
	 *            压缩后的文件保存全路径
	 * @param targetWidth
	 *            压缩后的宽度
	 * @param targetHeight
	 *            压缩后的高度
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
		// 设置背景色
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
	 * 添加图片水印
	 * 
	 * @param targetImgPath
	 *            目标文件全路径
	 * @param markImgPath
	 *            水印文件全路径
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
	 * 添加文字水印
	 * 
	 * @param targetImgPath
	 *            目标图片全路径 markText 水印文字
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
		g.setFont(new Font("宋体", Font.BOLD, 20));
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
