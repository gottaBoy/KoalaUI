package com.foreveross.koala.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单节点
 * @author 赵健华
 *
 */
public class MenuNode {

	//父节点的ID
	private String parentId;
	
	//菜单节点上的菜单实体
	private Menu menu;
	
	//子节点
	private Map<String,MenuNode> children = new LinkedHashMap<String,MenuNode>();
	
	public MenuNode() {
		// TODO Auto-generated constructor stub
	}
	
	public MenuNode(Menu menu) {
		this.menu = menu;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Map<String,MenuNode> getChildren() {
		return children;
	}

	public void setChildren(Map<String,MenuNode> children) {
		this.children = children;
	}
	
	/**
	 * 增加一个子菜单
	 * @param menu
	 */
	public void addChildMenuNode(MenuNode mn){
		children.put(mn.getMenu().getId(), mn);
	}
	
	/**
	 * 根据子菜单ID获取菜单节点
	 * @param id
	 * @return
	 */
	public MenuNode getChildMenuNode(String id){
		return children.get(id);
	}
	
	/**
	 * 判断是否包含该子菜单
	 * @param menu
	 * @return
	 */
	public boolean hasChildMenuNode(MenuNode mn){
		if(mn==null) return false;
		if(children.containsKey(mn.getMenu().getId())){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断是否包含该子菜单
	 * @param id
	 * @return
	 */
	public boolean hasChildMenuNode(String id){
		return children.containsKey(id);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return menu.toString() + ",parentId = " + parentId + "\n\t children = [" + children + "]";
	}
}
