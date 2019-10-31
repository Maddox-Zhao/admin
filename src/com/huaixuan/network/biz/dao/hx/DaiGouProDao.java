package com.huaixuan.network.biz.dao.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.hx.DaiGouPro;

public interface DaiGouProDao
{
	public void addDaiGouPro(DaiGouPro daigouPro);
	
	public List<DaiGouPro> getDaiGouProListByDaiGouCode(String daigouCode);
	
	public void deleteDaiGouProById(Long id);
	
	public void updatDaiGouProByNotNull(DaiGouPro daigouPro);
	
	public DaiGouPro getDaiGouProById(Long id);
}
