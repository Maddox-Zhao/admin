package com.huaixuan.network.web.action.supplier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.pic.impl.AutoCreateImageServiceImpl;
import com.huaixuan.network.biz.service.supplier.SupplierGoodsService;
import com.huaixuan.network.common.util.ImgUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;



/**
 * @author Mr_Yang   2015-9-15 上午10:14:09
 **/

@Controller
public class SupplierGoodsAction extends BaseAction
{
	
	@Autowired
	private SupplierGoodsService supplierGoodsService;
	
	
	private @Value("${file.upload.dir}")
	String path;
	
	@RequestMapping("/supplier/searchSupplierGoods")
	public String searchSupplierGoods(@ModelAttribute("supplierGoods")SupplierGoods supplierGoods,
			@RequestParam(value="currPage",required=false,defaultValue="1")int currPage,@RequestParam(value="idFile",required=false,defaultValue="")Long idFile,
			Model model)
	{
		//默认查询未删除状态的
		if(supplierGoods.getIsDelete() == null)
		{
			supplierGoods.setIsDelete(0L);
		}
		else if(-1 == supplierGoods.getIsDelete())
		{
			supplierGoods.setIsDelete(null);
		}
		
		//供货商源文件ID
		supplierGoods.setIdFile(idFile);
		QueryPage queryPage = supplierGoodsService.searchSupplierGoods(supplierGoods, currPage, 30);
		
		List<SupplierGoods> slist = (List<SupplierGoods>)queryPage.getItems();
		for(SupplierGoods s : slist)
		{
			String goodsSize = supplierGoodsService.getGoodsSizeByGoodsId(s.getId());
			s.setSize(goodsSize);
		}
		
		
		 
		model.addAttribute("query",queryPage);
		return "/supplier/goodsViewList";
	}
	
	@RequestMapping("/supplier/editSupplierGoods")
	public String editSupplierGoods(@RequestParam("id")Long id,@RequestParam(value="message",required=false,defaultValue="")String message,Model model)
	{
		SupplierGoods supplierGoods = supplierGoodsService.getGoodsByGoodsId(id);
		List<SupplierGoodsSize> list = supplierGoodsService.getGoodsSizeByGoodsId2List(id);
		supplierGoods.setGoodsSize(list);
		model.addAttribute("message", message);
		model.addAttribute("supplierGoods", supplierGoods);
		return "/supplier/editSupplierGoods";
	}
	
	
	@RequestMapping("/supplier/saveSupplierGoods")
	public String saveSupplierGoods(@ModelAttribute("supplierGoods")SupplierGoods supplierGoods,
			@RequestParam(value="size",required=false,defaultValue="")String size,Model model)
	{
		supplierGoodsService.updateSupplierGoods(supplierGoods);
		
		
		//更新size库存  传入size参数为 id,num;id,num  eg:1:10,2,20
		if(!"".equals(size) && null != size)
		{
			SupplierGoodsSize supplierGoodsSize = new SupplierGoodsSize();
			String[] tmpArr = size.split(";");
			for(int i = 0;i < tmpArr.length;i++)
			{
				String[] temp = tmpArr[i].split(",");
				if(temp.length == 2)
				{
					String sizeId = temp[0];
					String numStr  = temp[1];
					supplierGoodsSize.setId(Long.valueOf(sizeId));
					supplierGoodsSize.setNum(Integer.valueOf(numStr));					
					supplierGoodsService.updateSupplierGoodsSize(supplierGoodsSize);
				}
			}
		}
		model.addAttribute("message", "更新成功");
		return "redirect:/supplier/editSupplierGoods.html?id="+supplierGoods.getId();
	}
	
	
	/**
	 * 上传产品图片
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/supplier/uploadGoodsPic")
	@AdminAccess
	@ResponseBody
	public String uploadGoodsPic(@RequestParam(value="goodsId")Long goodsId,HttpServletRequest req) throws Exception
	{
		MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		MultipartFile gFile = request.getFile("goodsPic");
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		String monthStr = month + "";
		if(month < 10) monthStr = "0"+month;

		String orgImgPath = "supplier/goods/" + year + monthStr;
		String orgPath = path  + Constants.FILE_SEP + orgImgPath;		
		File orgFile = new File(orgPath);
		if(!orgFile.exists()) orgFile.mkdirs();
		String fileName = UUID.randomUUID().toString() + ".jpg";
		String fullPath = orgPath + "/" +  fileName;
		
		OutputStream os = new FileOutputStream(fullPath); 
		InputStream is = gFile.getInputStream();
		int length;
		byte[] b = new byte[2048];
		while((length = is.read(b, 0, b.length)) != -1)
		{
			os.write(b, 0, length);
		}
		os.close();
		is.close();

		//上传完成  压缩
		String org_img = orgImgPath + Constants.FILE_SEP  + fileName;
		String large_img = org_img.replace(".jpg", "_l.jpg");
		String middle_img = org_img.replace(".jpg", "_m.jpg");
		String samll_img = org_img.replace(".jpg", "_s.jpg");

		ImgUtils.reduceImg(fullPath, path +  Constants.FILE_SEP + large_img, 600, 610);
		ImgUtils.reduceImg(fullPath, path +  Constants.FILE_SEP + middle_img, 150, 155);
		ImgUtils.reduceImg(fullPath, path +  Constants.FILE_SEP  + samll_img, 40, 50);
		
		SupplierGoods supplierGoods = new SupplierGoods();
		supplierGoods.setId(goodsId);
		supplierGoods.setImgLarge(large_img);
		supplierGoods.setImgMiddle(middle_img);
		supplierGoods.setImgOriginal(org_img);
		supplierGoods.setImgSmall(samll_img);
		supplierGoodsService.updateSupplierGoods(supplierGoods);
		
		return "_ok_";
	}
	
	
	
}
 
