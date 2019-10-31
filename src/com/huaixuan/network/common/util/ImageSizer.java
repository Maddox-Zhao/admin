package com.huaixuan.network.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.base.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-21 下午05:48:37 上传、压缩 图片工具类
 */
public class ImageSizer
{

	private boolean canUpload(String ext, MultipartFile file)
	{
		// 设定可以上传的文件类型
		List<String> fileType = new ArrayList<String>();
		fileType.add("jpg");
		fileType.add("jpeg");
		fileType.add("bmp");
		fileType.add("gif");
		if (fileType.contains(ext) && file.getSize() < 200 * 1024)
		{
			return true;
		}
		return false;

	}

	/**
	 * 上传文件
	 * 
	 * @param img
	 *            需要上传的图片 MultipartFile类型
	 * @param path
	 *            基本路径（${file.upload.dir}）
	 * @return
	 */
	public String upLoadImage(MultipartFile img, String path)
	{
		String result = "";
		if (img != null && img.getSize() > 0)
		{
			String fileName = img.getOriginalFilename();
			String ext = getExtension(fileName);
			if (canUpload(ext, img))
			{
				Date date = new Date();
				Long name = date.getTime() + new Random().nextInt(100000);
				String saveName = name + "." + ext;
				try
				{
					Calendar calendar = Calendar.getInstance();
					String year = calendar.get(Calendar.YEAR) + "";
					String month = (calendar.get(Calendar.MONTH) + 1) + "";
					String day = calendar.get(Calendar.DAY_OF_MONTH)
							+ Constants.FILE_SEP;
					String saveSubPath = "/img/" + year + month + day
							+ saveName;
					File f = new File(path, saveSubPath);
					if (!f.exists())
						f.mkdirs();
					img.transferTo(f); // upLoad
					imageZipProce(f,150	,100,1);//压缩图片
					result = saveSubPath;// return this picture's URL
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				result = "error";
			}
		}
		return result;
	}

	/**
	 * 获取扩展名的方法
	 */
	private String getExtension(String fileName)
	{
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 时间测试
	public static void main(String[] args)
	{
		// dateFormat 使用
		DateFormat shortDateFormat = DateFormat
				.getDateInstance(DateFormat.SHORT);
		DateFormat mediumDateFormat = DateFormat
				.getDateInstance(DateFormat.MEDIUM);
		DateFormat longDateFormat = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		Date date = new Date();
		System.out.println(shortDateFormat.format(date));
		System.out.println(mediumDateFormat.format(date));
		System.out.println(longDateFormat.format(date));
		System.out.println(fullDateFormat.format(date));

		// Calendar 使用
		System.out.println("-------------------------");
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(calendar.YEAR) + " "
				+ calendar.get(calendar.MONDAY) + " "
				+ calendar.get(calendar.DAY_OF_MONTH));
	}

	/**
	 * 压缩图片方法
	 * 
	 * @param oldFile
	 *            将要压缩的图片  File
	 * @param width
	 *            压缩宽
	 * @param height
	 *            压缩长
	 * @param quality
	 *            压缩清晰度 <b>建议为1.0</b>
	 * @return
	 */
	public void imageZipProce(File oldFile, int width, int height,float quality)
	{
		try
		{			
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(oldFile);
			
			//新的宽度和高度
			int new_w = 0, new_h = 0;
			
			// 获取图片的实际大小 高度
			int h = (int) srcFile.getHeight(null);
			// 获取图片的实际大小 宽度
			int w = (int) srcFile.getWidth(null);
			
			// 为等比缩放计算输出的图片宽度及高度
			if ((((double) w) > (double) width) || (((double) h) > (double) height))
			{
				double rate = 0;// 算出图片比例值
				// 宽度大于等于高度
				if (w >= h)
				{
					rate = ((double) w) / (double) width;
				}
				// 宽度小于高度
				else if (h > w)
				{
					rate = ((double) h) / (double) height;
				}
				// 构造新的比例的图片高度与宽度值
				new_w = (int) (((double) w) / rate);
				new_h = (int) (((double) h) / rate);
				/** 宽,高设定 */
				BufferedImage tag = new BufferedImage(new_w, new_h,BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);

				/** 压缩之后临时存放位置 */
				FileOutputStream out = new FileOutputStream(oldFile.getAbsolutePath().replace(".","_s."));
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/** 压缩质量 */
				jep.setQuality(quality, true);
				encoder.encode(tag, jep);
				out.close();
				srcFile.flush();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
