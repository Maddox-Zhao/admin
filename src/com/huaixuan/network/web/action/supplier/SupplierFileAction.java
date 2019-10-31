package com.huaixuan.network.web.action.supplier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.Result;
import com.huaixuan.network.biz.service.supplier.SupplierFileSourceService;
import com.huaixuan.network.biz.service.supplier.SupplierGoodsService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;



/**
 * @author Mr_Yang   2015-9-7 ����11:37:08
 * C�˹����̴���
 **/

@Controller
@RequestMapping("/cSupplier")
public class SupplierFileAction
{

	
	private @Value("${file.upload.dir}") String upload;
	
	
	@Autowired
	private SupplierFileSourceService supplierFileSourceService;
	
	@Autowired
	private SupplierGoodsService supplierGoodsService;
	/**
	 * ��ѯ������Դ�ļ�
	 * @return
	 */
	@RequestMapping(value = "/searchSourceFile")
	@AdminAccess
	public String searchFile(@ModelAttribute("supplierFileSource")SupplierFileSource supplierFileSource,
			@RequestParam(value="page",required = false,defaultValue="1")int currPage,Model model)
	{
		if (supplierFileSource != null && StringUtils.isNotBlank(supplierFileSource.getSourceName())) {
			supplierFileSource.setSourceName(supplierFileSource.getSourceName().trim());
		}
		//Ĭ�ϲ�ѯδɾ��״̬��
		if(supplierFileSource.getIsDelete()  == null)
		{
			supplierFileSource.setIsDelete(0L);
		}
		else if(-1 == supplierFileSource.getIsDelete() )
		{
			supplierFileSource.setIsDelete(null);
		}
		
		//������Դ�ļ�ID
		
		QueryPage queryPage = supplierFileSourceService.searchSupplierFileSourceListWithPage(supplierFileSource, currPage,20);
		model.addAttribute("query",queryPage);
		return "/supplier/searchSupplierFileSource";
	}
	
	
	/**
	 * ����Դ�ļ�
	 * @return
	 */
	@RequestMapping(value = "/downLoadSourceFile")
	@AdminAccess
	public ResponseEntity<byte[]> downLoadSourceFile(@RequestParam(value="id",required = true)Long sourceFileId,Model model,HttpServletRequest request) 
	throws IOException
	{
		SupplierFileSource supplierFileSource = supplierFileSourceService.getSupplierFileSourceById(sourceFileId);
		if(null ==supplierFileSource)return null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		//һ�����������
		String fileName = java.net.URLEncoder.encode(supplierFileSource.getSourceName(), "UTF-8");
		
		//�������������������
		String browser = getBrowser(request);
		if(browser.equals("FF"))
		{
			fileName = new String(supplierFileSource.getSourceName().getBytes("UTF-8"),"iso-8859-1");
		}
		headers.setContentDispositionFormData("attachment", fileName);
		File file = new File(upload + supplierFileSource.getPath());
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	}
	
	
	/**
	 * ����Excelģ��
	 * @return
	 */
	@RequestMapping(value = "/downLoadExcelTemplate")
	@AdminAccess
	public ResponseEntity<byte[]> downLoadExcelTemplate(Model model,HttpServletRequest request) 
	throws IOException
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		//һ�����������
		String fileName = java.net.URLEncoder.encode("supplier_template.xlsx", "UTF-8");
		
		//�������������������
		String browser = getBrowser(request);
		if(browser.equals("FF"))
		{
			fileName = new String("supplier_template.xlsx".getBytes("UTF-8"),"iso-8859-1");
		}
		headers.setContentDispositionFormData("attachment", fileName);
		File file = new File(request.getRealPath("/") + "template/supplier_template.xlsx");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	}
	
	
	/**
	 * �ϴ�������Excel�ļ�
	 * @return
	 */
	@RequestMapping(value = "/upLoadSourceFile")
	@AdminAccess
	@ResponseBody
	public String upLoadSourceFile(@RequestParam(value="sourceFileId",required = true)Long sourceFileId,
			@RequestParam(value="supplierId",required = true)Long supplierId,HttpServletRequest req,Model model)
	throws Exception
	{
		 MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		 MultipartFile mFile = request.getFile("sourceFile");
		 String orgFileName = mFile.getOriginalFilename();
		 
		 //����
		 Result result = supplierFileSourceService.exportExcel2SupplierGoods(mFile.getInputStream(), supplierId, sourceFileId);
		 if(result.isOK() != true) return result.getMsg(); //ģ�����������Ϣ����
		 
		 String subPath =  "/supplier/excel/";
		 String path = upload + subPath;
		 File file = new File(path);
		 if(!file.exists()) file.mkdirs();
		 
		 //��ȡ��׺
		 String houZui = ".xlsx";
		 String[] fileNameArr = orgFileName.split(".");
		 if(fileNameArr.length == 2) 
		 {
			 houZui = "." + fileNameArr[1];
		 }
		 
		 //�ϴ��ļ���ָ��Ŀ¼
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 String fileName = sdf.format(new Date()) + houZui;
		 path = path + fileName;
		 InputStream in = mFile.getInputStream();
		 OutputStream os = new FileOutputStream(path);
		 BufferedInputStream bis = new BufferedInputStream(in);
		 BufferedOutputStream bos = new BufferedOutputStream(os);
		 
		 int length = 0;
		 byte[] b = new byte[1024];
		 while((length = bis.read(b)) != -1)
		 {
			 bos.write(b, 0, length);
		 }
		 bis.close();
		 bos.close();
		 
		 
		//��װ��Ҫ�����Excel����
		SupplierFile supplierFile = new SupplierFile();
		supplierFile.setFileName(fileName);
		supplierFile.setPath(subPath+fileName);
		supplierFile.setSourceFile(sourceFileId);
		supplierFile.setSourceName(orgFileName);
		supplierFileSourceService.insertSupplierFile(supplierFile); //����

	
		if(result.isOK() == true)
		{
			//����Դ�ļ�Ϊ���ϴ�
			SupplierFileSource s = new SupplierFileSource();
			s.setId(sourceFileId);
			s.setIsDeal(1L);
			supplierFileSourceService.updateSupplierFileSourceByNotNull(s);
		}
		
		//�������Ժ󷵻���Ϣ
		return "_ok_";
	}
	
	
	/**
	 * ɾ��Դ�ļ�   ��Ӧ�Ĳ�ƷҲɾ��
	 * @param id
	 * @return
	 */
	@AdminAccess
	@RequestMapping("/deleteSourceFile")
	@ResponseBody
	public String deleteSourceFile(@RequestParam("id")Long id)
	{
		SupplierFileSource file = supplierFileSourceService.getSupplierFileSourceById(id);
		file.setIsDelete(1L);//��̨ɾ��
		supplierFileSourceService.updateSupplierFileSourceByNotNull(file);
		
		//ɾ����Ӧ�Ĳ�Ʒ
		SupplierGoods supplierGoods = new SupplierGoods();
		supplierGoods.setIdFile(id);
		List<SupplierGoods> list =  supplierGoodsService.selectSupplierGoods(supplierGoods);
		for(SupplierGoods goods : list)
		{
			goods.setIsDelete(1L); //����Ϊ��ɾ��
			supplierGoodsService.updateSupplierGoods(goods);
		}
		return "ok";
	}
	
	private String getBrowser(HttpServletRequest request)
	{
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		if(userAgent != null)
		{
			if(userAgent.indexOf("msie") >= 0) return "IE";
			if(userAgent.indexOf("firefox") >= 0) return "FF";
			if(userAgent.indexOf("safari") >= 0) return "SF";
		}
		return "";
	}
	 
 

}
 
