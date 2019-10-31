package com.huaixuan.network.biz.domain.taobao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.huaixuan.network.common.util.remote.TaobaoFenXiaoUtils;

@XmlRootElement(name = "fenxiao_orders_get_response")
public class FenxiaoOrders implements Serializable{

	private static final long serialVersionUID = 7841381704820681760L;
	
	private List<PurchaseOrder> purchaseOrders;
	
	private String totalResults;
	private Map<String,String> map;
	
    public Map<String, String> getMap(){
    	return map;
    }
	
    public void setMap(Map<String, String> map){
    	this.map = map;
    }	

	@XmlElement(name = "total_results")
	public String getTotalResults(){
		return totalResults;
	}
	
	@XmlElementWrapper(name = "purchase_orders")
	@XmlElement(name = "purchase_order")
    public List<PurchaseOrder> getPurchaseOrders(){
    	return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders){
    	this.purchaseOrders = purchaseOrders;
    }

	public void setTotalResults(String totalResults){
		this.totalResults = totalResults;
	}

	public static class PurchaseOrder implements Serializable{

		private static final long serialVersionUID = 855443207913725353L;

		/**
		 * 供应商来源网�, values: taobao, alibaba�
		 */
		private String supplierFrom;
		/**
		 * 供应商在来源网站的帐号名�
		 */
		private String supplierUsername;
		/**
		 * 分销商来源网站（暂时只有taobao）�
		 */
		private String distributorFrom;
		/**
		 * 分销商在来源网站的帐号名�
		 */
		private String distributorUsername;
		/**
		 * 买家nick�
		 */
		private String buyerNick;
		/**
		 * 采购单类型（代销、零批） values:daixiao,lingpi�
		 */
		private String type;
		/**
		 * 采购单创建时间�格�yyyy-MM-dd HH:mm:ss
		 */
		private String created;
		/**
		 * 采购单编�
		 */
		private String id;
		/**
		 * 分销商留�
		 */
		private String distributorMemo;
		/**
		 * 支付宝交易号
		 */
		private String alipayNo;
		/**
		 * 采购单�额（不含邮�,精确刍小�单位:元�如:200.07，表�200� )
		 */
		private String totalFee;
		/**
		 * 采购单邮费�(精确刍小�单位:元�如:200.07，表�200� )
		 */
		private String postFee;
		/**
		 * 分销商实付金额�(精确刍小�单位:元�如:200.07，表�200� )
		 */
		private String distributorPayment;
		/**
		 * 订单快照URL
		 */
		private String snapshotUrl;
		/**
		 * 交易状��可选�
		 * <ul>
		 * <li>WAIT_BUYER_PAY(等待买家付款)</li>
		 * <li>WAIT_SELLER_SEND_GOODS(等待卖家发货,�买家已付�)</li>
		 * <li>WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,�卖家已发�)</li>
		 * <li>TRADE_FINISHED(交易成功)</li>
		 * <li>TRADE_CLOSED(交易关闭)</li>
		 *</ul>
		 */
		private String status;
		/**
		 * 付款时间
		 */
		private String payTime;
		/**
		 * 发货时间
		 */
		private String consignTime;
		/**
		 * 交易修改时间
		 */
		private String modified;
		/**
		 * 买家详细的信息（物流相关：收货人 收货地址 邮编 固定电话 移动电话 省份 城市 地区�
		 */
		private Receiver receiver;
		/**
		 * 子订单的详细信息
		 */
		private List<SubPurchaseOrder> subPurchaseOrders;

		public String getId(){
			return id;
		}

		public void setId(String id){
			this.id = id;
		}

		@XmlElement(name = "buyer_nick")
		public String getBuyerNick(){
			return buyerNick;
		}

		public void setBuyerNick(String buyerNick){
			this.buyerNick = buyerNick;
		}

		@XmlElement(name = "distributor_from")
		public String getDistributorFrom(){
			return distributorFrom;
		}

		public void setDistributorFrom(String distributorFrom){
			this.distributorFrom = distributorFrom;
		}

		@XmlElement(name = "distributor_username")
		public String getDistributorUsername(){
			return distributorUsername;
		}

		public void setDistributorUsername(String distributorUsername){
			this.distributorUsername = distributorUsername;
		}

