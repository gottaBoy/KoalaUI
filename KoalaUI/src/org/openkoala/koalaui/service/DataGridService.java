package org.openkoala.koalaui.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openkoala.koalaui.dao.DataGridDao;
import org.openkoala.koalaui.model.Page;


public class DataGridService extends PageServiceImpl{

	private final static Logger logger = Logger.getLogger(DataGridService.class);
	
	private DataGridDao dataGridDao = new DataGridDao();

	private String sortProperty;
	private String sortDirection;
	private String search;
	private String filter;
	
	@Override
	public List getData(Map condition) {
		List list = new ArrayList();
		try {
			list = dataGridDao.getData(condition, this.beginBlockPos, this.endBloockPos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getTotalRecordCount(Map param) {
		try {
			return dataGridDao.getTotalRecordCount(param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Page getPageContion() {
		return new Page(this.recordCount,this.pageCount,
				this.absPos,this.pageSize,this.beginPos+1,this.endPos);
	}

	public List<Map<String, Object>> getGridData(Map<String, Object> condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int pageSize = Integer.valueOf(condition.get("pageSize").toString());
		int pageNum = Integer.valueOf(condition.get("pageIndex").toString());
		Object sortDirection = condition.get("sortDirection");
		Object sortProperty = condition.get("sortProperty");
		Object search = condition.get("search");
		Object filter = condition.get("filter");
		
		try {
			if(this.blockDataList == null || this.pageSize != pageSize
				|| (sortDirection != null && sortProperty != null &&
						(!sortDirection.equals(this.sortDirection)|| !sortProperty.equals(this.sortProperty))) 
				|| (search != null && !search.equals(this.search)) 
				|| (search == null && this.search != null)
				|| (filter != null && !filter.equals(this.filter))){
				this.pageSize = pageSize;
				if(sortDirection != null && sortProperty != null){
					this.sortProperty = sortProperty.toString();
					this.sortDirection = sortDirection.toString();
				}
				if(search != null){
					this.search = search.toString();
				}else{
					this.search = null;
				}
				if(filter != null){
					this.filter = filter.toString();
				}
				list = this.search(condition);
			}else{
				list = this.goPage(condition, pageNum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
