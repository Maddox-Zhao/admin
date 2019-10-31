package com.huaixuan.network.biz.service.goods;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.common.util.emisZipUtil;

public interface GoodsBatchManager {

	 /**����excel
	 * @param os �����
	 * @param resultList ����Ľ��,list�з�������
	 * @return
	 */
	public boolean exportExcel(OutputStream os,List<String[]> resultList);
	
	/**����excel ���һ����ͼƬ
	 * @param os �����
	 * @param resultList ����Ľ��,list�з�������
	 * @return
	 */
	public boolean exportExcelWidthPic(OutputStream os,List<String[]> resultList);

	
	/**
	 * @param os
	 * @param resultList
	 * @return
	 */
	public boolean exportExcelWidthNoPic(OutputStream os, List<String[]> resultList);
	
	
	/**
	 * ��excel�е���ݶ��뵽һ��LIST<MAP>������
	 *
	 * @return
	 */
	public List<Object> readExcel(File filePath);

	/**
	 * ���EXCELģ��
	 * @param os
	 * @param sourcefile ģ���ļ�·��
	 * @param attributeList ��Ŀ����
	 * @param catCode ��Ŀ����
	 */
	public void createTitle(OutputStream os,String sourcefile,List<AttributeDTO>  attributeList,String catCode);

	 /**����excel
	 * @param os �����
	 * @param resultList ����Ľ��,��������
	 * list�з�������
	 * @return
	 */
	public boolean exportExcelByObject(OutputStream os, List<Object[]> resultList) ;


	/**���ģ������Ա���ݸ�ʽ����Ʒ��Ϣ
	 * @param os
	 * @param sourcefile
	 * @param listGoods
	 */
	public void exportTaoBaoData(OutputStream os, String sourcefile,List<Goods> listGoods);

	/**����Ա����csv��ʽ����Ʒ��Ϣ
	 * @param os
	 * @param listGoods
	 */
	public void exportCsvData(emisZipUtil zipUtil, String ctx, String filePath,List<Goods> listGoods,String imageService, String fileName);

	/**����������csv��ʽ����Ʒ��Ϣ
	 * @param os
	 * @param listGoods
	 */
	public void exportPaiPaiCsvData(String ctx, OutputStream os,String filePath,List<Goods> listGoods,String imageService);

	/**
	 * ȱ���Ǽǵ���excel
	 */
	public void exportStockOutExcel(OutputStream os, String sourcefile,List<Stockout> listGoods);

	
}
