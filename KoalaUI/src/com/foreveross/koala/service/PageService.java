package com.foreveross.koala.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PageService {
	/**
	 * Goto the page with pageNum
	 * 
	 * @param T
	 * @param pageNum
	 */
	public List goPage(Map param,int pageNum) throws SQLException;

	/**
	 * Next page
	 * 
	 * @return Data List of next page
	 * @throws SQLException
	 */
	public List nextPage(Map param) throws SQLException;

	/**
	 * Previous page
	 * 
	 * @return Date List of Previous page
	 * @throws SQLException
	 */
	public List prevPage(Map param) throws SQLException;

	/**
	 * First page / Requery page
	 * 
	 * @return Data List queried from DAO
	 * @throws SQLException
	 */
	public List search(Map param) throws SQLException;

	/**
	 * Get date from DAO
	 * 
	 * @param t
	 * @return
	 */
	public List getData(Map param);

	/**
	 * Get total record count of tables
	 * 
	 * @param t
	 * @return
	 */
	public int getTotalRecordCount(Map param);

	/**
	 * Get absolute page position
	 * 
	 * @return
	 */
	public int getAbsPos();

	/**
	 * Get total page count
	 * 
	 * @return
	 */
	public int getPageCount();

	/**
	 * Get total page count
	 * 
	 * @return
	 */
	public int getRecordCount();
	
	/**
	 * Get total record count
	 * 
	 * @return
	 */
	public int getRecordCount(Map param);
}
