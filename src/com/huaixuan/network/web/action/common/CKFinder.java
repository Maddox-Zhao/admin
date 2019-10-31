package com.huaixuan.network.web.action.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.hundsun.network.melody.common.web.url.URLBroker;

@Controller
public class CKFinder implements ServletContextAware {

    @Autowired
    private URLBroker upServerBroker;
    
	@Autowired
	private UploadUtil uploadUtil;
	
	private @Value("${file.upload.dir}")
	String upload;

    private static Hashtable allowedExtensions;// 允许的上传文件扩展名    
    
    private static  int maxSize = 200;  //200KB

    @Override
    public void setServletContext(ServletContext arg0) {
        // TODO Auto-generated method stub
        
    }
    @RequestMapping(value = "/ckeditor/uploader", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response,
                      AdminAgent adminAgent) throws ServletException, IOException {
        System.out.println("ttt");
    }
    @RequestMapping(value = "/ckeditor/uploader", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest request2,AdminAgent adminAgent)    
    throws ServletException, IOException {    

        response.setContentType("text/html; charset=UTF-8");    
        response.setHeader("Cache-Control", "no-cache");    
        PrintWriter out = response.getWriter();    

        MultipartFile file = request2.getFile("upload");
        
        try{
            if(file.getSize() > (maxSize * 1024)){
                String callback = request.getParameter("CKEditorFuncNum");    
                out.println("<script type=\"text/javascript\">");    
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback    
                        + ",'','上传文件过大'" + ")");    
                out.println("</script>"); 
            }
            if(extIsAllowed(getExtension(file.getOriginalFilename()))){
            	
            	String path = uploadUtil.newFckUpload(file);
                
                System.out.println("ttt");
             // CKEditorFuncNum是回调时显示的位置，这个参数必须有    
                String callback = request.getParameter("CKEditorFuncNum");    
                out.println("<script type=\"text/javascript\">");    
//                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback    
//                        + ",'"+ Constants.FILE_SEP + "imageService" +Constants.FILE_SEP+path+"','上传成功'" + ")"); 
                
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback    
                        + ",'"+ upServerBroker.getConfig().getURL()+Constants.FILE_SEP+path+"','上传成功'" + ")");
                
                out.println("</script>"); 
            }else{
                String callback = request.getParameter("CKEditorFuncNum");    
                out.println("<script type=\"text/javascript\">");    
                out.println("window.parent.CKEDITOR.tools.callFunction(" + callback    
                        + ",'','文件类型为支持'" + ")");    
                out.println("</script>"); 
            }
        }catch(Exception ex){
            String callback = request.getParameter("CKEditorFuncNum");    
            out.println("<script type=\"text/javascript\">");    
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback    
                    + ",'','上传失败'" + ")");    
            out.println("</script>"); 
        }
        out.flush();    
        out.close();    

    }
    /**   
     * 判断扩展名是否允许的方法   
     */   
    private boolean extIsAllowed(String ext) {    
        ext = ext.toLowerCase();    
        ArrayList allowList = new ArrayList();
        allowList.add("jpg");
        allowList.add("jpeg");
        allowList.add("gif");
        allowList.add("png");
        allowList.add("bmp");
      
       
        if (allowList.contains(ext)) {    
            return true;    
        } else {    
            return false;    
        }    
       
    }    
    /**   
     * 获取扩展名的方法   
     */   
    private String getExtension(String fileName) {    
        return fileName.substring(fileName.lastIndexOf(".") + 1);    
    }  
}
