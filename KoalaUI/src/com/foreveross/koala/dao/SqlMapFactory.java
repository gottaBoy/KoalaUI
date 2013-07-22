package com.foreveross.koala.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 单例模式的SqlMapFactory，防止每次都重复读xml
 * @author 赵健华
 *
 */
public class SqlMapFactory {

	private final static Logger log = Logger.getLogger(SqlMapFactory.class);
	private static SqlMapFactory factory = null;
	private String xmlPath = "com/foreveross/koala/sqlmap/SqlMapConfig.xml";
	private SqlMapClient sqlmap = null;
	
	private SqlMapFactory(){
		init();
	}
	
	public synchronized static SqlMapFactory getInstance(){
		if(factory==null){
			factory = new SqlMapFactory();
		}
		
		return factory;
	}
	
	public SqlMapClient getSqlmap() {
		return sqlmap;
	}

	/**
	 * 初始化方法
	 */
	private void init(){
		try {
			String path = this.getClass().getResource("/").getPath();
			log.debug("path = " + path);
			Reader reader = Resources.getResourceAsReader(xmlPath);
			sqlmap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("SqlMapFactory初始化失败",e);
		}
	}
}
