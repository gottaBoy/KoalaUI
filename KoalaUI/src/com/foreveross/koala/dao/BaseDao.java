package com.foreveross.koala.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class BaseDao {

	protected SqlMapClient sqlmap;
	
	public BaseDao() {
		// TODO Auto-generated constructor stub
		SqlMapFactory sqlMapFactory = SqlMapFactory.getInstance();
		sqlmap = sqlMapFactory.getSqlmap();
	}
}
