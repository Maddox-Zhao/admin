package com.huaixuan.network.biz.service.goods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadUtil {
    /**
     * �ϴ��ļ�
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
     * �ϴ�ͼƬ��ѹ��
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
     * ɾ���ļ�
     *
     * @param docfilePath
     * @param fileName
     * @return
     */
    boolean deleteFile(String docfilePath, String fileName);

    /**
     * ��ȡ�ļ��ϴ��ĸ�·��
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
     *            �ϴ�ͼƬ��ָ���ļ�����
     * @param uploadSubPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void uploadWithSpecificName(File file, String fileFileName, String specificName,
                                       String uploadSubPath) throws FileNotFoundException,
                                                            IOException;

    /**
     * add by guoyj �ϴ������ļ�
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
     * �ϴ��ļ�
     *
     * @param file
     * @param uploadSubPath
     * @return
     */
    public String newUpload(MultipartFile file, String uploadSubPath) throws IOException;
    
    /**
     * �ϴ��ļ�
     *
     * @param file
     * @param uploadSubPath
     * @return
     */
    public String newFileUpload(File file,String fileFileName, String uploadSubPath) throws IOException;

    /**
     * �ϴ�ͼƬ��ָ���ļ�����
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
     * add by guoyj �ϴ������ļ�
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
     * �ؼ��ϴ�
     * @param file
     * @return
     * @throws IOException
     */
    public String newFckUpload(MultipartFile file) throws IOException;
}
