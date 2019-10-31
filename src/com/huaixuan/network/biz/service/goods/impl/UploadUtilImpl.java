package com.huaixuan.network.biz.service.goods.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.hundsun.network.melody.common.util.ShortUUIDGenerator;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("uploadUtil")
public class UploadUtilImpl implements UploadUtil {

    private final Log log          = LogFactory.getLog(getClass());
    /**
     * 上传路径
     */
    private String    uploadDir;
    private String    rootPath;
    private int       picMinLength = 256000;

    private @Value("${file.upload.dir}")
	String upload;

    /**
     *
     * @param file
     * @param fileFileName
     * @param uploadSubPath
     * @return
     * @throws Exception
     */
    public synchronized String upload(File file, String fileFileName, String uploadSubPath)
                                                                                           throws FileNotFoundException,
                                                                                           IOException {

        String fileName = null;
        // the directory to upload to
        String realPath = uploadDir + Constants.FILE_SEP + rootPath + Constants.FILE_SEP
                          + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // retrieve the file data
        // String fileType =
        // fileFileName.substring(StringUtil.lastIndexOf(fileFileName, "."));
        String randonFile = createFileName(fileFileName);

        if (file.length() > 0) {
            InputStream stream = new FileInputStream(file);

            // write the file to the file specified
            fileName = realPath + randonFile;

            OutputStream bos = new FileOutputStream(fileName);
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();
            stream.close();
        }

        return randonFile;
    }

