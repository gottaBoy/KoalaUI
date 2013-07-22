package com.foreveross.koala.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foreveross.koala.model.Menu;
import com.foreveross.koala.model.MenuNode;

public class MenuDao extends BaseDao{

	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 * @throws SQLException
	 */
	public int insert(Menu menu) throws SQLException{
		Object obj = sqlmap.insert("insert",menu);
		if(obj!=null){
			return (Integer) obj;
		}
		
		return 0;
	}
	
	/**
	 * 查询所有菜单
	 * @return
	 * @throws SQLException
	 */
	public List<MenuNode> selectAll() throws SQLException{
		List<MenuNode> list = new ArrayList<MenuNode>();
		MenuRowHandler handler = new MenuRowHandler();
		
		sqlmap.queryWithRowHandler("menu.selectMenu",handler);
		
		list = handler.getList();
		
		return list;
	}
}
