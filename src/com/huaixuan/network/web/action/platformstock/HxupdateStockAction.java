package com.huaixuan.network.web.action.platformstock;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remove;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.dao.platformstock.HxPlatformStockUpdateDao;
import com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;
import com.huaixuan.network.biz.service.platformstock.HxStockUpdateService;
import com.huaixuan.network.biz.service.platformstock.StockUpdateService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;


@Controller
@RequestMapping("HxStockUpdate")
public class HxupdateStockAction extends BaseAction{
	@Autowired
	private HxStockUpdateService hxStockUpdateService;
	
	@Autowired
	private PlatformStockUpdateDao platformStockUpdateDao;
	
	@Autowired
    private HxPlatformStockUpdateDao hxPlatformStockUpdateDao;
	
	@Autowired
	private StockUpdateService stockUpdateService;
	
	
	@RequestMapping("/HxStockUpdateList")
	public String HxStockUpdate(Model model,HttpServletRequest request)
	{
		
		return "/autosync/hxStockUpdateList";
	}
	@RequestMapping("/hxSearchStockUpdate")
	@ResponseBody
	public Object HxSearchStockUpdate(Model model,HttpServletRequest request)
	{
		Map<String,String> searchmap = MiniUiUtil.getParameterMap(request);
		
		MiniUiUtil.trimAndConvSpeSqlStr(searchmap, false, true, true); //去除空格
		
		
		
		if(request.getParameter("type")!=null){
		String typee = request.getParameter("type");
		if(typee.length()>0){
		int type = Integer.parseInt(typee);
		if(type==1){
			searchmap.put("type", "hk");
		}else if(type==2){
			searchmap.put("type", "sh");
		}
		}
		}
		//判断商品是上架(1或者2)，下架(0)
		String onsalestatus = request.getParameter("onsalestatus");
		if(StringUtils.isNotBlank(onsalestatus)){
			String[] up = onsalestatus.split("=");
			onsalestatus = up[0];
			String status = up[1];
			searchmap.put("onsalestatus", onsalestatus);
			searchmap.put("statusValue", status);
			
			//前面传来的3，表示商品没有上架过,或上架过被干掉了
			/*if(status.equals("3")){ 
				searchmap.remove("onsalestatus");
				searchmap.put("onsalestatusnever", onsalestatus);
				
			}*/
		}
		
		//查询平台的sku是否为空
		String skuis = request.getParameter("platskuisnotnull");
		if(StringUtils.isNotBlank(skuis)){
			String[] up = skuis.split("=");
			String skuisone = up[0];
			String status = up[1];
			if(status.equals("yes")){
				searchmap.put("platskuisnotnull", skuisone);				
			}else{
				searchmap.remove("platskuisnotnull");
				searchmap.put("platskuisnull", skuisone);
			}	
		}
		
		return hxStockUpdateService.searchStockUpdateHx(searchmap);
	}
	
	
	
	
	@RequestMapping("/exportStockUpdate")
	public void exportStockUpdate(HttpServletRequest request,HttpServletResponse response,AdminAgent adminAgent) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		/*MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);*/
		String[] titleArr = new String[]{"sku","品牌","品名","型号","材质","颜色","尺寸","渠道","当前库存","更新到平台数","平台订单数","考拉sku","寺库sku","天猫sku","京东sku","小红书sku","魅力惠sku","奢品sku","珍品sku","分期乐sku","拼多多sku","Ofashion的sku","银泰sku","魅力惠大于5000的sku","考拉订单数","寺库订单数","天猫订单数","小红书订单数","魅力惠订单数","天猫奢品订单数","珍品订单数","分期乐订单数","拼多多订单数","ofashion订单数","银泰订单数","考拉商品状态","寺库商品状态","天猫商品状态","京东商品状态","小红书商品状态","魅力惠商品状态","天猫奢品商品状态","珍品商品状态","数据生成时间","平台最后下单时间","数据最后更新时间"};
		
		searchMap.put("noStartRowAndEndRow", "yes");//不需要分页
		String sku = searchMap.get("sku");
		String type = searchMap.get("type");
		String platskuisnotnull = searchMap.get("platskuisnotnull");
		String onsalestatus = searchMap.get("onsalestatus");
		String generateTimeStart = searchMap.get("generateTimeStart");
		String generateTimeEnd = searchMap.get("generateTimeEnd");
		
		
		if(StringUtils.isNotBlank(type)){
			String typee = request.getParameter("type");
			if(typee.length()>0){
			int types = Integer.parseInt(typee);
			if(types==1){
				searchMap.put("type", "hk");
			}else if(types==2){
				searchMap.put("type", "sh");
			}
			}
			}else{
				searchMap.remove("type");
			}
			
