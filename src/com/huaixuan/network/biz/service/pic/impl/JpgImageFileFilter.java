package com.huaixuan.network.biz.service.pic.impl;

import java.io.File;
import java.io.FileFilter;



/**
 * @author Mr_Yang   2015-8-19 обнГ05:47:47
 **/

public class JpgImageFileFilter implements FileFilter
{

	@Override
	public boolean accept(File arg0)
	{
		if(arg0.getName().endsWith(".jpg")) return true;
		else if(arg0.getName().endsWith(".JPG")) return true;
		return false;
	}

}
 
