/**
 * 
 */
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
 * 交易结构
 */
@XmlRootElement(name = "trades_sold_get_response")
public class TaobaoTrade implements Serializable{

	private static final long serialVersionUID = -8410518683112517552L;
	private List<Trade> tradeList;
	
	private Map<String, String> map;

	@XmlElementWrapper(name = "trades")
	@XmlElement(name = "trade")
	public List<Trade> getTradeList(){
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList){
		this.tradeList = tradeList;
	}
	
    public Map<String, String> getMap(){
    	return map;
    }
	
    public void setMap(Map<String, String> map){
    	this.map = map;
    }

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
	}

}