		@XmlElement(name = "distributor_payment")
		public String getDistributorPayment(){
			return distributorPayment;
		}

		public void setDistributorPayment(String distributorPayment){
			this.distributorPayment = distributorPayment;
		}

		public Receiver getReceiver(){
			return receiver;
		}

		public void setReceiver(Receiver receiver){
			this.receiver = receiver;
		}

		@XmlElement(name = "snapshot_url")
		public String getSnapshotUrl(){
			return snapshotUrl;
		}

		public void setSnapshotUrl(String snapshotUrl){
			this.snapshotUrl = snapshotUrl;
		}

		public String getCreated(){
			return created;
		}

		public void setCreated(String created){
			this.created = created;
		}

		public String getModified(){
			return modified;
		}

		public void setModified(String modified){
			this.modified = modified;
		}

		public String getStatus(){
			return status;
		}

		public void setStatus(String status){
			this.status = status;
		}

		public String getType(){
			return type;
		}

		public void setType(String type){
			this.type = type;
		}

		@XmlElement(name = "alipay_no")
		public String getAlipayNo(){
			return alipayNo;
		}

		public void setAlipayNo(String alipayNo){
			this.alipayNo = alipayNo;
		}

		@XmlElement(name = "post_fee")
		public String getPostFee(){
			return postFee;
		}

		public void setPostFee(String postFee){
			this.postFee = postFee;
		}

		@XmlElementWrapper(name = "sub_purchase_orders")
		@XmlElement(name = "sub_purchase_order")
        public List<SubPurchaseOrder> getSubPurchaseOrders(){
        	return subPurchaseOrders;
        }
		
        public void setSubPurchaseOrders(List<SubPurchaseOrder> subPurchaseOrders){
        	this.subPurchaseOrders = subPurchaseOrders;
        }
		
		@XmlElement(name = "supplier_from")
		public String getSupplierFrom(){
			return supplierFrom;
		}
		
		public void setSupplierFrom(String supplierFrom){
			this.supplierFrom = supplierFrom;
		}

		@XmlElement(name = "supplier_username")
		public String getSupplierUsername(){
			return supplierUsername;
		}

		public void setSupplierUsername(String supplierUsername){
			this.supplierUsername = supplierUsername;
		}

		@XmlElement(name = "total_fee")
		public String getTotalFee(){
			return totalFee;
		}

		public void setTotalFee(String totalFee){
			this.totalFee = totalFee;
		}

		public String getDistributorMemo(){
			return distributorMemo;
		}

		public void setDistributorMemo(String distributorMemo){
			this.distributorMemo = distributorMemo;
		}

		@XmlElement(name = "pay_time")
		public String getPayTime(){
			return payTime;
		}

		public void setPayTime(String payTime){
			this.payTime = payTime;
		}

		public String getConsignTime(){
			return consignTime;
		}

		public void setConsignTime(String consignTime){
			this.consignTime = consignTime;
		}

		public static class Receiver{

			private static final long serialVersionUID = 8116027310366432655L;
			/**
			 * 收货人全�
			 */
			private String name;
			/**
			 * 收货地址
			 */
			private String address;
			/**
			 * 邮编
			 */
			private String zip;
			/**
			 * 固定电话
			 */
			private String phone;
			/**
			 * 移动电话
			 */
			private String mobilePhone;
			/**
			 * 省份
			 */
			private String state;
			/**
			 * 城市
			 */
			private String city;
			/**
			 * 地区
			 */
			private String district;

			public String getAddress(){
				return address;
			}

			public void setAddress(String address){
				this.address = address;
			}

			public String getDistrict(){
				return district;
			}

			public void setDistrict(String district){
				this.district = district;
			}

			public String getCity(){
				return city;
			}

			public void setCity(String city){
				this.city = city;
			}

			public String getName(){
				return name;
			}

			public void setName(String name){
				this.name = name;
			}

			@XmlElement(name = "mobile_phone")
			public String getMobilePhone(){
				return mobilePhone;
			}

			public void setMobilePhone(String mobilePhone){
				this.mobilePhone = mobilePhone;
			}

			public String getZip(){
				return zip;
			}

			public void setZip(String zip){
				this.zip = zip;
			}

			public String getPhone(){
				return phone;
			}

