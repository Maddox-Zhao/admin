package com.huaixuan.network.web.action.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.purchase.PurchaseProduct;
import com.huaixuan.network.biz.service.purchase.PurchaseProductService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
import com.huaixuan.network.common.util.miniui.MiniUiUtil;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.common.lang.StringUtil;

@Controller()
@RequestMapping("/purchaseproduct")
public class PurchaseProductAction {
	@Autowired
	PurchaseProductService purchaseProductService;
	
	
	@RequestMapping("/purchaseProducts")
	public String getList(HttpServletRequest request,Model model) {
		model.addAttribute("idPurchaseLifeCycle", request.getParameter("idPurchaseLifeCycle"));
		return "/purchase/purchaseproduct";
	}
	
	
	@RequestMapping("/getAllPurchaseProduct")
	@AdminAccess
	public @ResponseBody
	Object PurchaseProductList(HttpServletRequest request){
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		MiniUiGrid m =  purchaseProductService.getPurchaseProductList(searchMap);
		return m;
	}
	/*@RequestMapping("/exportPurchaseProdut")
	@AdminAccess
	public void exportPurchase(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		String[] titleArr = new String[]{"编号","品牌","品名","型号","材质","颜色","单价","欧洲价","市场价","尚上价","代销价","尚美价","大小","数量","总数量","产地"};
		searchMap.put("noStartRowAndEndRow","yes");
		MiniUiGrid minuiGrid = purchaseProductService.getPurchaseProduct(searchMap);
		int totalCnt = minuiGrid.getTotal();
		List<PurchaseProduct> purchaseProduct = minuiGrid.getData();
		String[][] resultArr = new String[totalCnt+1][titleArr.length];
		resultArr[0] = titleArr;
		for (int i = 0; i < resultArr.length; i++) {
			String[] rowArr = resultArr[i+1];
			PurchaseProduct p = purchaseProduct.get(i);
			rowArr[0] = p.getIdPurchaseProduct()+"";
			rowArr[1] = p.getBrandname();
			rowArr[2] = p.getSeriesname();
			rowArr[3] = p.getType();
			rowArr[4] = p.getMaterial();
			rowArr[5] = p.getColor();
			rowArr[6] = p.getUnitCost()+"";
			rowArr[7] = p.getEUPrice()+"";
			rowArr[8] = p.getCNPrice()+"";
			rowArr[9] = p.getHXPrice()+"";
			rowArr[10] = p.getHKPrice()+"";
			rowArr[11] = p.getHKHXPrice()+"";
			rowArr[12] = p.getSizename();
			rowArr[13] = p.getNumber();
			rowArr[14] = p.getTotalNumber()+"";
			rowArr[15] = p.getOrigin();
			HSSFWorkbook exportWb = new HSSFWorkbook();
			HSSFSheet exportSheet = exportWb.createSheet("sheet0");
			HSSFCellStyle exportStyle = exportWb.createCellStyle();
			exportStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for (int  rowIndex = 0;rowIndex < resultArr.length;rowIndex++) {
				String[] row =  resultArr[rowIndex];
				Row exportRow = exportSheet.createRow(rowIndex);
				for (int  colIndex = 0;colIndex < row.length;colIndex++) {
					exportRow.createCell(colIndex).setCellValue(row[colIndex]);
				}
			}
			response.setContentType("application/vnd.ms-excel");    
	        response.setHeader("Content-disposition", "attachment;filename=PurchaseProduct_"+System.currentTimeMillis()+".xls");
	        OutputStream ouputStream = response.getOutputStream();    
	        exportWb.write(ouputStream);
	        ouputStream.flush();
	        ouputStream.close();
		}
	}
	*/
	@RequestMapping("/getAllPurchase")
	@AdminAccess
	public @ResponseBody
	String PurchaseProductLists(HttpServletRequest request,
			@ModelAttribute("purchaseProduct")PurchaseProduct purchaseProduct){
		int idBrand = Integer.valueOf(request.getParameter("idBrand"));
		int idSeries =Integer.valueOf(request.getParameter("idSeries"));
		int idSize = Integer.valueOf(request.getParameter("idSize"));
		int totalNumber =Integer.valueOf(request.getParameter("totalNumber"));
		int idPurchaseProduct = Integer.valueOf(request.getParameter("idPurchaseProduct"));
		Double unitCost =Double.valueOf(request.getParameter("unitCost"));
		Double euprice =Double.valueOf(request.getParameter("euprice"));
		Double cnprice =Double.valueOf(request.getParameter("cnprice"));
		Double hxprice =Double.valueOf(request.getParameter("hxprice"));
		Double hkprice =Double.valueOf(request.getParameter("hkprice"));
		Double hkhxprice =Double.valueOf(request.getParameter("hkhxprice"));
		purchaseProduct.setUnitCost(unitCost);
		purchaseProduct.setIdPurchaseProduct(idPurchaseProduct);
		purchaseProduct.setIdBrand(idBrand);
		purchaseProduct.setTotalNumber(totalNumber);
		purchaseProduct.setEUPrice(euprice);
		purchaseProduct.setCNPrice(cnprice);
		purchaseProduct.setHXPrice(hxprice);
		purchaseProduct.setHKPrice(hkprice);
		purchaseProduct.setHKHXPrice(hkhxprice);
		purchaseProduct.setIdSeries(idSeries);
		purchaseProduct.setIdSize(idSize);
		purchaseProduct.setNumber(request.getParameter("number"));
		purchaseProduct.setType(request.getParameter("type"));
		purchaseProduct.setMaterial(request.getParameter("material"));
		purchaseProduct.setColor(request.getParameter("color"));
		purchaseProduct.setOrigin(request.getParameter("origin"));
		purchaseProductService.updatePurchaseProductf(purchaseProduct);
		return "ok";
	}
	@RequestMapping("/addPurchase")
	@AdminAccess
	public @ResponseBody
	String AddPurchaseProduct(HttpServletRequest request,
			@ModelAttribute("purchaseProduct")PurchaseProduct purchaseProduct){
		int idBrand = Integer.valueOf(request.getParameter("idBrand"));
		int idSeries =Integer.valueOf(request.getParameter("idSeries"));
		int idSize = Integer.valueOf(request.getParameter("idSize"));
		int totalNumber =Integer.valueOf(request.getParameter("totalNumber"));
		int idPurchaseLifeCycle = Integer.valueOf(request.getParameter("idPurchaseLifeCycle"));
		Double unitCost =Double.valueOf(request.getParameter("unitCost"));
		Double euprice =Double.valueOf(request.getParameter("euprice"));
		Double cnprice =Double.valueOf(request.getParameter("cnprice"));
		Double hxprice =Double.valueOf(request.getParameter("hxprice"));
		Double hkprice =Double.valueOf(request.getParameter("hkprice"));
		Double hkhxprice =Double.valueOf(request.getParameter("hkhxprice"));
		purchaseProduct.setUnitCost(unitCost);
		purchaseProduct.setIdPurchaseLifeCycle(idPurchaseLifeCycle);
		purchaseProduct.setIdBrand(idBrand);
		purchaseProduct.setTotalNumber(totalNumber);
		purchaseProduct.setEUPrice(euprice);
		purchaseProduct.setCNPrice(cnprice);
		purchaseProduct.setHXPrice(hxprice);
		purchaseProduct.setHKPrice(hkprice);
		purchaseProduct.setHKHXPrice(hkhxprice);
		purchaseProduct.setIdSeries(idSeries);
		purchaseProduct.setIdSize(idSize);
		purchaseProduct.setNumber(request.getParameter("number"));
		purchaseProduct.setType(request.getParameter("type"));
		purchaseProduct.setMaterial(request.getParameter("material"));
		purchaseProduct.setColor(request.getParameter("color"));
		purchaseProduct.setOrigin(request.getParameter("origin"));
		purchaseProductService.addAddPurchaseProduct(purchaseProduct);
		return "ok";
	}
	@RequestMapping("/getPurchaseProductTotlPrice")
	public @ResponseBody
	Object getPurchaseProductTotlPrice(HttpServletRequest request){
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		searchMap.put("idPurchaseLifeCycle", request.getParameter("idPurchaseLifeCycle"));
		return purchaseProductService.getPurchaseProductTotlPrice(searchMap);
		 
	}
	@RequestMapping("/getPurchaseProducttotalNumber")
	public @ResponseBody
	Object getPurchaseProducttotalNumber(HttpServletRequest request){
		Map<String,String> searchMap = MiniUiUtil.getParameterMap(request);
		MiniUiUtil.trimAndConvSpeSqlStr(searchMap, true);
		searchMap.put("idPurchaseLifeCycle", request.getParameter("idPurchaseLifeCycle"));
		return purchaseProductService.getPurchaseProducttotalNumber(searchMap);
		
	}
	@RequestMapping("/deletePurchaseProduct")
	public @ResponseBody Object deletePurchaseProduct(HttpServletRequest request){
		Map<String,String> map =MiniUiUtil.getParameterMap(request);
		String requestStr = map.get("idPurchaseProduct");
		if(StringUtil.isNotBlank(requestStr)){

			String[] arr = requestStr.split(",");
			for (String s : arr) {
				PurchaseProduct purchaseProduct = new PurchaseProduct();
				try {
					int idPurchaseProduct =Integer.parseInt(s);
					purchaseProduct.setIdPurchaseProduct(idPurchaseProduct);
					purchaseProductService.deletePurchaseProduct(purchaseProduct);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return "OK";
		
	}
}