			if(StringUtils.isNotBlank(onsalestatus)){
				String[] up = onsalestatus.split("=");
				onsalestatus = up[0];
				String status = up[1];
				searchMap.put("onsalestatus", onsalestatus);
				searchMap.put("statusValue", status);
				/*if(status.equals("3")){ //前面传来的3，表示商品没有上架过
					searchMap.remove("onsalestatus");
					searchMap.put("onsalestatusnever", onsalestatus);
					
				}*/
			}else{
				searchMap.remove("onsalestatus");
			}
		
			
			//查询平台的sku是否为空
			if(StringUtils.isNotBlank(platskuisnotnull)){
				String[] up = platskuisnotnull.split("=");
				String skuisone = up[0];
				String status = up[1];
				if(status.equals("yes")){
					searchMap.put("platskuisnotnull", skuisone);				
				}else{
					searchMap.remove("platskuisnotnull");
					searchMap.put("platskuisnull", skuisone);
				}	
			}else{
				searchMap.remove("platskuisnotnull");
			}
		
		
		if(StringUtil.isBlank(sku)){
			searchMap.remove("sku");
			
		}
		
		if(StringUtil.isBlank(generateTimeStart)){
			searchMap.remove("generateTimeStart");
		}
		
		if(StringUtil.isBlank(generateTimeEnd)){
			searchMap.remove("generateTimeEnd");
			
		}
		int count = hxPlatformStockUpdateDao.HxStockUpdatecnt(searchMap);
		if(count > 30000)
		{
			return;
		}
		MiniUiGrid miniuiGrid = hxStockUpdateService.searchStockUpdateHx(searchMap);
		int totalCnt = miniuiGrid.getTotal();
		List<StockUpdate> productList = (List<StockUpdate>)miniuiGrid.getData();//获取需要导出的数据
 
