package com.foreveross.koala.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.foreveross.koala.dao.MenuDao;
import com.foreveross.koala.model.MenuNode;

public class MenuService {

	private final static Logger logger = Logger.getLogger(MenuService.class);
	
	private MenuDao menuDao = new MenuDao();
	
	public List<MenuNode> getAllMenu(){
		List<MenuNode> list = new ArrayList<MenuNode>();
		
		try {
			list = menuDao.selectAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("MenuService.getAllMenu error:" + e);
		} 
		
		return list;
	}
}
