package com.huaixuan.network.biz.service.pic.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsGalleryDao;
import com.huaixuan.network.biz.dao.shop.BrandDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsGallery;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.pic.AutoCreateImageService;
import com.huaixuan.network.common.util.ImgUtils;

/**
 * @author Mr_Yang 2015-8-19 下午02:23:17
 **/

@Service("autoCreateImageService")
public class AutoCreateImageServiceImpl implements AutoCreateImageService
{
	public static long interval = 1000 * 20;// 每隔20秒扫描一次
	public static final String path = "e:/scannPic"; // 扫描的目录
	public static final String savePath = "e:/upload"; // web页面图片保存的目录
	public static final String[] subPathArr = new String[] { "800" };// 扫描path目录下子目录
	public static final int maxPicNum = 8;// 最多8张
	private Map<String, String> map = new HashMap<String, String>();//查询goods所需条件

	private Map<String, String> galleryMap = new HashMap<String, String>(); //查询图片列表所需条件

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private GoodsGalleryDao goodsGalleryDao;
	
	@Autowired
	private  BrandDao brandDao;

	/**
	 * spring 自动执行该方法
	 * 启动线程  自动同步path目录下图片
	 */
//	@PostConstruct
	public void autoUpdateImage2DB()
	{
		Thread thread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				FileFilter jpgFileFilter = new JpgImageFileFilter();
				while (true)
				{
					String[] imgePathArr = null;
					File directoryFile = new File(AutoCreateImageServiceImpl.path);
					if(!directoryFile.exists()) directoryFile.mkdirs();
					File[] totalFiles = directoryFile.listFiles();
					for (int i = 0; i < totalFiles.length; i++)
					{
						boolean hasSubDirectory = true; // 如果没有800X800目录 则更新为当前目录下的图片
						File huoHaoFileDirectory = totalFiles[i]; // eg:  d:/upload1/1M1132 QME F0002
						String huoHao = huoHaoFileDirectory.getName(); // eg: 1M1132 QME F0002 型号 材质 颜色
						File[] huoHaoFiles = huoHaoFileDirectory.listFiles();// eg: d:/upload1/1M1132 QME F0002/800
						if(huoHaoFiles == null) 
						{
							try
							{
								Thread.sleep(AutoCreateImageServiceImpl.interval);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							continue;
						
						}
							
						for (int index = 0; index < huoHaoFiles.length; index++)
						{
							File huohaoFile = huoHaoFiles[index];

							if (huohaoFile.isDirectory())
							{
								// 如果有800X800 或者定义的其他尺寸的 更新到数据库 否则更新当前目录下的图片到数据库
								if (Arrays.binarySearch(AutoCreateImageServiceImpl.subPathArr,huohaoFile.getName()) == 0)
								{
									hasSubDirectory = false;// 有800X800尺寸
									File[] subFiles = huohaoFile.listFiles(jpgFileFilter);
									imgePathArr = getImageFromFilesArr(subFiles);
								}
							}
						}

						// 没有800X800或者其他定义的尺寸 更新当前目录下的图片
						if (hasSubDirectory)
						{

							imgePathArr = getImageFromFilesArr(huoHaoFileDirectory.listFiles(jpgFileFilter));
						}

						
						if(imgePathArr.length > 0)
						{
							//处理图片path完成 后面压缩 修改数据库值
							updateGoodsPic(huoHao, imgePathArr);
							
						}

					}

					try
					{
						Thread.sleep(AutoCreateImageServiceImpl.interval);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
			}
			
		});
		thread.start();
		
	}

	/** 
	 * 处理图片 1.jpg为主图  最多AutoCreateImageServiceImpl.maxPicNum 张 默认为8
	 * @param files
	 * @return
	 */
	public String[] getImageFromFilesArr(File[] files)
	{
		// 处理这里涉及到处理主图,图片规则为1.jpg 后面的随机
		int size = AutoCreateImageServiceImpl.maxPicNum;
		if (files.length < AutoCreateImageServiceImpl.maxPicNum) size = files.length;
		String[] imgArr = new String[size];
		boolean haveMainPic = false;
		for (File f : files)
		{
			if ("1.jpg".equals(f.getName().toLowerCase())) // 目录规则：主图为1.jpg 附图随机
			{
				imgArr[0] = f.getAbsolutePath();
				haveMainPic = true;
				break;
			}
		}
		int index = 1;
		if (!haveMainPic) index = 0;
		for (File f : files)
		{
			if (!"1.jpg".equals(f.getName().toLowerCase())) imgArr[index++] = f.getAbsolutePath();
			if (index >= AutoCreateImageServiceImpl.maxPicNum) break;
		}
		return imgArr;
	}

