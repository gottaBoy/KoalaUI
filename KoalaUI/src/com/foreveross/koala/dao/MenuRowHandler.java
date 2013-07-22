package com.foreveross.koala.dao;

import java.util.ArrayList;
import java.util.List;

import com.foreveross.koala.model.Menu;
import com.foreveross.koala.model.MenuNode;
import com.ibatis.sqlmap.client.event.RowHandler;

public class MenuRowHandler implements RowHandler {

	private List<MenuNode> list = new ArrayList<MenuNode>();
	
	@Override
	public void handleRow(Object obj) {
		// TODO Auto-generated method stub
		Menu menu = (Menu) obj;
		
		MenuNode menuNode = new MenuNode(menu);
		String id = menu.getId();
		String parentId = getParentId(id);
		menuNode.setParentId(parentId);
		
		list.add(menuNode);
	}

	public List<MenuNode> getList() {
		return list;
	}
	
	/**
	 * 通过ID来判断父节点的ID
	 * @param id
	 * @return
	 */
	private static String getParentId(String id){
		int len = id.length();
		String parentId = "root";
		
		if(len==3){
			return "root";
		}else{
			parentId = id.substring(0,len-3);
		}
		
		return parentId;
	}
}
