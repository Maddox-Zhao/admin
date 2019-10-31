package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.AttrGroup;

 public interface AttrGroupManager{
	 	/* @interface model: AttrGroup */ 
	 	public void addAttrGroup(AttrGroup attrGroup); 
	  
	 	/* @interface model: AttrGroup */ 
	 	public void editAttrGroup(AttrGroup attrGroup); 
	  
	 	/* @interface model: AttrGroup */ 
	 	public void removeAttrGroup(Long attrGroupId); 
	  
	 	/* @interface model: AttrGroup,AttrGroup */ 
	 	public AttrGroup getAttrGroup(Long attrGroupId); 
	  
	 	/* @interface model: AttrGroup,AttrGroup */ 
	 	public List<AttrGroup> getAttrGroups();
 }