		String[][] resultArr = new String[totalCnt+1][titleArr.length]; //保存查询结果  总行数+1(title行)
		resultArr[0] = titleArr;
		for(int  i = 0; i < resultArr.length-1; i++)
		{
			String[] rowArr = resultArr[i+1]; //第一行为title  不需要在设置
			StockUpdate su = productList.get(i);
			rowArr[0] = su.getSku(); //sku
			
			rowArr[1] = su.getBrandname()+""; //,"品牌",
			rowArr[2] = su.getSeriesname()+""; //,"品名",
			rowArr[3] = su.getTypename()+"";//型号
			rowArr[4] = su.getMaterialname()+"";  //"材质"
			rowArr[5] = su.getColorname()+""; //,"颜色",
			rowArr[6] = su.getSizename()+""; //,"尺寸",
			
			    //渠道 hk/sh
			if(su.getType()!=null && su.getType().equals("sh")){
				rowArr[7] ="上海";
			}else if(su.getType()!=null && su.getType().equals("hk")){
				rowArr[7] ="香港";
			}
			
			
			
			
			rowArr[8] = su.getNowStockNum()+""; //当前库存
			rowArr[9] = su.getLastUpdateStockNum()+"";	//更新到平台数
			rowArr[10] = su.getOrderStockNum()+"";//平台订单数
			rowArr[11] = su.getKaolaSku()+"";//考拉sku
			rowArr[12] = su.getSikuSku()+"";//寺库sku
			rowArr[13] = su.getTmallSku()+"";//天猫sku
			rowArr[14] = su.getJdSku()+"";//"京东sku
			rowArr[15] = su.getXhsSku()+"";//"小红书sku"
			rowArr[16] = su.getMlhSku()+"";//,"魅力惠sku",
			rowArr[17] = su.getShepinSku()+"";//"奢品sku",
			rowArr[18] = su.getZhenpinSku()+"";//"珍品sku",
			rowArr[19] = su.getFqlSku()+"";//"分期乐sku",
			rowArr[20] = su.getPddSku()+""; //拼多多sku
			rowArr[21] = su.getOfashionsku()+""; //ofashion的sku
			rowArr[22] = su.getYinTaiSku()+""; //银泰的sku  ,
			rowArr[23] = su.getMlhnewSku()+""; //"魅力惠大于5000的sku" 
			
			
			rowArr[24] = su.getKaolaOrderStock()+""; //"考拉订单数",
			rowArr[25] = su.getSikuOrderStock()+"";//"寺库订单数",
			rowArr[26] = su.getTmallOrderStock()+"";//"天猫订单数",
			rowArr[27] = su.getXhsOrderStock()+"";//"小红书订单数",
			rowArr[28] = su.getMlhOrderStock()+"";//"魅力惠订单数",
			rowArr[29] = su.getShepinOrderStock()+"";//"天猫奢品订单数",
			rowArr[30] = su.getZhenpinOrderStock()+"";//"珍品订单数",
			rowArr[31] = su.getFqlOrderStock()+""; //"分期乐订单数",
			rowArr[32] = su.getPddOrderStock()+""; //"拼多多订单数",
			rowArr[33] = su.getOfashionOrderStock()+""; //"Ofashion订单数",
			rowArr[34] = su.getYinTaiOrderStock()+""; //银泰订单数
			 //"考拉商品状态",
			if(su.getKaola_on_sale_status()!=null && su.getKaola_on_sale_status().equals("0")){
				rowArr[35] = "已下架";
			}else if(su.getKaola_on_sale_status()!=null && su.getKaola_on_sale_status().equals("1")){
				rowArr[35] = "已上架";
			}else{
				rowArr[35] = "未上架";
			}
			//"寺库商品状态",
			if(su.getSiku_on_sale_status()!=null && su.getSiku_on_sale_status().equals("0")){
				rowArr[36] = "已下架";
			}else if(su.getSiku_on_sale_status()!=null && su.getSiku_on_sale_status().equals("1")){
				rowArr[36] = "已上架";
			}else{
				rowArr[36] = "未上架";
			}
			//天猫商品状态
			if(su.getTmall_on_sale_status()!=null && su.getTmall_on_sale_status().equals("0")){
				rowArr[37] = "已下架";
			}else if(su.getTmall_on_sale_status()!=null && su.getTmall_on_sale_status().equals("1")){
				rowArr[37] = "已上架";
			}else{
				rowArr[37] = "未上架";
			}
			//"京东商品状态",
			if(su.getJd_on_sale_status()!=null && su.getJd_on_sale_status().equals("0")){
				rowArr[38] = "已下架";
			}else if(su.getJd_on_sale_status()!=null && su.getJd_on_sale_status().equals("1")){
				rowArr[38] = "已上架";
			}else{
				rowArr[38] = "未上架";
			}
			 //小红书商品状态
			if(su.getXhs_on_sale_status()!=null && su.getXhs_on_sale_status().equals("0")){
				rowArr[39] = "已下架";
			}else if(su.getXhs_on_sale_status()!=null && su.getXhs_on_sale_status().equals("1")){
				rowArr[39] = "已上架";
			}else{
				rowArr[39] = "未上架";
			}
		  //魅力惠商品状态
			if(su.getMlh_on_sale_status()!=null && su.getMlh_on_sale_status().equals("0")){
				rowArr[40] = "已下架";
			}else if(su.getMlh_on_sale_status()!=null && su.getMlh_on_sale_status().equals("1")){
				rowArr[40] = "已上架";
			}else{
				rowArr[40] = "未上架";
			}
			 //天猫奢品商品状态
			if(su.getShepin_on_sale_status()!=null && su.getShepin_on_sale_status().equals("0")){
				rowArr[41] = "已下架";
			}else if(su.getShepin_on_sale_status()!=null && su.getShepin_on_sale_status().equals("1")){
				rowArr[41] = "已上架";
			}else{
				rowArr[41] = "未上架";
			}
			 //珍品商品状态
			if(su.getZhenpin_on_sale_status()!=null && su.getZhenpin_on_sale_status().equals("0")){
				rowArr[42] = "已下架";
			}else if(su.getZhenpin_on_sale_status()!=null && su.getZhenpin_on_sale_status().equals("1")){
				rowArr[42] = "已上架";
			}else{
				rowArr[42] = "未上架";
			}
			
			rowArr[43] = su.getGenerate_time()+""; //数据生成时间
			rowArr[44] = su.getLastOrderTime()+"";//"平台最后下单时间"
			rowArr[45] = su.getUpdateTime()+"";//"数据最后更新时间"
	
			}
		 //导出excel
        HSSFWorkbook exportWb = new HSSFWorkbook();
        HSSFSheet exportSheet = exportWb.createSheet("sheet0");
        HSSFCellStyle exportStyle = exportWb.createCellStyle();    
        exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        for(int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++)
        {
        	String[] row =  resultArr[rowIndex];
 
    		Row exportRow = exportSheet.createRow(rowIndex);

    		for(int  colIndex = 0;colIndex < row.length;colIndex++)
    		{
         		 exportRow.createCell(colIndex).setCellValue(row[colIndex]);
    		}
    			
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=product_"+sdf1.format(new Date())+".xls");
        OutputStream ouputStream = response.getOutputStream();    
        exportWb.write(ouputStream);
        ouputStream.flush();    
        ouputStream.close(); 
			
		}
	
}
