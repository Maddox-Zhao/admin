package com.huaixuan.network.biz.dao.common;

 import com.huaixuan.network.biz.domain.Code;

 /**
  * @version 3.2.0
  */
 public interface CodeDao {
 	void addCode(Code code) throws Exception;

 	void editCode(Code code) throws Exception;

 	Code getCode(Long codeId) throws Exception;

 	Code getCodeByType(int type) throws Exception;
 }
