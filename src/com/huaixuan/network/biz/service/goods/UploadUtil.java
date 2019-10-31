package com.huaixuan.network.biz.service.goods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadUtil {
    /**
     * 上传文件
     *
     * @param file
     * @param fileFileName
     * @param uploadSubPath
     * @return
     */
    String upload(File file, String fileFileName, String uploadSubPath)
                                                                       throws FileNotFoundException,
                                                                       IOException;

    /**
     * 上传图片并压缩
     *
     * @param file
     * @param fileFileName
     * @param uploadSubPath
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    String uploadImageWithCompress(File file, String fileFileName, String uploadSubPath, int width,
                                   int height) throws IOException;

    /**
     * 删除文件
     *
     * @param docfilePath
     * @param fileName
     * @return
     */
    boolean deleteFile(String docfilePath, String fileName);

    /**
     * 获取文件上传的根路径
     *
     * @return
     */
    String getUploadRootPath();

    public String createFileName(String preName);

    public String createFileName(String preName, String name);

    public String createFileName(String preName, String name, String backName);

    public String createImageFileName(String preName, String name, String backName);

    public String getRealUpload();

    /**
     * @param file
     * @param fileFileName
     * @param specificName
     *            上传图片并指定文件名称
     * @param uploadSubPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void uploadWithSpecificName(File file, String fileFileName, String specificName,
                                       String uploadSubPath) throws FileNotFoundException,
                                                            IOException;

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
    String[] uploadComapreFile(File file, String fileFileName, String uploadSubPath)
                                                                                    throws FileNotFoundException,
                                                                                    IOException;

    /**
     * 上传文件
     *
     * @param file
     * @param uploadSubPath
     * @return
     */
    public String newUpload(MultipartFile file, String uploadSubPath) throws IOException;
    
    /**
     * 上传文件
     *
     * @param file
     * @param uploadSubPath
     * @return
     */
    public String newFileUpload(File file,String fileFileName, String uploadSubPath) throws IOException;

    /**
     * 上传图片并指定文件名称
     *
     * @param files
     * @param specificName
     * @param uploadSubPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void uploadWithSpecificName(MultipartFile file, String specificName, String uploadSubPath)
                                                                                                     throws FileNotFoundException,
                                                                                                     IOException;

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
    String[] uploadComapreMultipartFile(MultipartFile file, String fileFileName,
                                        String uploadSubPath) throws FileNotFoundException,
                                                             IOException;
    
    /**
     * 控件上传
     * @param file
     * @return
     * @throws IOException
     */
    public String newFckUpload(MultipartFile file) throws IOException;
}
