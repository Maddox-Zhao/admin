package com.huaixuan.network.biz.domain.goods;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;


/**
 * ��Ʒ����ʱ��,��ʾ����Ŀ����Ʒ�м������������
 * 
 * @author dell
 * 
 */
public class CategoryWithNum extends Category {

	private static final long serialVersionUID = 38709961831344237L;
	private Integer num;

	public CategoryWithNum() {
		super();
	}

	public CategoryWithNum(Category c) {
		super();
		try {
			BeanUtils.copyProperties(this, c);
		} catch (IllegalAccessException ignore) {

		} catch (InvocationTargetException ignore) {

		}
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
