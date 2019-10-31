package com.huaixuan.network.biz.domain.goods;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;



public class Category extends BaseObject {
	/* @property: */
	private long id;
	/* @property: */
	private String catName;
	/* @property: */
	private String catDesc;
	/* @property: */
	private String picPath;
	/* @property: */
	private String catCode;
	/* @property: */
	private String parentCode;
	private int sortOrder;
	/* @property: */
	private int isShow;
	/* @property: */
	private int childNums;
	/* @property: */
	private int depth;
	/* @property: */
	private int isLeaf;

	private int isSearch;

	private String cache;

	private Category parent;

	private Double commonAgentAgio;//普通代销折扣

	private Double pointProportional;//积分获取比例

	private Set<Category> chidrens = new TreeSet<Category>(
			new Comparator<Category>() {
				public int compare(Category o1, Category o2) {
					if (o1.getId() == o2.getId())
						return 0;
					if (o1.getSortOrder() != o2.getSortOrder())
						return o1.getSortOrder() - o2.getSortOrder();
					return -1;
				}
			});

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChidrens() {
		return chidrens;
	}

	public void setChidrens(Set<Category> chidrens) {
		this.chidrens = chidrens;
	}

	public void addChild(Category entity) {
		if (!this.chidrens.contains(entity)) {
			entity.setParent(this);
			this.chidrens.add(entity);
		}
	}

	/* Default constructor - creates a new instance with no values set. */
	public Category() {
	}

	/* @model: */
	public void setId(long obj) {
		this.id = obj;
	}

	/* @model: */
	public long getId() {
		return this.id;
	}

	/* @model: */
	public void setCatName(String obj) {
		this.catName = obj;
	}

	/* @model: */
	public String getCatName() {
		return this.catName;
	}

	/* @model: */
	public void setCatDesc(String obj) {
		this.catDesc = obj;
	}

	/* @model: */
	public String getCatDesc() {
		return this.catDesc;
	}

	/* @model: */
	public void setPicPath(String obj) {
		this.picPath = obj;
	}

	/* @model: */
	public String getPicPath() {
		return this.picPath;
	}

	/* @model: */
	public void setCatCode(String obj) {
		this.catCode = obj;
	}

	/* @model: */
	public String getCatCode() {
		return this.catCode;
	}

	/* @model: */
	public void setSortOrder(int obj) {
		this.sortOrder = obj;
	}

	/* @model: */
	public int getSortOrder() {
		return this.sortOrder;
	}

	/* @model: */
	public void setIsShow(int obj) {
		this.isShow = obj;
	}

	/* @model: */
	public int getIsShow() {
		return this.isShow;
	}

	/* @model: */
	public void setChildNums(int obj) {
		this.childNums = obj;
	}

	/* @model: */
	public int getChildNums() {
		return this.childNums;
	}

	/* @model: */
	public void setDepth(int obj) {
		this.depth = obj;
	}

	/* @model: */
	public int getDepth() {
		return this.depth;
	}

	/* @model: */
	public void setIsLeaf(int obj) {
		this.isLeaf = obj;
	}

	/* @model: */
	public int getIsLeaf() {
		return this.isLeaf;
	}

	/* {@inheritDoc} */

	/* {@inheritDoc} */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("id", this.id).append(
				"catName", this.catName).append("catDesc", this.catDesc)
				.append("picPath", this.picPath)
				.append("catCode", this.catCode).append("sortOrder",
						this.sortOrder).append("isShow", this.isShow).append(
						"childNums", this.childNums)
				.append("depth", this.depth).append("isLeaf", this.isLeaf);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((catCode == null) ? 0 : catCode.hashCode());
		result = prime * result + ((catDesc == null) ? 0 : catDesc.hashCode());
		result = prime * result + ((catName == null) ? 0 : catName.hashCode());
		result = prime * result + childNums;
		result = prime * result + depth;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + isLeaf;
		result = prime * result + isShow;
		result = prime * result + ((picPath == null) ? 0 : picPath.hashCode());
		result = prime * result + sortOrder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Category other = (Category) obj;
		if (catCode == null) {
			if (other.catCode != null)
				return false;
		} else if (!catCode.equals(other.catCode))
			return false;
		if (catDesc == null) {
			if (other.catDesc != null)
				return false;
		} else if (!catDesc.equals(other.catDesc))
			return false;
		if (catName == null) {
			if (other.catName != null)
				return false;
		} else if (!catName.equals(other.catName))
			return false;
		if (childNums != other.childNums)
			return false;
		if (depth != other.depth)
			return false;
		if (id != other.id)
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (isShow != other.isShow)
			return false;
		if (picPath == null) {
			if (other.picPath != null)
				return false;
		} else if (!picPath.equals(other.picPath))
			return false;
		if (sortOrder != other.sortOrder)
			return false;
		return true;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(int isSearch) {
		this.isSearch = isSearch;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getCache() {
		return cache;
	}

    public Double getCommonAgentAgio() {
        return commonAgentAgio;
    }

    public void setCommonAgentAgio(Double commonAgentAgio) {
        this.commonAgentAgio = commonAgentAgio;
    }

    public Double getPointProportional() {
        return pointProportional;
    }

    public void setPointProportional(Double pointProportional) {
        this.pointProportional = pointProportional;
    }

}
