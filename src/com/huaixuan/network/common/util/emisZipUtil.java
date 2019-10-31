package com.huaixuan.network.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ѹ���ļ���
 * @author chenfei
 *
 */
public class emisZipUtil extends ZipOutputStream {

    protected static Log  log              = LogFactory.getLog(emisZipUtil.class);
    //压缩级别:0-9
    private static int    defaultLevel     = 7;
  //编码,简体:GB2312,繁体:BIG5
    private static String defaultEncoding  = "GB2312";
  //压缩时用全路径,会生成对应的目录,false:不带路径,只有文件名
    private boolean       userFullPathName = false;
  //注释
    private StringBuffer  comment;

    private String        encoding;

    private int           fileCount        = 0;

    public emisZipUtil(OutputStream outputStream) {
        this(outputStream, defaultEncoding, defaultLevel);
    }

    public emisZipUtil(String file) throws IOException {
        this(new FileOutputStream(new File(file)), defaultEncoding, defaultLevel);
    }

    public emisZipUtil(File file) throws IOException {
        this(new FileOutputStream(file), defaultEncoding, defaultLevel);
    }

    /**
     * 统一调用的构造函数
     *
     * @param outputStream 输出流(输出路径),*.zip
     * @param encoding 编码
     * @param level 压缩级别 0-9
     * */
    public emisZipUtil(OutputStream outputStream, String encoding, int level) {
        super(outputStream);
        buf = new byte[1024];//1024 KB����

        if (encoding != null || !"".equals(encoding))
            this.setEncoding(encoding);

        if (level < 0 || level > 9)
            level = 7;
        this.setLevel(level);
        comment = new StringBuffer();
    }

    public String put(String fileName) throws IOException {
        return put(fileName, "");
    }

    /**
     * 加入要压缩的文件或文件夹
     *
     * @param fileName 加入一个文件,或一个文件夹
     * @param pathName 生成ZIP时加的文件夹路径
     * @return fileName
     * */
    public String put(String fileName, String pathName) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            comment.append("发现一个不存在的文件或目录: ").append(fileName).append("\n");
            return null;
        }

      //递归加入文件
        if (file.isDirectory()) {
            pathName += file.getName() + "/";
            String fileNames[] = file.list();
            if (fileNames != null) {
                for (String f : fileNames)
                    put(fileName + "\\" + f, pathName);
            }
            return fileName;
        }
        fileCount++;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(this);
            if (userFullPathName)
                pathName += file.getPath();
            this.putNextEntry(new ZipEntry(pathName + file.getName()));
            int len;
          //BufferedOutputStream会自动使用 this.buf,如果再使用in.read(buf)数据会错误
            while ((len = in.read()) > -1) {
                out.write(len);
            }
        } catch (IOException ex) {
            comment.append("一个文件读取写入时错误: ").append(fileName).append("\n");
        }

        if (out != null) {
            out.flush();
        }
        if (in != null) {
            in.close();
        }
        this.closeEntry();
        return file.getAbsolutePath();
    }

    /**
     * 加入要压缩的文件或文件夹
     *
     *  淘宝数据包下载后转tbi格式    modify by yangak
     *
     * @param fileName 加入一个文件,或一个文件夹
     * @param pathName 生成ZIP时加的文件夹路径
     * @return fileName
     * */
    public String puts(String fileName, String pathName) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            comment.append("发现一个不存在的文件或目录: ").append(fileName).append("\n");
            return null;
        }

      //递归加入文件
        if (file.isDirectory()) {
            pathName += file.getName() + "/";
            String fileNames[] = file.list();
            if (fileNames != null) {
                for (String f : fileNames)
                    put(fileName + "\\" + f, pathName);
            }
            return fileName;
        }
        fileCount++;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(this);
            if (userFullPathName)
                pathName += file.getPath();
            this.putNextEntry(new ZipEntry(pathName + file.getName()+".tbi"));//ת��ʽ
            int len;
          //BufferedOutputStream会自动使用 this.buf,如果再使用in.read(buf)数据会错误
            while ((len = in.read()) > -1) {
                out.write(len);
            }
        } catch (IOException ex) {
            comment.append("一个文件读取写入时错误: ").append(fileName).append("\n");
        }

        if (out != null) {
            out.flush();
        }
        if (in != null) {
            in.close();
        }
        this.closeEntry();
        return file.getAbsolutePath();
    }

    public String[] put(String[] fileName) throws IOException {
        return put(fileName, "");
    }

    public String[] put(String[] fileName, String pathName) throws IOException {
        for (String file : fileName)
            put(file, pathName);
        return fileName;
    }

    /**
     * 压缩的文件个数
     *
     * @return int
     * */
    public int getFileCount() {
        return this.fileCount;
    }

    //测试
    public static void main(String[] args) {
        try {
            java.util.Date d1 = new java.util.Date();
            //	      emisZipUtil util = new emisZipUtil("C:\\emisZipUtil.zip");
            emisZipUtil util = new emisZipUtil("d:\\emisZipUtil.zip");

            //util.buf = new byte[1024*2]; //可以指定缓存
            util.comment.append("报表批量下载!\n\n");
            util.put("D:/upload/provide/pic");
//            util.put(new String[]{"D:/upload/provide/pic/1111244450450/1111244450450_0.jpg","D:/upload/provide/pic/1111374786161/1111374786161_0.jpg"});
//            	      util.put(new String[]{"D:/emallFile/uploadFiles/goods/200907","C:\\emis","C:\\wwwroot\\smepos_cn 路径.txt"});
//  	      util.put("D:\\JQuery\\Jquery 1.2.6 源码分析\\jquery1.2.6-源码文档-cn.js");
            //	      util.put("C:\\wwwroot\\smepos_cn\\版本更新说明.doc","doc\\");
            //	      util.put("E:\\Images\\16012991.jpg E:\\Images\\16012993.jpg E:\\Images\\16012995.jpg E:\\Images\\16012985.jpg E:\\Images\\16012987.jpg E:\\Images\\16012989.jpg".split(" "),"IMG");
            //	      util.put("C:\\wwwroot\\smepos_cn\\版本更新说明2.doc");
            //	      util.put("C:\\wwwroot\\smepos_cn2");
            //	      util.put("C:\\wwwroot\\smepos_cn\\report_out\\pre_print\\ROOT\\5AROOT中分类 2009-03-04 17-08-14 1072940375_1.xls");

            //	      util.put("D:/emallFile/uploadFiles/goods/200907","D:/emallFile");
            util.put("d:/emallFile/uploadFiles/goods/200909/1251749703009nngbydyu_l.jpg",
                "D:/emallFile");
            util.comment.append("\n共成功压缩文件: ").append(util.getFileCount()).append("个!");
            util.setComment(util.comment.toString());
            if (null != util)
                util.close();
            java.util.Date d2 = new java.util.Date();
            System.out.println("used time = " + (d2.getTime() - d1.getTime()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public StringBuffer getComment() {
        return comment;
    }

    public void setComment(StringBuffer comment) {
        this.comment = comment;
    }
}