    /**
     * add by guoyj 上传对账文件
     *
     * @param file
     * @param fileFileName
     * @param uploadSubPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public synchronized String[] uploadComapreFile(File file, String fileFileName,
                                                   String uploadSubPath)
                                                                        throws FileNotFoundException,
                                                                        IOException {
        String[] str = new String[2];
        String fileName = null;
        // the directory to upload to
        String realPath = uploadDir + Constants.FILE_SEP + rootPath + Constants.FILE_SEP
                          + uploadSubPath + Constants.FILE_SEP;
        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // retrieve the file data
        String fileType = "";
        String randonFile = "";
        int lastPosition = StringUtil.lastIndexOf(fileFileName, ".");
        if (lastPosition != -1) {
            fileType = fileFileName.substring(StringUtil.lastIndexOf(fileFileName, "."));
            randonFile = createFileName(fileType);
        } else {
            fileType = fileFileName;
            randonFile = createFileName(fileType);
        }

        if (file.length() > 0) {
            InputStream stream = new FileInputStream(file);

            // write the file to the file specified
            fileName = realPath + randonFile;

            OutputStream bos = new FileOutputStream(fileName);
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();
            stream.close();
        }
        str[0] = randonFile; // 重新命名的文件名称
        str[1] = fileName; // 上传的文件的路径
        return str;
    }

    /**
     * @param file
     * @param fileFileName
     * @param specificName
     *            //上传图片并指定文件名称
     * @param uploadSubPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public synchronized void uploadWithSpecificName(File file, String fileFileName,
                                                    String specificName, String uploadSubPath)
                                                                                              throws FileNotFoundException,
                                                                                              IOException {
        if (file == null)
            return;
        String fileName = null;
        // the directory to upload to
        String realPath = uploadDir + Constants.FILE_SEP + rootPath + Constants.FILE_SEP
                          + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // retrieve the file data
        String fileType = fileFileName.substring(StringUtil.lastIndexOf(fileFileName, "."));

        if (file.length() > 0) {
            InputStream stream = new FileInputStream(file);

            // write the file to the file specified
            fileName = realPath + specificName + fileType;

            OutputStream bos = new FileOutputStream(fileName);
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();
            stream.close();
        }

    }

    /**
     *
     * @param file
     * @param fileFileName
     * @param uploadSubPath
     * @param width
     * @param height
     * @throws IOException
     */
    public String uploadImageWithCompress(File file, String fileFileName, String uploadSubPath,
                                          int width, int height) throws IOException {

        String newFileName = null;
        String realPath = uploadDir + Constants.FILE_SEP + rootPath + Constants.FILE_SEP
                          + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        Image bis = ImageIO.read(file);
        int c_w = bis.getWidth(null);

        newFileName = this.createFileName(fileFileName);

        if (c_w < width && file.length() < picMinLength) {
            ImageIO.write(ImageIO.read(file), "jpeg", new File(realPath + newFileName));
        } else {
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(bis.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
            ImageIO.write(tag, "jpeg", new File(realPath + newFileName));
        }
        bis = null;

        return newFileName;
    }

    public synchronized String createFileName(String name) {
        return createFileName("", name);
    }

    public synchronized String createFileName(String preName, String name) {
        return createFileName(preName, name, "");
    }

    public synchronized String createFileName(String preName, String name, String backName) {
        Date date = new Date();
        RandomStringUtils randomString = new RandomStringUtils();
        Random rad = new Random();
        int i = name.lastIndexOf(".");
        // String[] names = name.split(".");
        if (preName == null)
            preName = "";
        if (backName == null)
            backName = "";
        String fileName = preName + String.valueOf(date.getTime())
                          + randomString.random(8, 'a', 'z', true, false, null, rad) + backName
                          + name.substring(i);

        return fileName;
    }

    public synchronized String createImageFileName(String preName, String name, String backName) {
        Date date = new Date();
        RandomStringUtils randomString = new RandomStringUtils();
        Random rad = new Random(System.currentTimeMillis());
        String[] names = name.split(".");
        if (preName == null)
            preName = "";
        if (backName == null)
            backName = "";
        String fileName = preName + names[0] + backName + names[1];

        return fileName;
    }

    public boolean deleteFile(String docfilePath, String fileName) {
        boolean flag = (new File(this.uploadDir + Constants.FILE_SEP + this.rootPath
                                 + Constants.FILE_SEP + docfilePath + Constants.FILE_SEP + fileName))
            .delete();
        if (!flag) {
            if (log.isDebugEnabled()) {
                log.debug("file delete failed!");
            }
        }
        return flag;
    }

    @Override
    public String newUpload(MultipartFile file, String uploadSubPath) throws IOException {

        String filePath = null;

        if (file == null || StringUtil.isEmpty(file.getOriginalFilename())) {
            return filePath;
        }

        String realPath = upload + Constants.FILE_SEP + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        String randonFile = createFileName(file.getOriginalFilename());
        if (file.getSize() > 0) {
            filePath = realPath + randonFile;
            
            OutputStream bos = new FileOutputStream(filePath);
            InputStream in = file.getInputStream();
            IOUtils.copy(in, bos);
        }

        return randonFile;

    }
    
    @Override
    public String newFileUpload(File file,String fileFileName,
    		String uploadSubPath) throws IOException {

    	String fileName = null;
		// the directory to upload to
		String realPath = upload + Constants.FILE_SEP 
						+ uploadSubPath + Constants.FILE_SEP;

		// write the file to the file specified
		File dirPath = new File(realPath);


		// 如果没有，建立目录
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		
		//采用原文件名
		// retrieve the file data
//		String fileType = StringUtil.toLowerCase(fileFileName
//				.substring(StringUtil.lastIndexOf(fileFileName, ".")));
		
//		String randonFile = createFileName(fileType);
		String randonFile = fileFileName.substring(
				StringUtil.lastIndexOf(fileFileName, "/")+1);

		if (file.length() > 0) {
			InputStream stream = new FileInputStream(file);

			// write the file to the file specified
			fileName = realPath + randonFile;

			OutputStream bos = new FileOutputStream(fileName);
			int bytesRead;
			byte[] buffer = new byte[8192];

			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();
			stream.close();
		}

		return randonFile;

    }


    @Override
    public void uploadWithSpecificName(MultipartFile file, String specificName, String uploadSubPath)
                                                                                                     throws FileNotFoundException,
                                                                                                     IOException {
        if (file == null)
            return;
        String filePath = null;
        // the directory to upload to
        String realPath = uploadDir + Constants.FILE_SEP + rootPath + Constants.FILE_SEP
                          + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // retrieve the file data
        String fileType = file.getOriginalFilename().substring(
            StringUtil.lastIndexOf(file.getOriginalFilename(), "."));

        String randonFile = createFileName(file.getOriginalFilename());
        if (file.getSize() > 0) {
            filePath = realPath + randonFile;
            OutputStream bos = new FileOutputStream(filePath);
            InputStream in = file.getInputStream();
            IOUtils.copy(in, bos);
        }

    }

    public String getUploadRootPath() {
        return this.rootPath;
    }

    /**
     * @param picMinLength
     *            the picMinLength to set
     */
    public void setPicMinLength(int picMinLength) {
        this.picMinLength = picMinLength;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRealUpload() {
        return this.uploadDir;
    }

    @Override
    public String[] uploadComapreMultipartFile(MultipartFile file, String fileFileName,
                                               String uploadSubPath) throws FileNotFoundException,
                                                                    IOException {
        String[] str = new String[2];
        String fileName = null;
        // the directory to upload to
        String realPath = upload + Constants.FILE_SEP + uploadSubPath + Constants.FILE_SEP;
        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // retrieve the file data
        String fileType = "";
        String randonFile = "";
        int lastPosition = StringUtil.lastIndexOf(fileFileName, ".");
        if (lastPosition != -1) {
            fileType = fileFileName.substring(StringUtil.lastIndexOf(fileFileName, "."));
            randonFile = createFileName(fileType);
        } else {
            fileType = fileFileName;
            randonFile = createFileName(fileType);
        }

        if (file.getSize() > 0) {
            InputStream stream = file.getInputStream();

            // write the file to the file specified
            fileName = realPath + randonFile;

            OutputStream bos = new FileOutputStream(fileName);
            int bytesRead;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();
            stream.close();
        }
        str[0] = randonFile; // 重新命名的文件名称
        str[1] = fileName; // 上传的文件的路径
        return str;
    }

	@Override
	public String newFckUpload(MultipartFile file) throws IOException {
        String filePath = null;

        if (file == null || StringUtil.isEmpty(file.getOriginalFilename())) {
            return filePath;
        }
        
        String uploadSubPath = "fck" + Constants.FILE_SEP + "image";

        String realPath = upload + Constants.FILE_SEP + uploadSubPath + Constants.FILE_SEP;

        // write the file to the file specified
        File dirPath = new File(realPath);

        // 如果没有，建立目录
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        String UUId = ShortUUIDGenerator.generate();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String randonFile = UUId + prefix;
        if (file.getSize() > 0) {
            filePath = realPath + randonFile;
            OutputStream bos = new FileOutputStream(filePath);
            InputStream in = file.getInputStream();
            IOUtils.copy(in, bos);
        }

        return uploadSubPath + Constants.FILE_SEP + randonFile;
	}

}
