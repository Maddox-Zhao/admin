package com.huaixuan.network.biz.service.goods.impl;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.common.util.emisZipUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author cf
 *
 */
@Service("goodsBatch")
public class GoodsBatchImpl implements GoodsBatchManager {
	Log log = LogFactory.getLog(this.getClass());
	
	private @Value("${file.upload.dir}")
	String upload;

	// private Goods goods;
	// private AttributeManager attrManager;
	// private BrandManager brandManager;
	// private GoodsManager goodsManager;
	// private CategoryManager categoryManager;
	// private StringBuffer sbMsg = new StringBuffer();
	 private java.text.DateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd");
	// private String patternString = "^(0|[1-9][0-9]*)$"; // ����
	// private String parttern = "^(0|[1-9][0-9]*)\\.[0-9]+$"; //������
	// private Pattern patternInt = Pattern.compile(patternString);
	// private Pattern patternNum = Pattern.compile(parttern);
	// private RegionManager regionManager;

	/**
	 *导出excel
	 *
	 * @param os
	 *            输出流
	 * @param resultList
	 *            输出的结果集,list中放入数组
	 * @return
	 */
	public boolean exportExcelByObject(OutputStream os, List<Object[]> resultList) {
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("new Sheet", 0);
			for (int row = 0; row < resultList.size(); row++) {
				Object[] rs = resultList.get(row);
				for (int col = 0; col < rs.length; col++) {
					if (null != rs[col]) {
						if (rs[col] instanceof String) {
							Label label = new jxl.write.Label(col, row, rs[col]
									.toString());
							sheet.addCell(label);
						} else if (rs[col] instanceof Double) {
							jxl.write.Number nLabel = new jxl.write.Number(col,
									row, Double.parseDouble(rs[col].toString()));
							sheet.addCell(nLabel);
						} else if (rs[col] instanceof Date) {
							Date date = (Date) rs[col];
							Label dlabel = new jxl.write.Label(col, row, sf
									.format(date));
							sheet.addCell(dlabel);
						} else {
							jxl.write.Number iLabel = new jxl.write.Number(col,
									row, Integer.parseInt(rs[col].toString()));
							sheet.addCell(iLabel);
						}
					}
				}
			}
			wwb.write();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;

		} catch (WriteException e) {
			log.error(e.getMessage());
			return false;
		} finally {
			if (null != wwb) {
				try {
					wwb.close();
				} catch (WriteException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	public boolean exportExcel(OutputStream os, List<String[]> resultList) {
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("new Sheet", 0);
			for (int row = 0; row < resultList.size(); row++) {
				String[] rs = resultList.get(row);
				for (int col = 0; col < rs.length; col++) {
					if (null != rs[col]) {
						Label label = new jxl.write.Label(col, row, rs[col]);
						sheet.addCell(label);
					}
				}
			}
			wwb.write();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;

		} catch (WriteException e) {
			log.error(e.getMessage());
			return false;
		} finally {
			if (null != wwb) {
				try {
					wwb.close();
				} catch (WriteException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	
	public boolean exportExcelWidthPic(OutputStream os, List<String[]> resultList) {
		WritableWorkbook wwb = null;  
		try {
			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("new Sheet", 0);
			for (int row = 0; row < resultList.size(); row++) {
				String[] rs = resultList.get(row);
				for (int col = 0; col < rs.length; col++) {
					if (null != rs[col] && col<(rs.length-1)) {
						Label label = new jxl.write.Label(col, row, rs[col]);
						sheet.addCell(label);
					}
					if(null == rs[col] || StringUtil.isBlank(rs[col])){
						Label label = new jxl.write.Label(col, row, "");
						sheet.addCell(label);
					}
					if(col==(rs.length-1)){
						if(row==0){
							Label label = new jxl.write.Label(col, row, rs[col]);
							sheet.addCell(label);
						}else{
							//设置行高
							sheet.setRowView( row , 2500 );
							//设置列宽
							sheet.setColumnView( col , 25 );
							File exsitFile = new File(upload + Constants.FILE_SEP + rs[col]);
//							if(StringUtils.isBlank(rs[col])){
//								continue;
//							}
							if (exsitFile.exists()) {
								
								File pngExsit = new File(upload + Constants.FILE_SEP + rs[col].replace(".jpg", ".png"));
								//如果png图片已经存在就不用在生成了
								if (!pngExsit.exists()) {
									RenderedImage   img   =   ImageIO.read(new File( upload + Constants.FILE_SEP + rs[col]));       
									ImageIO.write(img,   "png", new File( upload + Constants.FILE_SEP + rs[col].replace(".jpg", ".png"))); 
								}
								WritableImage ri=new WritableImage(col,row,1,1,new File(upload + 
										Constants.FILE_SEP + rs[col].replace(".jpg", ".png")));   
								sheet.addImage(ri);
							}
						}
					}
				}
			}
			
			wwb.write();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;

		} catch (WriteException e) {
			log.error(e.getMessage());
			return false;
		} finally {
			if (null != wwb) {
				try {
					wwb.close();
				} catch (WriteException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
	@Override
	public boolean exportExcelWidthNoPic(OutputStream os, List<String[]> resultList) {
		WritableWorkbook wwb = null;  
		try {
			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("new Sheet", 0);
			for (int row = 0; row < resultList.size(); row++) {
				String[] rs = resultList.get(row);
				for (int col = 0; col < rs.length; col++) {
					if (null != rs[col] && col<(rs.length-1)) {
						Label label = new jxl.write.Label(col, row, rs[col]);
						sheet.addCell(label);
					}
					if(null == rs[col] || StringUtil.isBlank(rs[col])){
						Label label = new jxl.write.Label(col, row, "");
						sheet.addCell(label);
					}
					if(col==(rs.length-1)){
						if(row==0){
							Label label = new jxl.write.Label(col, row, rs[col]);
							sheet.addCell(label);
						}else{
							//设置行高
							sheet.setRowView( row , 2500 );
							//设置列宽
							sheet.setColumnView( col , 25 );
//							File exsitFile = new File(upload + Constants.FILE_SEP + rs[col]);
//							if(StringUtils.isBlank(rs[col])){
//								continue;
//							}
							/*if (exsitFile.exists()) {
								
								File pngExsit = new File(upload + Constants.FILE_SEP + rs[col].replace(".jpg", ".png"));
								//如果png图片已经存在就不用在生成了
								if (!pngExsit.exists()) {
									RenderedImage   img   =   ImageIO.read(new File( upload + Constants.FILE_SEP + rs[col]));       
									ImageIO.write(img,   "png", new File( upload + Constants.FILE_SEP + rs[col].replace(".jpg", ".png"))); 
								}
								WritableImage ri=new WritableImage(col,row,1,1,new File(upload + 
										Constants.FILE_SEP + rs[col].replace(".jpg", ".png")));   
								sheet.addImage(ri);
							}*/
						}
					}
				}
			}
			
			wwb.write();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;

		} catch (WriteException e) {
			log.error(e.getMessage());
			return false;
		} finally {
			if (null != wwb) {
				try {
					wwb.close();
				} catch (WriteException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	
	/**
	 * ͨ��Ʒ����Ƶõ�Ʒ��id
	 *
	 * @param name
	 * @return
	 */
	public long getBrandIdByName(String brandName) {
		// List<Brand> brand = brandManager.getBrandsByName(brandName);
		// if (null != brand && brand.size() > 0) {
		// return brand.get(0).getId();
		// }
		// return 0;
		return 0;
	}

	/**
	 * �ж��Ƿ�������������
	 *
	 * @param data
	 * @param type
	 * @return
	 */
	public boolean isNumber(String data, String type) {
		// if (null != type && type.equals("int")) {
		// Matcher matcher = patternInt.matcher(data.trim());
		// return matcher.matches();
		// } else {//�Ƿ��������ͣ����ж��Ƿ�����Ȼ���ж��Ƿ񸡵���
		// Matcher matcher = patternInt.matcher(data.trim());
		// if (!matcher.matches()) {
		// Matcher matcherNum = patternNum.matcher(data.trim());
		// return matcherNum.matches();
		// } else {
		// return true;
		// }
		// }
		return false;
	}

	/**
	 * ��ÿ�����ת���ɶ���
	 *
	 * @param sheet
	 * @param row
	 *            getBrandsByName
	 */
	// public boolean rowToGoods(Sheet sheet, int row, Goods goods) {
	// Cell cell;
	// StringBuilder choose = new StringBuilder();
	// String excelHead = this.getText("goods.batch.excel.head.error");
	// if (sbMsg.length() > 0)
	// sbMsg.delete(0, sbMsg.length());//��ǰ��մ�����Ϣ
	// try {
	// cell = sheet.getCell(0, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ��Ŀ����
	// goods.setCatCode(cell.getContents());
	// } else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.cat.code.isnotNull"));
	// return false;
	// }
	// cell = sheet.getCell(1, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) {// ����
	// goods.setTitle(cell.getContents());
	// } else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.title.isnotNull"));
	// return false;
	// }
	// cell = sheet.getCell(2, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) {// ��Ʒ����
	// goods.setGoodsSn(cell.getContents());
	// if (!goodsManager.checkGoodsCode(goods)) {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.goodssn.exist"));
	// return false;
	// }
	// } else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.goodssn.isnotnull"));
	// return false;
	// }
	// cell = sheet.getCell(3, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // Ʒ��ID
	// if (getBrandIdByName(cell.getContents()) != 0)
	// goods.setBrandId(getBrandIdByName(cell.getContents()));
	// else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.brand.notexist"));
	// return false;
	// }
	// }
	// // else{
	// // sbMsg.append("excel�е�"+row+"�еĵ�Ʒ����Ʋ���Ϊ��");
	// // return false;
	// // }
	// cell = sheet.getCell(4, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ��������
	// boolean flag = isNumber(cell.getContents(), "int");
	// if (flag)
	// goods.setSaleNumber(Integer.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.sale.number.type.error"));
	// return false;
	// }
	// } else {
	// goods.setSaleNumber(0);
	// }
	// cell = sheet.getCell(5, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // �г��۸�
	// if (isNumber(cell.getContents().trim(), "double"))
	// goods.setMarketPrice(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.market.price.type.error"));
	// return false;
	// }
	// }
	// cell = sheet.getCell(6, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ���ۼ۸�
	// if (isNumber(cell.getContents(), "double"))
	// goods.setGoodsPrice(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.goods.price.type.error"));
	// return false;
	// }
	// }
	// cell = sheet.getCell(7, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ��۸�
	// if (isNumber(cell.getContents(), "double"))
	// goods.setSalesProPrice(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.sales.pro.price.type.error"));
	// return false;
	// }
	// }
	// cell = sheet.getCell(8, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ����۸�
	// if (isNumber(cell.getContents(), "double"))
	// goods.setWholesalePrice(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.whole.sale.price.type.error"));
	// return false;
	// }
	// }
	//
	// cell = sheet.getCell(9, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) {// ��λ
	// goods.setGoodsUnit(cell.getContents());
	// } else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.goods.unit.isnotnull"));
	// return false;
	// }
	// cell = sheet.getCell(10, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ��Ʒ����
	// goods.setGoodsDesc(cell.getContents());
	// }
	// cell = sheet.getCell(11, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ͼƬ
	// goods.setOriginalImg(cell.getContents());
	// }
	// cell = sheet.getCell(12, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) {// ��Ʒ����
	// if (isNumber(cell.getContents(), "double"))
	// goods.setGoodsWeight(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.goods.weight.error"));
	// return false;
	// }
	// }
	// cell = sheet.getCell(13, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ��������
	// goods.setAttrDesc(cell.getContents());
	// }
	// cell = sheet.getCell(14, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { // ����۸�
	// if (isNumber(cell.getContents(), "double"))
	// goods.setAgentPrice(Double.valueOf(cell.getContents()));
	// else {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.goods.agentPrice.error"));
	// return false;
	// }
	// }
	// cell = sheet.getCell(15, row);
	// if (null != cell && StringUtil.isNotBlank(cell.getContents())) { //�Ƿ����
	// if (cell.getContents().length() > 1) {
	// sbMsg.append(excelHead + (row + 1)
	// + this.getText("goods.batch.goods.isAgent.error"));
	// return false;
	// }
	// goods.setIsAgent(cell.getContents());
	// }
	// goods.setGoodsNumber(0);// ��ʼ�����Ϊ0
	// int column = sheet.getColumns();
	// // String[] attrs = null;
	// // ��Ʒ����
	// StringBuilder attrDesc = new StringBuilder();
	// StringBuilder attrValue = new StringBuilder();
	// //����Ʒ���԰�ҳ���ʽƴװ����
	// for (int i = 16; i < column; i++) {
	// Cell cellValue = sheet.getCell(i, row);
	// if (null != cellValue) {
	// // attrs[i - 14] = cellValue.getContents();
	// if (StringUtil.isNotBlank(cellValue.getContents())) {
	// String[] goodsAttrString = cellValue.getContents()
	// .split(":");
	// Cell cellDesc = sheet.getCell(i, 0);
	// Cell cellCode = sheet.getCell(i, 1);
	// String attributeCode = cellCode.getContents();
	// String attributeDesc = cellDesc.getContents();
	// Attribute attribute = (Attribute) attrManager
	// .getAttributeByAttrCode(attributeCode);
	//
	// if ("checkbox".equalsIgnoreCase(attribute
	// .getInputType().toLowerCase())) {
	// if (attribute.getIsBuyerChoose() == 0) {
	// attrValue.append(attribute.getAttrCode() + ":");
	// attrDesc.append(attribute.getAttrName() + ":");
	// for (int j = 0; j < goodsAttrString.length; j++) {
	// attrDesc.append(goodsAttrString[j]).append(
	// "-");
	// attrValue.append(goodsAttrString[j])
	// .append("-");
	// }
	// // }else{
	// // attrValue.append(attribute.getAttrCode() + ":");
	// // attrDesc.append(attribute.getAttrName() + ":");
	// // for (int j = 0; j < goodsAttrString.length; j++) {
	// // attrDesc.append(goodsAttrString[j]).append("-");
	// // attrValue.append(goodsAttrString[j]).append("-");
	// // }
	// }
	// } else {
	// attrValue.append(attribute.getAttrCode() + ":");
	// attrDesc.append(attribute.getAttrName() + ":");
	// for (int j = 0; j < goodsAttrString.length; j++) {
	// attrDesc.append(goodsAttrString[j]).append("-");
	// attrValue.append(goodsAttrString[j])
	// .append("-");
	// }
	// }
	//
	// // attrDesc.append(attributeDesc).append(":");
	// // attrValue.append(attributeCode).append(":");
	//
	// StringBuilder tempChoose = new StringBuilder();
	//
	// tempChoose.append(attributeCode).append("*");
	// tempChoose.append(attributeDesc).append("*");
	//
	// for (int j = 0; j < goodsAttrString.length; j++) {
	// // attrDesc.append(goodsAttrString[j]).append("-");
	// // attrValue.append(goodsAttrString[j]).append("-");
	// //�ж�����������Ƿ����
	// if (StringUtil
	// .isNotEmpty(attribute.getAttrValues())) {
	// if (attribute.getAttrValues().replaceAll(
	// "\\\r\\\n", "").indexOf(
	// goodsAttrString[j].trim()) == -1) {
	// sbMsg
	// .append(excelHead
	// + row
	// + this
	// .getText("goods.batch.goods.attr.error"));
	// return false;
	// }
	// // if(StringUtil.isNotBlank(attribute.getAttrValues()) && attribute.getAttrValues().replaceAll("\\\r\\\n",
	// "").indexOf(goodsAttrString[j].trim())==-1){
	// // sbMsg.append("excel�е�"+row+"�е�����ֵ�����ڣ�����������");
	// // return false;
	// // }
	// }
	// // if("text"!=attribute.getInputType().trim()){
	// //
	// // }
	// tempChoose.append(goodsAttrString[j]).append("*");
	// }
	//
	// tempChoose.delete(tempChoose.length() - 1, tempChoose
	// .length());
	//
	// // Attribute attribute = attrManager
	// // .getAttributeByAttrCode(attributeCode);
	// if (null != attribute) {
	// // �ж�����ܷ�ѡ�� ���� �ǲ�Ʒ����
	// if (1 == attribute.getIsBuyerChoose()
	// || 1 == attribute.getIsInstance()) {
	// choose
	// .append(tempChoose.append("+")
	// .toString());
	// // goods.setChoose(tempChoose.append("+").toString());
	// }
	// }
	// if (attrDesc.length() > 0) {
	// attrDesc.delete(attrDesc.length() - 1, attrDesc
	// .length());
	// attrDesc.append(";");
	// }
	// if (attrValue.length() > 0) {
	// attrValue.delete(attrValue.length() - 1, attrValue
	// .length());
	// attrValue.append(";");
	// }
	// }
	//
	// }
	// // ������ɲ�Ʒ�ķ���
	// // this.getGoodsInstance(attrs);
	//
	// }
	// if (null != choose) {//
	// goods.setChoose(choose.toString());
	// }
	// if (null != attrValue) {
	// goods.setAttrValue(attrValue.toString());
	// }
	// if (null != attrDesc)
	// goods.setAttrDesc(attrDesc.toString());
	// goods.setGoodsStatus("on_depot");// ��ʼ��״̬Ϊ�ڲֿ���
	// return true;
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// sbMsg.append(e);
	// return false;
	// }
	//
	// }

	/**
	 * �����Ʒѡ���������ɶ�Ӧ�Ĳ�Ʒ
	 *
	 * @return
	 */
	public List<Object> getGoodsInstance(String... attr) {
		// if (null != attr) {
		// int size = attr.length;
		// for (int i = 0; i < size; i++) {
		//
		// }
		// }
		return null;
	}

	/**
	 * ��excel�е���ݶ��뵽һ��LIST<MAP>������
	 *
	 * @return
	 */
	public List<Object> readExcel(File filePath) {
		// InputStream is = null;
		// Workbook rwb = null;
		// List<Object> goodsList = new ArrayList<Object>();
		// try {
		// // ����Workbook����, ֻ��Workbook����
		// // ֱ�Ӵӱ����ļ�����Workbook
		// // ������������Workbook
		// is = new FileInputStream(filePath);
		// // ���workbook ������
		// rwb = Workbook.getWorkbook(is);
		// // Workbook rwb = Workbook.getWorkbook(new File(sourcefile));
		// // ��ȡSheet ������
		// for (int sheetNum = 0; sheetNum < rwb.getNumberOfSheets(); sheetNum++) {
		// Sheet sheet = rwb.getSheet(sheetNum);
		// for (int rowNum = 2; rowNum < sheet.getRows(); rowNum++) {
		// Goods goods = new Goods();
		// boolean flag = rowToGoods(sheet, rowNum, goods);
		// if (!flag) {//��������֤��ͨ����ֱ�ӷ��ش�����Ϣ
		// goodsList.clear();
		// goodsList.add(sbMsg.toString());
		// return goodsList;
		// }
		//
		// goodsList.add(goods);
		// }
		// }
		// rwb.close();
		// is.close();
		// return goodsList;
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// return null;
		// } finally {
		// try {
		// if (null != is)
		// is.close();
		// if (null != rwb)
		// rwb.close();
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }
		return null;
	}

	/**
	 * ���ģ������Ա���ݸ�ʽ����Ʒ��Ϣ
	 *
	 * @param os
	 * @param sourcefile
	 * @param listGoods
	 */
	public void exportTaoBaoData(OutputStream os, String sourcefile, List<Goods> listGoods) {
		// Workbook wb = null;
		// WritableWorkbook wwb = null;
		// try {
		// wb = Workbook.getWorkbook(new File(sourcefile));
		// wwb = Workbook.createWorkbook(os, wb);
		// WritableSheet sheet = wwb.getSheet(0);
		// int temp = 1;//�ӵ�һ�п�ʼ�����
		// jxl.write.Label label;
		// for (Goods list : listGoods) {
		// if (StringUtil.isNotBlank(list.getTitle())) {
		// label = new jxl.write.Label(0, temp, list.getTitle());
		// sheet.addCell(label);//��Ʒ���
		// }
		//
		// if (StringUtil.isNotBlank(list.getCatCode())) {
		// label = new jxl.write.Label(1, temp, categoryManager
		// .getCatFullNameByCatcode(list.getCatCode()));
		// sheet.addCell(label);//��Ʒ��Ŀ
		// }
		// label = new jxl.write.Label(6, temp, "b");
		// sheet.addCell(label);//���۷�ʽ
		//
		// // jxl.write.Number labe = new jxl.write.Number(7, temp, list.getMarketPrice());
		// label = new jxl.write.Label(9, temp, ((Double) list
		// .getMarketPrice()).toString());
		// sheet.addCell(label);//��Ʒ�۸�
		//
		// // labe = new jxl.write.Number(9, temp, list.getGoodsNumber());
		// label = new jxl.write.Label(9, temp, ((Integer) list
		// .getGoodsNumber()).toString());
		// sheet.addCell(label);//��Ʒ����
		//
		// if (null != list.getGmtCreate()) {
		// label = new jxl.write.Label(22, temp, sf.format(list
		// .getGmtCreate()));
		// sheet.addCell(label);//����ʱ��
		// }
		//
		// if (StringUtil.isNotBlank(list.getGoodsDesc())) {
		// label = new jxl.write.Label(24, temp, list.getGoodsDesc());
		// sheet.addCell(label);//��������
		// }
		// if (StringUtil.isNotBlank(list.getImgLarge())) {
		// label = new jxl.write.Label(25, temp, list.getImgLarge());
		// sheet.addCell(label);//����ͼƬ��ַ
		// }
		// if (StringUtil.isNotBlank(list.getAttrDesc())) {
		// label = new jxl.write.Label(26, temp, list.getAttrDesc());
		// sheet.addCell(label);//������������
		// }
		// // if(null!=list.getGmtModify()){
		// // label = new jxl.write.Label(31, temp, sf.format(list.getGmtModify()));
		// // sheet.addCell(label);//�޸�ʱ��
		// // }
		// // if(StringUtil.isNotBlank(list.getGoodsSn())){
		// // label = new jxl.write.Label(39, temp,list.getGoodsSn());
		// // sheet.addCell(label);//��Ʒ����
		// // }
		// temp++;
		// }
		// wwb.write();
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// } finally {
		// if (null != wwb) {
		// try {
		// wwb.close();
		// } catch (WriteException e) {
		// log.error(e.getMessage());
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }
		// if (null != wb) {
		// wb.close();
		// }
		// }

	}

	/**
	 * ȱ���ǼǼ�¼������excel
	 */
	public void exportStockOutExcel(OutputStream os, String sourcefile, List<Stockout> listGoods) {
		// Workbook wb = null;
		// WritableWorkbook wwb = null;
		// try {
		// wb = Workbook.getWorkbook(new File(sourcefile));
		// wwb = Workbook.createWorkbook(os, wb);
		// WritableSheet sheet = wwb.getSheet(0);
		// int temp = 1;//�ӵ�һ�п�ʼ�����
		// jxl.write.Label label;
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �����ʽ����ʾ����
		// for (Stockout list : listGoods) {
		// label = new jxl.write.Label(0, temp, list
		// .getGoodsInstanceName());
		// sheet.addCell(label);//
		// label = new jxl.write.Label(1, temp, list.getGoodsSn());
		// sheet.addCell(label);//
		// label = new jxl.write.Label(2, temp, attrManager
		// .getFullAttributeStringByAttrs(list.getGoodsDesc()));
		// sheet.addCell(label);//
		// label = new jxl.write.Label(3, temp, categoryManager
		// .getCatFullNameByCatcode(list.getCatCode()));
		// sheet.addCell(label);//��Ʒ��Ŀ
		// label = new jxl.write.Label(4, temp, list.getAccount());
		// sheet.addCell(label);//
		// label = new jxl.write.Label(5, temp, list.getUserEmail());
		// sheet.addCell(label);//
		//
		// label = new jxl.write.Label(6, temp, df.format(list
		// .getGmtCreate()));
		// sheet.addCell(label);//
		//
		// String notifyStatus = null;
		// if ("init".equals(list.getNotifyStatus())) {
		// notifyStatus = this
		// .getText("admin.stockout.notifyStatus.no");
		// } else if ("notified".equals(list.getNotifyStatus())) {
		// notifyStatus = this
		// .getText("admin.stockout.notifyStatus.yes");
		// } else {
		// notifyStatus = this
		// .getText("admin.stockout.notifyStatus.nonessary");
		// }
		// label = new jxl.write.Label(7, temp, notifyStatus);
		// sheet.addCell(label);//
		//
		// temp++;
		// }
		// wwb.write();
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// } finally {
		// if (null != wwb) {
		// try {
		// wwb.close();
		// } catch (WriteException e) {
		// log.error(e.getMessage());
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }
		// if (null != wb) {
		// wb.close();
		// }
		// }
	}

	public void exportCsvData(emisZipUtil zipUtil, String ctx, String filePath, List<Goods> listGoods,
			String imageService, String fileName) {
		// try {
		// zipUtil.putNextEntry(new ZipEntry(fileName));
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// OutputStreamWriter op = null;
		// try {
		// op = new OutputStreamWriter(zipUtil,"gbk");
		// } catch (UnsupportedEncodingException e1) {
		// e1.printStackTrace();
		// return ;
		// }
		// CSVWriter csvWriter = new CSVWriter(op, ',', '\u0000', '\u0000');
		// InputStream is = null;
		//
		// try {
		// StringBuilder sb = new StringBuilder();
		// is = new FileInputStream(filePath);
		// InputStreamReader inputReader = new InputStreamReader(is,"gbk");
		// CSVReader reader = new CSVReader(inputReader, ',');
		// String[] headers = reader.readNext();
		// for (String header : headers) {
		// sb.append(header).append(",");
		// }
		// if (sb.toString().endsWith(",")) {
		// sb.delete(sb.length() - 1, sb.length());
		// }
		// String[] tmp = sb.append('\r').toString().split(",");
		// csvWriter.writeNext(tmp);
		// // csvWriter = new CSVWriter(op,',');
		// for (Goods list : listGoods) {
		// if (sb.length() > 0) {//���
		// sb.delete(0, sb.length());
		// }
		// if (null != list.getTitle())//��Ʒ���
		// sb.append(list.getTitle()).append(",");
		// else {
		// sb.append(",");
		// }
		// if (StringUtil.isNotBlank(list.getCatCode())) {//������Ŀ
		// sb.append(
		// categoryManager.getCatFullNameByCatcode(list
		// .getCatCode())).append(",");
		// } else {
		// sb.append(",");
		// }
		// sb.append(",");//������Ŀ
		// sb.append("5").append(",");//�¾ɳ̶�
		// //��ݿ��ÿ�������ҵ��� zhangwy
		// List<AvailableStock> availableStocklist = this.goodsManager
		// .getAvailableStockGroupByGoodsId(list.getId());
		// boolean allblank = true;
		// if (availableStocklist != null && availableStocklist.size() > 0) {
		// for (AvailableStock temp : availableStocklist) {
		// if (temp.getGoodsNumber() > 0) {
		// allblank = false;
		// Region regionDistrict = regionManager
		// .getRegionByCode(temp.getRegionCode());
		// if (null != regionDistrict) {
		// Region regionCity = regionManager
		// .getRegionByCode(regionDistrict
		// .getParentCode());
		// if (null != regionCity) {
		// Region regionProvince = regionManager
		// .getRegionByCode(regionCity
		// .getParentCode());
		// sb.append(regionProvince.getRegionName())
		// .append(",");//ʡ
		// sb.append(regionCity.getRegionName())
		// .append(",");//����
		// break;
		// }
		// }
		// }
		// }
		// } else {
		// allblank = false;
		// sb.append(getText("taobaocsv.province")).append(",");//ʡ
		// sb.append(getText("taobaocsv.city")).append(",");//����
		// }
		// if (allblank) {
		// sb.append(getText("taobaocsv.province")).append(",");//ʡ
		// sb.append(getText("taobaocsv.city")).append(",");//����
		// }
		// sb.append("b").append(",");//���۷�ʽ
		// sb.append(list.getGoodsPrice()).append(",");//�����۸�
		// sb.append("0").append(",");//�Ӽ۷��
		// sb.append("50").append(",");//��������
		// sb.append("7").append(",");//��Ч��
		// sb.append("1").append(",");//�˷ѳе�
		// sb.append("").append(",");//ƽ��
		// sb.append("").append(",");//EMS
		// sb.append("").append(",");//���
		// sb.append("0").append(",");//���ʽ
		// sb.append(",");//֧����
		// sb.append("0").append(",");//��Ʊ
		// sb.append("0").append(",");//����
		//
		// sb.append("1").append(",");//�Զ��ط�
		// sb.append("0").append(",");//����ֿ�
		// sb.append("0").append(",");//�����Ƽ�
		// sb.append(sf.format(list.getGmtCreate())).append(",");//����ʱ��
		// sb.append("").append(",");//�������
		// // sb.append("").append("  ");//��������
		//
		// if (list != null && StringUtils.isNotBlank(list.getGoodsDesc())) {
		// StringBuffer s = new StringBuffer();
		// if (list != null
		// && StringUtils.isNotBlank(list.getAttrDesc())) {
		// String[] decs = null;
		// if (StringUtil.isBlank(list.getAttrDesc())) {
		// decs = list.getAttrDesc().split(";");
		// } else {
		// decs = list.getAttrDesc().replaceAll("\"", "'")
		// .replaceAll(",", "��")
		// .replaceAll("\r", "\t")
		// .replaceAll("\n", "").split(";");
		// }
		//
		// if (decs.length > 0) {
		// s
		// .append("<img src='http://v6.freep.cn/3tb_1002211104169vm6287225.jpg' /><br>");
		// s
		// .append(list.getGoodsWeight() != 0.000 ? getText("goods.batch.goodsWeight")
		// + list.getGoodsWeight() + "<br>"
		// : "");
		// for (int i = 0; i < decs.length; i++) {
		// s.append(decs[i]).append("<br>");
		// }
		// }
		// s
		// .append("<img src='http://v6.freep.cn/3tb_100221110417vnkq287225.jpg' /><br>");
		// s
		// .append(list.getGoodsWeight() != 0.000 ? getText("goods.batch.goodsWeight")
		// + list.getGoodsWeight() + "<br>"
		// : "");
		// }
		// sb
		// .append('"')
		// .append("<p><img src='")
		// .append(ctx)
		// .append("/remoting/isStockout?goodsSn=")
		// .append(list.getGoodsSn())
		// .append("&reqUrl=")
		// .append(ctx)
		// .append("' /></p>")
		// .append(
		// s.toString()
		// + list.getGoodsDesc().replaceAll(
		// "/imageService",
		// imageService).replaceAll(
		// "\"", "'").replaceAll(",",
		// "��").replaceAll("\r", "\t")
		// .replaceAll("\n", ""))
		// .append('"').append(",");//��������
		// } else {
		// sb.append("").append(",");//��������
		// }
		//
		// sb.append(list.getImgLarge()).append(",");//����ͼƬ
		// // sb.append("").append("  ").append("\r\n");//��������
		// sb.append(
		// list.getAttrDesc() == null ? "" : list.getAttrDesc()
		// .replaceAll("\"", "'").replaceAll(",", "��")
		// .replaceAll("\r", "\t").replaceAll("\n", ""))
		// .append(",");//��������
		//
		// sb.append("").append(",");//�Ź���
		// sb.append("").append(",");//��С�Ź�����
		// sb.append("").append(",");//�ʷ�ģ��ID
		// sb.append("").append(",");//��Ա����
		// sb.append("").append(",");//�޸�ʱ��
		// sb.append("").append(",");//�ϴ�״̬
		// sb.append("").append(",");//ͼƬ״̬
		// sb.append("").append(",");//�������
		//
		// if (list != null && StringUtils.isNotBlank(list.getImgLarge())) {
		// String[] des = list.getImgLarge().split("/");
		// StringBuffer ss = new StringBuffer();
		// int i = des.length;
		// if (i > 0) {
		//
		// ss.append(des[i - 1]);
		// ss.append(":0:0:|;");
		// sb.append(ss.toString()).append(",");//��ͼƬ
		// }
		// } else {
		// sb.append("").append(",");//��ͼƬ
		// }
		//
		// sb.append("").append(",");//��Ƶ
		// sb.append("").append(",");//�����������
		// sb.append("").append(",");//�û�����ID��
		// sb.append("").append(",");//�û�������-ֵ��
		// sb.append(list.getGoodsSn()).append(",");//�̼ұ���
		// sb.append("").append(",");//
		// sb.append("");//
		//
		// //sb.append(list.getGoodsSn()).append("  ").append("\r\n");//�̼ұ���
		// String[] tmp1 = sb.append('\r').toString().split(",");
		// csvWriter.writeNext(tmp1);
		// //bw.write(sb.toString());
		// }
		// } catch (Exception e) {
		// this.log.error("error : ", e);
		// } finally {
		// try {
		// csvWriter.close();
		// if (null != is)
		// is.close();
		// } catch (Exception e) {
		// this.log.error("error : ", e);
		// }
		//
		// }
	}

	public void exportPaiPaiCsvData(String ctx, OutputStream os, String filePath, List<Goods> listGoods,
			String imageService) {
		// InputStream is = null;
		// try {
		// StringBuilder sb = new StringBuilder();
		// is = new FileInputStream(filePath); //��������ģ���ļ��ļ�
		// byte[] buffer = new byte[1024];//��buffer
		// int byteread = 0;
		// while ( (byteread = is.read(buffer)) != -1) {
		// os.write(buffer, 0, byteread);
		// }
		// os.write("\r".getBytes("UTF-16LE"));
		// try{
		// if(is != null){
		// is.close();
		// }
		// } catch (Exception e) {
		// this.log.error("error : ", e);
		// }
		//
		// //���ĸ�ʽ���ƴװ
		// for (Goods list : listGoods) {
		// if (sb.length() > 0) {//���
		// sb.delete(0, sb.length());
		// }
		// //id
		// sb.append("-1").append("\t");
		// //��Ʒ���
		// if (null != list.getTitle())
		// addDate(sb,list.getTitle());
		// else {
		// addDate(sb,"");
		// }
		// //���۷�ʽ
		// addDate(sb,"b");
		// //������Ŀ
		// addDate(sb,list.getPaipaiClassId() !=null?list.getPaipaiClassId()+"":"");
		// //������Ŀ
		// addDate(sb,"");
		// //��Ʒ����
		// addDate(sb,"50");
		// //"��Ʒ����"
		// addDate(sb,list.getGoodsWeight()+"");
		// //"��Ч��"
		// addDate(sb,"7");
		// //"��ʱ�ϼ�"
		// addDate(sb,"");
		// //"�¾ɳ̶�"
		// addDate(sb,"1");
		// //"�۸�"
		// addDate(sb,list.getGoodsPrice()+"");
		// //"�Ӽ۷��"
		// addDate(sb,"");
		//
		// //"ʡ"
		// //��ݿ��ÿ�������ҵ��� zhangwy
		// List<AvailableStock> availableStocklist = this.goodsManager
		// .getAvailableStockGroupByGoodsId(list.getId());
		// boolean allblank = true;
		// if (availableStocklist != null && availableStocklist.size() > 0) {
		// for (AvailableStock temp : availableStocklist) {
		// if (temp.getGoodsNumber() > 0) {
		// allblank = false;
		// Region regionDistrict = regionManager
		// .getRegionByCode(temp.getRegionCode());
		// if (null != regionDistrict) {
		// Region regionCity = regionManager
		// .getRegionByCode(regionDistrict
		// .getParentCode());
		// if (null != regionCity) {
		// Region regionProvince = regionManager
		// .getRegionByCode(regionCity
		// .getParentCode());
		// addDate(sb,regionProvince.getRegionName());//ʡ
		// addDate(sb,regionCity.getRegionName());//����
		// break;
		// }
		// }
		// }
		// }
		// } else {
		// allblank = false;
		// sb.append(getText("taobaocsv.province")).append(",");//ʡ
		// sb.append(getText("taobaocsv.city")).append(",");//����
		// }
		// if (allblank) {
		// sb.append(getText("taobaocsv.province")).append(",");//ʡ
		// sb.append(getText("taobaocsv.city")).append(",");//����
		// }
		// //"��"
		// //"�˷ѳе�"
		// addDate(sb,"1");
		// //"ƽ��"
		// addDate(sb,"0");
		// //"���"
		// addDate(sb,"0");
		// //"EMS"
		// addDate(sb,"0");
		// //"��������"
		// addDate(sb,"");
		// //"���ʽ"
		// addDate(sb,"0");
		// //"�з�Ʊ"
		// addDate(sb,"2");
		// //"�б���"
		// addDate(sb,"2");
		// //"֧�ֲƸ�ͨ"
		// addDate(sb,"1");
		// //"�Զ��ط�"
		// addDate(sb,"1");
		// //"����ԭ��"
		// addDate(sb,"");
		// //"ͼƬ"
		// addDate(sb,list.getImgLarge());
		// //"ͼƬ2"
		// addDate(sb,"");
		// //"ͼƬ3"
		// addDate(sb,"");
		// //"ͼƬ4"
		// addDate(sb,"");
		// //"ͼƬ5"
		// addDate(sb,"");
		// //"��Ʒ����"
		// if (list != null && StringUtils.isNotBlank(list.getGoodsDesc())) {
		// StringBuffer s = new StringBuffer();
		// if (list != null
		// && StringUtils.isNotBlank(list.getAttrDesc())) {
		// String[] decs = null;
		// if (StringUtil.isBlank(list.getAttrDesc())) {
		// decs = list.getAttrDesc().split(";");
		// } else {
		// decs = list.getAttrDesc().replaceAll("\"", "'")
		// .replaceAll(",", "��")
		// .replaceAll("\r", "\t")
		// .replaceAll("\n", "").split(";");
		// }
		//
		// if (decs.length > 0) {
		// s.append("<img src='http://v6.freep.cn/3tb_1002211104169vm6287225.jpg' /><br>");
		// s.append(list.getGoodsWeight() != 0.000 ? getText("goods.batch.goodsWeight")
		// + list.getGoodsWeight() + "<br>"
		// : "");
		// for (int i = 0; i < decs.length; i++) {
		// s.append(decs[i]).append("<br>");
		// }
		// }
		// s.append("<img src='http://v6.freep.cn/3tb_100221110417vnkq287225.jpg' /><br>");
		// s.append(list.getGoodsWeight() != 0.000 ? getText("goods.batch.goodsWeight")
		// + list.getGoodsWeight() + "<br>"
		// : "");
		// }
		// sb.append('"')
		// .append("<p><img src='")
		// .append(ctx)
		// .append("/remoting/isStockout?goodsSn=")
		// .append(list.getGoodsSn())
		// .append("&reqUrl=")
		// .append(ctx)
		// .append("' /></p>")
		// .append(
		// s.toString()
		// + list.getGoodsDesc().replaceAll(
		// "/imageService",
		// imageService).replaceAll(
		// "\"", "'").replaceAll(",",
		// "��").replaceAll("\r", "\t")
		// .replaceAll("\n", ""))
		// .append('"').append(",");
		// } else {
		// addDate(sb,"");
		// }
		// addDate(sb,"");
		// //"�ϼ�ѡ��"
		// addDate(sb,"1");
		// //"Ƥ�����"
		// addDate(sb,"0");
		// //"����"
		// addDate(sb,list.getAttrDesc() == null ? "" : list.getAttrDesc()
		// .replaceAll("\"", "'").replaceAll(",", "��")
		// .replaceAll("\r", "\t").replaceAll("\n", ""));
		// //"�ϱ�"
		// addDate(sb,"0");
		// //"��һ����"
		// addDate(sb,"0");
		// //"����"
		// addDate(sb,"0");
		// //"�������"
		// addDate(sb,"");
		// //"��ƷID"
		// addDate(sb,"0");
		// //"�̼ұ���"
		// addDate(sb,list.getGoodsSn());
		// //"�汾"
		// addDate(sb,"");
		//
		// os.write((sb.append('\r').toString()).getBytes("UTF-16LE"));
		// }
		// } catch (Exception e) {
		// this.log.error("error : ", e);
		// } finally {
		//
		// }
	}

	private void addDate(StringBuilder sb, String value) throws UnsupportedEncodingException {
		sb.append("\"");
		sb.append(value);
		sb.append("\"");
		sb.append("\t");
	}

	/**
	 * ���EXCELģ��
	 *
	 * @param os
	 * @param sourcefile
	 *            ģ���ļ�·��
	 * @param attributeList
	 *            ��Ŀ����
	 * @param catCode
	 *            ��Ŀ����
	 */
	public void createTitle(OutputStream os, String sourcefile, List<AttributeDTO> attributeList, String catCode) {
		// Workbook wb = null;
		// WritableWorkbook wwb = null;
		// try {
		// wb = Workbook.getWorkbook(new File(sourcefile));
		// wwb = Workbook.createWorkbook(os, wb);
		// WritableSheet sheet = wwb.getSheet(0);
		// int column = sheet.getColumns();
		//
		// for (AttributeDTO attrList : attributeList) {
		// jxl.write.Label label = new jxl.write.Label(column, 0, attrList
		// .getAttrName());
		// sheet.addCell(label);
		// label = new jxl.write.Label(column, 1, attrList.getAttrCode());
		// sheet.addCell(label);
		// column++;
		// }
		// jxl.write.Label label1 = new jxl.write.Label(0, 2, catCode);
		// sheet.addCell(label1);
		// wwb.write();
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// } catch (WriteException e) {
		// log.error(e.getMessage());
		// } catch (BiffException e) {
		// log.error(e.getMessage());
		// } finally {
		// if (null != wwb) {
		// try {
		// wwb.close();
		// } catch (WriteException e) {
		// log.error(e.getMessage());
		// } catch (IOException e) {
		// log.error(e.getMessage());
		// }
		// }
		// if (null != wb) {
		// wb.close();
		// }
		// }
	}

	// public void editExcel(String sourcefile) {
	// System.out.print(sourcefile);
	// try {
	// // Excel����ļ�
	// Workbook wb = Workbook.getWorkbook(new File(sourcefile));
	// // ��һ���ļ��ĸ���������ָ�����д�ص�ԭ�ļ�
	// WritableWorkbook book = Workbook.createWorkbook(
	// new File("d:/goods2.xls"), wb);
	// WritableSheet sheet = book.getSheet(0);
	// jxl.write.Number number = new jxl.write.Number(2, 3, 24);
	// sheet.addCell(number);
	// jxl.write.Label label2 = new jxl.write.Label(1, 11,
	// "2005.11.5-2005.11.25");
	// sheet.addCell(label2);
	// jxl.write.Label label3 = new jxl.write.Label(1, 12,
	// "2005.11.15-2005.11.25");
	// sheet.addCell(label3);
	// System.out.println("�ɹ�");
	// // ���һ��������
	// // WritableSheet sheet=book.createSheet("�ڶ�ҳ",1);
	//
	// // sheet.addCell(new Label(0,0,"�ڶ�ҳ�Ĳ������"));
	//
	// book.write();
	// System.out.println(book.toString());
	// book.close();
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// java.math.BigDecimal f1 = new java.math.BigDecimal(0.358741);//С������2λ��4��5��
		// f1 = f1.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		// System.out.println(f1);
		// CSVWriter csvWriter = new CSVWriter(op, ',', '\u0000', '\u0000');

		// try {
		// int bytesum = 0;
		// int byteread = 0;
		// File oldfile = new File("D://paipai-template.csv");
		// if (oldfile.exists()) { //�ļ�����ʱ
		// InputStream inStream = new FileInputStream("D://paipai-template.csv"); //����ԭ�ļ�
		// FileOutputStream fs = new FileOutputStream("D://paipai-template2.csv");
		// byte[] buffer = new byte[1444];
		// int length;
		// while ( (byteread = inStream.read(buffer)) != -1) {
		// bytesum += byteread; //�ֽ��� �ļ���С
		// System.out.println(bytesum);
		// fs.write(buffer, 0, byteread);
		// }
		// fs.write("������Ʒ".getBytes("UTF-16LE"));
		// fs.flush();
		// inStream.close();
		// }
		// }
		// catch (Exception e) {
		// System.out.println("���Ƶ����ļ���������");
		// e.printStackTrace();
		//
		// }

		// CSVReader reader = new CSVReader(new FileReader("D://paipai-template.csv"), ',');

		RandomAccessFile bw = new RandomAccessFile(new File("D://paipai-template1.csv"), "rw");
		String s = bw.readLine();

		System.out.println(new String(s.getBytes("UTF-16LE")));
		// bw.skipBytes(s.getBytes().length);
		// bw.writeBytes("-1");bw.writeBytes("\t");
		// bw.writeBytes(new String("\"�������\"".getBytes(),"UTF-16LE"));

		bw.close();
		// reader.readHeaders();

		// String[] headers = reader.readNext();
		// System.out.println(headers.length);
		//
		// for (int i = 0; i < headers.length; i++) {
		//
		// String value = headers[i];
		//
		// System.out.print(value+" ");
		//
		// }
		//
		// System.out.println("");
		//
		// CSVWriter csvWriter = new CSVWriter(new FileWriter("d://test.csv"),
		// ' ', ' ', ' ');
		// csvWriter.writeNext(headers);
		// String[] entries = "first,second,third".split(",");
		// csvWriter.writeNext(entries);

		// List<String[]> list = new ArrayList<String[]>();
		// for (int i = 0; i < headers.length; i++) {
		//
		// String value = headers[i];
		// String[] header = value.split(",");
		//
		// list.add(header);
		// System.out.print(value + " ");
		// }
		//
		// String[] header={"���Ļ��Ʒ�Ȧͷ��","","","","5","",""};
		// list.add(header);
		// csvWriter.writeAll(list);
		// csvWriter.flush();
		// csvWriter.close();
		// System.out.println("");
		// }
		// CSVWriter csvWriter = new CSVWriter(new FileWriter("c://test.csv"));
		// csvWriter.writeAll(arg0, arg1)

		// csvWriter.write("name");
		//
		// csvWriter.write("company");
		//
		// csvWriter.endRecord();
		//
		// csvWriter.write("11");
		//
		// csvWriter.write("12");
		//
		// csvWriter.endRecord();
		//
		// csvWriter.write("21");
		//
		// List<String> goodsList=new ArrayList<String>();
		// goodsList.add("aaaa");
		// goodsList.add("bbbbb");
		// goodsList.add("ccccc");
		// String[]
		// tmp={"<img width='600' height='600' alt='' src='/imageService/fck/image/dfe99ebd61d6418983adbabb4db62f09.JPG' /></p><p><span style='font-size: medium'>����Ʒ������ͯ�������������׶����ɫ����ȷ��ʶ�������ͯ�Ķ��ֶ���������</span><br />","bbbb","cccc"};
		// // CsvFormat cf=
		// csvWriter.writeNext(tmp);
		// String[] tmp1={" 11111111111111 "};
		// csvWriter.writeNext(tmp1);
		//
		//
		// csvWriter.flush();
		//
		// csvWriter.close();
		// System.out.println("�ɹ�");

		// String patternString = "^(0|[1-9][0-9]*)$";// ����
		//
		// String parttern="^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$";//������
		//
		// // String test="^(([0-9]+\\))";
		// String test1="^\\d+(\\.\\d+)?$";
		//
		// String test2="^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$";
		//
		// String test="^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+) ?([0-9]*[1-9][0-9]*))$";
		//
		//
		// // String test3="^(0|[1-9][0-9]*)|((0|[1-9][0-9]*)\\.[0-9]+)$";
		// String test3="^(0|[1-9][0-9]*)\\.[0-9]+$";
		// Pattern pattern = Pattern.compile(patternString);
		//
		// Matcher matcher = pattern.matcher("1");
		// System.out.print(matcher.find());

		// String rs = "/^[0-9]*[1-9][0-9]*$/";����//������
		// var de = /^(-|\+)?\d+(\.\d+)?$/; ��
		// GoodsBatchImpl poi = new GoodsBatchImpl();
		// String[] test={"sss","dsfd","bvds","fsdfd"};
		// String[] test2={"111","222","333","444"};
		// List<String[]> list=new ArrayList<String[]>();
		// list.add(test);
		// list.add(test2);
		// for(int i=0;i<list.size();i++){
		// String[] ss=list.get(i);
		// for(int j=0;j<ss.length;j++){
		// System.out.println( ss[j] );
		// }
		// }
		// boolean flag=poi.exportExcel(list);
		// if(flag){

	}


}
