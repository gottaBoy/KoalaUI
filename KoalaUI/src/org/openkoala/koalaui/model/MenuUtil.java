package org.openkoala.koalaui.model;

import java.util.List;

import org.apache.log4j.Logger;

public class MenuUtil {

	private final static Logger logger = Logger.getLogger(MenuUtil.class);
	/**
	 * 转换列表为树形
	 * @param menuList
	 */
	public static MenuNode toMenuTree(List<MenuNode> menuList){
		
		Menu root = new Menu();
		root.setId("root");
		root.setTitle("系统菜单");
		root.setHref("#");
		
		MenuNode rootNode = new MenuNode(root);
		
		for(MenuNode mn:menuList){
			int len = mn.getParentId().length();
			
			if(mn.getParentId().equalsIgnoreCase("root")){
				rootNode.addChildMenuNode(mn);
			}else{
				MenuNode tmpNode = rootNode;
				
//				rootNode.getChildMenuNode("001").getChildMenuNode("001001")
				for(int i=0;i<len/3;i++){
					String id = mn.getMenu().getId().substring(0, (i+1) * 3);		//001,001001
					
					tmpNode = tmpNode.getChildMenuNode(id);
				}
				
				tmpNode.addChildMenuNode(mn);
			}
		}
		
		//Menu [id=root, title=系统菜单, href=#],
		//	children = [{001=Menu [id=001, title=权限管理, href=#],children = [{001003=Menu [id=001003, title=菜单管理, href=#],children = [{}], parentId = 001, 001001=Menu [id=001001, title=用户管理, href=#],children = [{001001001=Menu [id=001001001, title=添加新用户, href=#],children = [{}], parentId = 001001, 001001002=Menu [id=001001002, title=系统用户查询, href=#],children = [{}], parentId = 001001}], parentId = 001, 001002=Menu [id=001002, title=角色管理, href=#],children = [{}], parentId = 001}], parentId = root, 002=Menu [id=002, title=系统管理, href=#],children = [{002001=Menu [id=002001, title=功能配置, href=#],children = [{}], parentId = 002, 002002=Menu [id=002002, title=系统访问记录, href=#],children = [{}], parentId = 002}], parentId = root, 003=Menu [id=003, title=日志管理, href=#],children = [{003001=Menu [id=003001, title=日志查询, href=#],children = [{}], parentId = 003}], parentId = root}], parentId = null
		logger.debug("rootNode = " + rootNode);
		
		return rootNode;
	}
}
