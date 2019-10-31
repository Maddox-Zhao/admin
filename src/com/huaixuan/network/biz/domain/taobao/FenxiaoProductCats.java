package com.huaixuan.network.biz.domain.taobao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.huaixuan.network.common.util.remote.TaobaoFenXiaoUtils;

/**
 * 产品�
 */
@XmlRootElement(name = "fenxiao_productcats_get_response")
public class FenxiaoProductCats implements Serializable {

	private static final long serialVersionUID = -3126062637338381733L;
	
	private List<ProductCats> productCatsList;
	
	private String totalResults;
	
	private String errorCode;
	
	private Map<String,String> map;
	
    public Map<String, String> getMap(){
    	return map;
    }
	
    public void setMap(Map<String, String> map){
    	this.map = map;
    }

	public String getErrorCode(){
    	return errorCode;
    }
	
    public void setErrorCode(String errorCode){
    	this.errorCode = errorCode;
    }

	@XmlElementWrapper(name = "productcats")
	@XmlElement(name = "product_cat")
    public List<ProductCats> getProductCatsList(){
    	return productCatsList;
    }

    public void setProductCatsList(List<ProductCats> productCatsList){
    	this.productCatsList = productCatsList;
    }

    @XmlElement(name = "total_results")
    public String getTotalResults(){
    	return totalResults;
    }

    public void setTotalResults(String totalResults){
    	this.totalResults = totalResults;
    }

	public static class ProductCats{

		// 产品线ID
		private Integer id;

		// 产品线名�
		private String name;

		// 产品数量
		private Integer productNum;	// product_num


		/* ====================== 以下getter setter方法 ====================== */

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the productNum
		 */
		@XmlElement(name = "product_num")
		public Integer getProductNum() {
			return productNum;
		}

		/**
		 * @param productNum the productNum to set
		 */
		public void setProductNum(Integer productNum) {
			this.productNum = productNum;
		}
		
		@Override
		public String toString(){
			return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
		}
		
	}
	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
	}

}
