package com.huaixuan.network.biz.domain.storage.query;


/**
 * ø‚¥ÊÃ®’À≤È—Ø¿‡
 * @version 3.2.0
 */
public class StorageLedgerQuery  {

    private String             instanceName;

    private String             goodsInstanceId;

    private String             code;
    
    private String             optTimeStart;
    
	private String             optTimeEnd;
	
	private String             depfirstId;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(String goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOptTimeStart() {
		return optTimeStart;
	}

	public void setOptTimeStart(String optTimeStart) {
		this.optTimeStart = optTimeStart;
	}

	public String getOptTimeEnd() {
		return optTimeEnd;
	}

	public void setOptTimeEnd(String optTimeEnd) {
		this.optTimeEnd = optTimeEnd;
	}

	public String getDepfirstId() {
		return depfirstId;
	}

	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
	}

}
