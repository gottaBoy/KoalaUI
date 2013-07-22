package com.foreveross.koala.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGridDao extends BaseDao{
	/** 
	 * 查询所有数据
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectAllData() throws SQLException{
		return sqlmap.queryForList("gridData.selectDataGrid", new HashMap<String, Object>());
	}

	public int getTotalRecordCount(Map param) throws SQLException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(param.get("search") != null){
			map.put("name", "%"+param.get("search")+"%");
		}
		if(param.get("filter") != null && !param.get("filter").equals("all")){
			map.put("population", param.get("filter"));
		}
		return (Integer)sqlmap.queryForObject("gridData.selectDataGridCount", map);
	}

	public List getData(Map<String, Object> condition, int begin, int end) throws SQLException {
		Map<String,Object> map = new HashMap<String, Object>();
		if(condition.get("sortProperty") != null && condition.get("sortDirection") != null){
			map.put("orderBy", condition.get("sortProperty") + " " + condition.get("sortDirection"));
		}else{
			map.put("orderBy", " ID ");
		}
		
		if(condition.get("filter") != null && !condition.get("filter").equals("all")){
			map.put("population", condition.get("filter"));
		}
		
		if(condition.get("search") != null){
			map.put("name", "%"+condition.get("search")+"%");
		}
		
		return sqlmap.queryForList("gridData.selectDataGrid", map, begin , end-begin);
	}
	
}