	//压缩图片,更新数据库
	public void updateGoodsPic(String huoHao, String[] imgArr)
	{
		if (null == huoHao || "".equals(huoHao)) return;
		huoHao = huoHao.trim();
		String type = "";
		String material = "";
		String color = "";
		String[] tmp = huoHao.split(" ");
		if (tmp.length == 1)
		{
			type = tmp[0];
		}
		else if (tmp.length == 2)
		{
			type = tmp[0];
			color = tmp[1];
		}
		else if (tmp.length >= 3)
		{
			type = tmp[0];
			material = tmp[1];
			color = tmp[2];
		}

		if (null != type) {type = type.trim(); type=type.replace("", "");}
		if (null != material) {material = material.trim();material = material.replace("", "");}
		if (null != color) {color = color.trim(); color = color.replace("", "");}
		if("".equals(type)) return; //没有型号返回  不用查了 都没型号
		map.put("type", type);
		map.put("material", material);
		map.put("color", color);
		Goods goods = goodsDao.getGooodsByTypeMaterialColor(map);
		if(goods == null && tmp.length == 2)
		{
			map.put("type", type);
			map.put("material", color);
			map.put("color", "");
			goods = goodsDao.getGooodsByTypeMaterialColor(map);
		}
		if (null != goods)
		{
			Long goodsId = goods.getId();
			galleryMap.put("goodsId", goodsId.toString());
			List<GoodsGallery> list = goodsGalleryDao.getGoodsGallerysByParameterMap(galleryMap); // 已存在的图片如果更新成功删除这些图片
			
			//处理生成数据库存储字段需要的数据  eg:goods/201503/BURBERRY_3877791___s.jpg
			Brand brand = brandDao.getBrand(goods.getBrandId());
			String orgImgFileName = brand.getBrandName() + "_" + goods.getType()+ "_" + goods.getMaterial() + "_" + goods.getColor() + ".jpg";
			String newFilePath = goods.getImgLarge().substring(0,goods.getImgLarge().lastIndexOf("/"));
			
			
			//路径不存在 自动生成
			File newFile = new File(AutoCreateImageServiceImpl.savePath+Constants.FILE_SEP+newFilePath);
			if(!newFile.exists())newFile.mkdirs();
			
			//开始压缩 并更新数据库
			for (int index = 0 ; index < imgArr.length; index++)
			{
					String srcImgFullPath  = imgArr[index];
					String newOrgFileName = orgImgFileName;
					if(index > 0)
						newOrgFileName = orgImgFileName.replace(".jpg", "_"+index + ".jpg");  //有多张图片  图片名字加上index
					
					GoodsGallery goodsGallery =reduceImg(srcImgFullPath,newFilePath,newOrgFileName);//压缩
					goodsGallery.setGoodsId(goods.getId());
					goodsGallery.setSort(index+1);
					if(index<AutoCreateImageServiceImpl.maxPicNum)
					{
						try
						{
							goodsGalleryDao.addGoodsGallery(goodsGallery);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					//更新goods表主图
					if (index == 0) {
						goods.setImgLarge(goodsGallery.getImgLarge());
						goods.setImgMiddle(goodsGallery.getImgMiddle());
						goods.setImgSmall(goodsGallery.getImgSmall());
						goods.setIsGmtCutprice("yes");
						goodsDao.editGoods(goods);
					}
			}
			
			//如果有图片，删除该goods已经存在的图片
			if(imgArr.length > 0)
			{
				
				for(int i = 0; i < list.size(); i++)
				{
					GoodsGallery goodsGallery = list.get(i);
					try
					{
						goodsGalleryDao.removeGoodsGallery(goodsGallery.getId());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				
				//压缩 更新之后 移动到新目录
				moveFolder(AutoCreateImageServiceImpl.path + Constants.FILE_SEP + huoHao, AutoCreateImageServiceImpl.savePath+Constants.FILE_SEP+"scannGoodsOri");
				
			}

		}

	}

	
	private GoodsGallery reduceImg(String srcImgFullPath,String targetImgPath,String orgImgFileName)
	{

		GoodsGallery goodsGallery = new GoodsGallery();
		try
		{
			String orgFilePath = AutoCreateImageServiceImpl.savePath + Constants.FILE_SEP + targetImgPath + Constants.FILE_SEP + orgImgFileName;
			AutoCreateImageServiceImpl.copyFile(srcImgFullPath,orgFilePath ); //把原图复制到新目录下
			
			String org_img = targetImgPath +Constants.FILE_SEP  +  orgImgFileName;
			

			String large_img =   org_img.replace(".", "_l.");

			String middle_img = large_img.replace("_l", "_m");

			String samll_img = large_img.replace("_l", "_s");

			ImgUtils.reduceImg(srcImgFullPath,
					AutoCreateImageServiceImpl.savePath + Constants.FILE_SEP + large_img, 600, 610);
			ImgUtils.reduceImg(srcImgFullPath,
					AutoCreateImageServiceImpl.savePath + Constants.FILE_SEP + middle_img, 320, 320);
			ImgUtils.reduceImg(srcImgFullPath,
					AutoCreateImageServiceImpl.savePath + Constants.FILE_SEP + samll_img, 40, 50);

			goodsGallery.setImgLarge(large_img);
			goodsGallery.setImgMiddle(middle_img);
			goodsGallery.setImgSmall(samll_img);
			goodsGallery.setImgOriginal(org_img);

		}
		catch (Exception e)
		{
			throw new ManagerException(e);
		}
		return goodsGallery;
	}

	//压缩完成后移动文件夹到新目录
	public static void moveFolder(String oldFolderPath,String newFolderPath)
	{
		try
		{
			File srcFolder = new File(oldFolderPath);
			File destFolder = new File(newFolderPath);
			if(!destFolder.exists()) destFolder.mkdirs();
			File newFile = new File(destFolder.getAbsoluteFile() + Constants.FILE_SEP + srcFolder.getName());
			if(newFile.exists())
			{
				delFile(newFile.getAbsolutePath());
			}
			srcFolder.renameTo(newFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 复制
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFile(String oldPath, String newPath) 
	{
		 try 
		 {
			 int byteread = 0;
			 File oldfile = new File(oldPath);
			 if (oldfile.exists()) 
			 {
				 FileInputStream inStream = new FileInputStream(oldPath);  //读入原文件
				 BufferedInputStream bufeInStream = new BufferedInputStream(inStream);;
				 FileOutputStream fs = new FileOutputStream(newPath);
				 BufferedOutputStream bufeOutStream =  new BufferedOutputStream(fs);
				 
				 byte[] buffer = new byte[1444];
				 while ( (byteread = bufeInStream.read(buffer)) != -1) 
				 {
					 bufeOutStream.write(buffer, 0, byteread);
				 }
				 bufeInStream.close();
				 bufeOutStream.close();
			 }
		 }
		 catch (Exception e) 
		 {
			 System.out.println("复制单个文件操作出错");
			 e.printStackTrace();
		 }
	
	}

	public static void main(String[] args)
	{
		//moveFolder("D:/upload2/1M0208 QWA F0016","D:/webpic/goodsOri");
		//delFile("D:\\webpic\\goodsOri\\1M0506 QHH F0002");
		
		//文件重命名
		File f =  new File("c:/1");
		File[] files = f.listFiles();
		for(File file : files)
		{
			String fileName = file.getName();
			String newFileName = fileName = fileName.replace("_", " ");
			newFileName = newFileName.trim();
			newFileName = fileName = fileName.replace("-", " ");
			newFileName = fileName = fileName.replace("BURBERRY ", "");
			newFileName = fileName = fileName.replace("Burberry ", "");
			newFileName = fileName = fileName.replace("FENDI ", "");
			newFileName = fileName = fileName.replace("GUCCI ", "");
			newFileName = fileName = fileName.replace("GZ ", "");
			newFileName = fileName = fileName.replace("PRADA ", "");
			newFileName = fileName = fileName.replace("SALVATORE FERRAGAMO ", "");
			newFileName = fileName = fileName.replace("SalvatoreFerragamo ", "");
			newFileName = fileName = fileName.replace("TORYBURCH ", "");
			newFileName = newFileName.trim();
			newFileName = newFileName.toUpperCase();
			File newFile = new File("c:/1.1/"+newFileName);
			file.renameTo(newFile);
			
			System.out.println(file.getName());
			System.out.println(newFileName);
			System.out.println();
			
		}
		
		 
	}
	
	
	//递归删除文件夹
	public static void delFile(String filepath) 
	{
			File f = new File(filepath);//定义文件路径       
			if(f.exists() && f.isDirectory())
			{
				if(f.listFiles().length==0)
				{
					f.delete();
				}
				else
				{
					File delFile[]=f.listFiles();
					int i =f.listFiles().length;
					for(int j=0;j<i;j++)
					{
						if(delFile[j].isDirectory())
						{
							delFile(delFile[j].getAbsolutePath()); 
						}
						delFile[j].delete();
					}
				}
			}
			f.delete();

	}
	

}
