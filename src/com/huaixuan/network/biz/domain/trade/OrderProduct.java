package com.huaixuan.network.biz.domain.trade;

public class OrderProduct
{
		private Integer id;
		private Long orderId;
		private Long productId;
		public Integer getId()
		{
			return id;
		}
		public void setId(Integer id)
		{
			this.id = id;
		}
		public Long getOrderId()
		{
			return orderId;
		}
		public void setOrderId(Long orderId)
		{
			this.orderId = orderId;
		}
		public Long getProductId()
		{
			return productId;
		}
		public void setProductId(Long productId)
		{
			this.productId = productId;
		}
		
}
