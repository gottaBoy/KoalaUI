package org.openkoala.koalaui.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openkoala.koalaui.model.Column;
import org.openkoala.koalaui.service.DataGridService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class MenuServlet
 */
public class DataGridServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DataGridServlet.class);

	private DataGridService dataGridService = new DataGridService();
    /**
     * Default constructor. 
     */
    public DataGridServlet() {
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
		Map<String, Object> condition = new Gson().fromJson(request.getParameter("condition"), 
				new TypeToken<Map<String, String>>() {}.getType());
		List<Map<String, Object>> list = dataGridService.getGridData(condition);
		Map<String,Object> result = new HashMap<String, Object>();
		List<Column> columns = getColumns();
		result.put("columns", columns);
		result.put("datas", list);
		result.put("page", dataGridService.getPageContion());
		response.setContentType("text/xml;charset=UTF-8");  
		response.setHeader("Cache-Control", "no-cache");  
		PrintWriter out = response.getWriter();
		out.write(new Gson().toJson(result));
		out.flush();
		out.close();
	}

	private List<Column> getColumns() {
		List<Column> columns = new ArrayList<Column>();
		columns.add(new Column("id","id",false,true));
		columns.add(new Column("name","name",true,false));
		columns.add(new Column("country","country",true,false));
		columns.add(new Column("population","population",true,false));
		return columns;
	}

}
