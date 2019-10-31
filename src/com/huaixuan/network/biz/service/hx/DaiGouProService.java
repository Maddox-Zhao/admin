package com.huaixuan.network.biz.service.hx;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.DaiGouPro;

public interface DaiGouProService
{
	public void addDaiGouPro(DaiGouPro daigouPro,AdminAgent adminAgent);
	
	public void updateDaiGouProByNotNull(DaiGouPro daigouPro);
	
	public void deleteDaiGouProById(Long id);
	
	public DaiGouPro getDaiGouProById(Long id);
	
	public List<DaiGouPro> getDaiGouProByDaiGouCode(String daigouCode);
	
	public void draw(DaiGouPro daiGouPro,AdminAgent adminAgent,String type,Double money);
}	
