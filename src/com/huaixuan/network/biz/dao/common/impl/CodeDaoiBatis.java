package com.huaixuan.network.biz.dao.common.impl;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.common.CodeDao;
import com.huaixuan.network.biz.domain.Code;

 /**
  * (bibleUtil auto code generation)
  * @version 3.2.0
  */
@Repository("codeDao")
 public class CodeDaoiBatis implements CodeDao{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

 	 public void addCode(Code code)throws Exception{
 		sqlMapClient.insert("addCode", code);
 	 }

 	 public void editCode(Code code)throws Exception{
 		sqlMapClient.update("editCode", code);
 	 }

 	 public Code getCode(Long codeId)throws Exception{
 		 return (Code)sqlMapClient.queryForObject("getCode", codeId);
 	 }

    public Code getCodeByType(int type) throws Exception {
        return (Code)sqlMapClient.queryForObject("getCodeByType", type);
    }

 }