			public void setPhone(String phone){
				this.phone = phone;
			}

			public String getState(){
				return state;
			}

			public void setState(String state){
				this.state = state;
			}

			@Override
			public String toString(){
				return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
			}
		}

		public static class SubPurchaseOrder implements Serializable{

			private static final long serialVersionUID = 4483258596428994416L;
			/**
			 * 分销平台上商品id�
			 */
			private String itemId;
			/**
			 * 分销平台上商品商家编码�
			 */
			private String itemOuterId;
			/**
			 * 商品的SKU id�
			 */
			private String skuId;
			/**
			 * SKU商家编码
			 */
			private String skuOuterId;
			/**
			 * SKU属���如: 颜色:红色:别名;尺码:L:别名
			 */
			private String skuProperties;
			/**
			 * 商品购买数量。取值范�大于零的整数
			 */
			private int num;
			/**
			 * 商品标题�
			 */
			private String title;
			/**
			 * 单个商品价格。（精确刍小�单位:元�如:200.07，表�200兆）
			 */
			private String price;
			/**
			 * 快照地址�
			 */
			private String snapshotUrl;
			/**
			 * 创建时间。格� yyyy-MM-dd HH:mm:ss �
			 */
			private String created;
			/**
			 * 状�
			 */
			private String status;
			/**
			 * 子采购单id�
			 */
			private String id;
			/**
			 * 分销商应付金额�（精确刍小�单位:元�如:200.07，表�200兆）
			 */
			private String totalFee;
			/**
			 * 分销商实付金额�（精确刍小�单位:元�如:200.07，表�200兆）
			 */
			private String distributorPayment;
			/**
			 * 买家实付金额。（精确刍小�单位:元�如:200.07，表�200兆）
			 */
			private String buyerPayment;

			@XmlElement(name = "item_id")
			public String getItemId(){
				return itemId;
			}

			public void setItemId(String itemId){
				this.itemId = itemId;
			}

			@XmlElement(name = "item_outer_id")
			public String getItemOuterId(){
				return itemOuterId;
			}

			public void setItemOuterId(String itemOuterId){
				this.itemOuterId = itemOuterId;
			}

			@XmlElement(name = "sku_id")
			public String getSkuId(){
				return skuId;
			}

			public void setSkuId(String skuId){
				this.skuId = skuId;
			}

			@XmlElement(name = "sku_outer_id")
			public String getSkuOuterId(){
				return skuOuterId;
			}

			public void setSkuOuterId(String skuOuterId){
				this.skuOuterId = skuOuterId;
			}

			@XmlElement(name = "sku_properties")
			public String getSkuProperties(){
				return skuProperties;
			}

			public void setSkuProperties(String skuProperties){
				this.skuProperties = skuProperties;
			}

			public int getNum(){
				return num;
			}

			public void setNum(int num){
				this.num = num;
			}

			@XmlElement(name = "buyer_payment")
			public String getBuyerPayment(){
				return buyerPayment;
			}

			public void setBuyerPayment(String buyerPayment){
				this.buyerPayment = buyerPayment;
			}

			@XmlElement(name = "distributor_payment")
			public String getDistributorPayment(){
				return distributorPayment;
			}

			public void setDistributorPayment(String distributorPayment){
				this.distributorPayment = distributorPayment;
			}

			@XmlElement(name = "total_fee")
			public String getTotalFee(){
				return totalFee;
			}

			public void setTotalFee(String totalFee){
				this.totalFee = totalFee;
			}

			public String getCreated(){
				return created;
			}

			public void setCreated(String created){
				this.created = created;
			}

			public String getPrice(){
				return price;
			}

			public void setPrice(String price){
				this.price = price;
			}

			@XmlElement(name = "snapshot_url")
			public String getSnapshotUrl(){
				return snapshotUrl;
			}

			public void setSnapshotUrl(String snapshotUrl){
				this.snapshotUrl = snapshotUrl;
			}

			public String getStatus(){
				return status;
			}

			public void setStatus(String status){
				this.status = status;
			}

			public String getId(){
				return id;
			}

			public void setId(String id){
				this.id = id;
			}

			public String getTitle(){
				return title;
			}

			public void setTitle(String title){
				this.title = title;
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

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
	}
}
