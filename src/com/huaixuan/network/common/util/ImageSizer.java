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
 * @version ����ʱ�䣺2012-3-21 ����05:48:37 �ϴ���ѹ�� ͼƬ������
 */
public class ImageSizer
{

	private boolean canUpload(String ext, MultipartFile file)
	{
		// �趨�����ϴ����ļ�����
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
	 * �ϴ��ļ�
	 * 
	 * @param img
	 *            ��Ҫ�ϴ���ͼƬ MultipartFile����
	 * @param path
	 *            ����·����${file.upload.dir}��
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
					imageZipProce(f,150	,100,1);//ѹ��ͼƬ
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
	 * ��ȡ��չ���ķ���
	 */
	private String getExtension(String fileName)
	{
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// ʱ�����
	public static void main(String[] args)
	{
		// dateFormat ʹ��
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

		// Calendar ʹ��
		System.out.println("-------------------------");
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(calendar.YEAR) + " "
				+ calendar.get(calendar.MONDAY) + " "
				+ calendar.get(calendar.DAY_OF_MONTH));
	}

	/**
	 * ѹ��ͼƬ����
	 * 
	 * @param oldFile
	 *            ��Ҫѹ����ͼƬ  File
	 * @param width
	 *            ѹ����
	 * @param height
	 *            ѹ����
	 * @param quality
	 *            ѹ�������� <b>����Ϊ1.0</b>
	 * @return
	 */
	public void imageZipProce(File oldFile, int width, int height,float quality)
	{
		try
		{			
			/** �Է������ϵ���ʱ�ļ����д��� */
			Image srcFile = ImageIO.read(oldFile);
			
			//�µĿ�Ⱥ͸߶�
			int new_w = 0, new_h = 0;
			
			// ��ȡͼƬ��ʵ�ʴ�С �߶�
			int h = (int) srcFile.getHeight(null);
			// ��ȡͼƬ��ʵ�ʴ�С ���
			int w = (int) srcFile.getWidth(null);
			
			// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
			if ((((double) w) > (double) width) || (((double) h) > (double) height))
			{
				double rate = 0;// ���ͼƬ����ֵ
				// ��ȴ��ڵ��ڸ߶�
				if (w >= h)
				{
					rate = ((double) w) / (double) width;
				}
				// ���С�ڸ߶�
				else if (h > w)
				{
					rate = ((double) h) / (double) height;
				}
				// �����µı�����ͼƬ�߶�����ֵ
				new_w = (int) (((double) w) / rate);
				new_h = (int) (((double) h) / rate);
				/** ��,���趨 */
				BufferedImage tag = new BufferedImage(new_w, new_h,BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);

				/** ѹ��֮����ʱ���λ�� */
				FileOutputStream out = new FileOutputStream(oldFile.getAbsolutePath().replace(".","_s."));
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/** ѹ������ */
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
