package org.openkoala.koalaui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openkoala.koalaui.model.Menu;
import org.openkoala.koalaui.model.MenuNode;
import org.openkoala.koalaui.model.MenuUtil;
import org.openkoala.koalaui.service.MenuService;

import com.google.gson.Gson;

/**
 * Servlet implementation class MenuServlet
 */
public class MenuServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MenuServlet.class);

	private MenuService menuService = new MenuService();
    /**
     * Default constructor. 
     */
    public MenuServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<MenuNode> list = menuService.getAllMenu();
		
		MenuNode mn = MenuUtil.toMenuTree(list);
		list = new ArrayList<MenuNode>();
		list.addAll(mn.getChildren().values());
		
		Menu menu1 = new Menu("1", "测试", "##");
		Menu Menu2 = new Menu("2","随机标题","#");
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu1);
		menuList.add(Menu2);
		
		logger.debug(new Gson().toJson(menuList));
		
		/*List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
		for(int i=0; i<3; i++){
			Map<String,Object> menu = new HashMap<String, Object>();
			menu.put("title", "title"+i);
			menu.put("type", "parent");
			menu.put("href", "href"+i);
			List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
			for(int j=0; j<4; j++){
				Map<String,Object> child = new HashMap<String, Object>();
				child.put("title", "child"+j);
				child.put("type", "children");
				child.put("href", "href"+j);
				children.add(child);
			}
			menu.put("children", children);
			menuList.add(menu);
		}*/
		response.setContentType("text/xml;charset=UTF-8");  
		response.setHeader("Cache-Control", "no-cache");  
		PrintWriter out = response.getWriter();
		out.write(new Gson().toJson(menuList));
		out.flush();
		out.close();
	}

}
