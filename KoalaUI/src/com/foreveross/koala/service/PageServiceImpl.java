package com.foreveross.koala.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhaojianhua
 * 
 */
public abstract class PageServiceImpl implements PageService {

	private static final int PAGESIZE = 20;
	private static final int BLOCKSIZE = 2;
	// 每页显示页数
	protected int pageSize = PAGESIZE;
	// 当前位置(块状内)
	protected int pos = 0;
	// 当前位置(绝对值)
	protected int absPos;
	// 开始位置
	protected int beginPos;
	// 结束位置
	protected int endPos;
	// 总记录数
	protected int recordCount;
	// 总页数
	protected int pageCount;
	// 块状大小，每个块内缓存的页数
	protected int blockSize = BLOCKSIZE;
	// 当前块的位置
	protected int blockPos = 0;
	// 开始时的块状的实际位置
	protected int beginBlockPos;
	// 结束时的块状的实际位置
	protected int endBloockPos;
	// 块状区域的个数
	protected int blockCount;
	// 块状数据列表
	protected List blockDataList;
	// 最终在分页里显示的数据列表
	protected List list;
	// 用来标记是否最后一页
	private boolean isLastPage;
	// 用来标记是否第一页
	private boolean isFirstPage;
	
//	private int rdn;

	// Constructor
	public PageServiceImpl() {
//		System.out.println("初始化……");
		isFirstPage = false;
		isLastPage = false;
		this.blockSize = BLOCKSIZE;
//		rdn = new Random().nextInt(10);
	}

	public PageServiceImpl(int pageSize,int blockSize) {

		isFirstPage = false;
		isLastPage = false;

		if(pageSize <= 0){
			this.pageSize = PAGESIZE;
		}else{
			this.pageSize = pageSize;
		}
		if(blockSize <= 0){
			this.blockSize = BLOCKSIZE;
		}else{
			this.blockSize = blockSize;
		}
	}

	/**
	 * 跳到某一页
	 * 
	 * @param 页数
	 * ，从1开始
	 */
	public final List goPage(Map param,int pageNum) throws SQLException {
		// 如果只有一页，那么不用翻页了
		if(isFirstPage && isLastPage){
			// 修正页码
			absPos = 1;
			return list;
		}
		// 如果是0页，直接返回
		if(pageCount == 0){
			return list;
		}
		// 至少是第一页
		if(pageNum <= 0){
			pageNum = 1;
		}
		// 如果超过了最后一页
		if(pageNum >= pageCount){
			pageNum = pageCount;
			isLastPage = true;
		}
		// 设置绝对位置为该页页码
		absPos = pageNum;
		// 必须修正，因为页码是从0开始的
		pageNum--;
		// 获取这个页面的临时块状区域
		int tmpBlockPos = pageNum / blockSize;
		// 在原来的区域内
		if(tmpBlockPos == blockPos){
			// 获取在块内的位置
			pos = pageNum % blockSize;
			beginPos = pageSize * pos;
			endPos = pageSize * (pos + 1);
			// 如果是最后一页
			if(endPos > blockDataList.size()){
				endPos = blockDataList.size();
				isLastPage = true;
				// 如果不止一页
				if(pageCount > 1){
					isFirstPage = false;
				}
			}
			// 如果是第一页
			if(pos == 0 && tmpBlockPos == 0){
				isFirstPage = true;
				// 如果不止一页
				if(pageCount > 1){
					isLastPage = false;
				}
			}
			// 块内分页
			list = blockDataList.subList(beginPos, endPos);
		}else{// 在块外
			blockDataList.clear();
			// 修正当前的块状区域位置
			blockPos = tmpBlockPos;
			// 下一个块
			beginBlockPos = pageSize * blockSize * blockPos;
			endBloockPos = pageSize * blockSize * (blockPos + 1);
			// 重新查询，取第blockSize*pageSize*blockPos至blockSize*pageSize*(blockPos+1)的块数据
			blockDataList = getData(param);
			// 获取在块内的位置
			pos = pageNum % blockSize;
			beginPos = pageSize * pos;
			endPos = pageSize * (pos + 1);
			// 如果是最后一页
			if(endPos > blockDataList.size()){
				endPos = blockDataList.size();
				isLastPage = true;
				// 如果不止一页
				if(pageCount > 1){
					isFirstPage = false;
				}
			}
			// 如果是第一页
			if(pos == 0 && tmpBlockPos == 0){
				isFirstPage = true;
				// 如果不止一页
				if(pageCount > 1){
					isLastPage = false;
				}
			}
			// 块内分页
			list = blockDataList.subList(beginPos, endPos);
		}
		return list;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 * @throws SQLException
	 */
	public final List nextPage(Map param) throws SQLException {
		// TODO Auto-generated method stub
		// 如果只有一页，那么不用翻页了
		if(isFirstPage && isLastPage){
			return list;
		}
		isFirstPage = false;
		// 并非最后一页
		if(!isLastPage){
			absPos++;
			// 刚开始的时候，或者是块内的时候
			if((pos + 1) % blockSize != 0){
				pos++;
				beginPos = pageSize * pos;
				endPos = pageSize * (pos + 1);
				// 如果是最后的记录，肯定是不足endPos*pageSize的
				if(endPos > blockDataList.size()){
					// endPos = blockDataList.size() - beginPos;
					endPos = blockDataList.size();
					isLastPage = true;
				}
				if(beginPos > blockDataList.size()){
					beginPos = blockDataList.size();
					isFirstPage = true;
				}
				// 块内分页
				list = blockDataList.subList(beginPos, endPos);
			}else{
				blockDataList.clear();
				blockPos++;
				// 下一个块
				beginBlockPos = pageSize * blockSize * blockPos;
				endBloockPos = pageSize * blockSize * (blockPos + 1);
				// 重新查询，取第blockSize*pageSize*blockPos至blockSize*pageSize*(blockPos+1)的块数据
				blockDataList = getData(param);
				// 复位pos为0
				pos = 0;
				// 此时的beginPos和endPos实际是从0~blockSize-1
				beginPos = pageSize * pos;
				endPos = pageSize * (pos + 1);
				// 如果是最后的记录，肯定是不足endPos*pageSize的
				if(endPos > blockDataList.size()){
					// endPos = blockDataList.size() - beginPos;
					endPos = blockDataList.size();
					isLastPage = true;
				}
				if(beginPos > blockDataList.size()){
					beginPos = blockDataList.size();
					isFirstPage = true;
				}
				list = blockDataList.subList(beginPos, endPos);
			}
		}
		return list;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 * @throws SQLException
	 */
	public final List prevPage(Map param) throws SQLException {
		// TODO Auto-generated method stub.
		// 如果只有一页，那么不用翻页了
		if(isFirstPage && isLastPage){
			return list;
		}
		// 如果前一页是另一个块状的
		isLastPage = false;
		// 并非第一页
		if(!isFirstPage){
			if(absPos > 1){
				absPos--;
			}
			if(pos % blockSize == 0){
				// 第一块内的第一分页，就是第一页
				if(blockPos == 0){
					isFirstPage = true;
					return list;
				}
				blockDataList.clear();
				blockPos--;
				// 下一个块
				beginBlockPos = pageSize * blockSize * blockPos;
				endBloockPos = pageSize * blockSize * (blockPos + 1);
				// 重新查询，取第blockSize*pageSize*blockPos至blockSize*pageSize*(blockPos+1)的块数据
				blockDataList = getData(param);
				// 复位为blockSize-1
				pos = blockSize - 1;
				beginPos = pageSize * pos;
				endPos = pageSize * (pos + 1);
				// 块内获取
				list = blockDataList.subList(beginPos, endPos);
			}else{
				pos--;
				beginPos = pageSize * pos;
				endPos = pageSize * (pos + 1);
				list = blockDataList.subList(beginPos, endPos);
			}
		}
		return list;
	}

	/**
	 * 最开始页/查询页
	 * 
	 * @return
	 * @throws SQLException
	 */
	public final List search(Map param) throws SQLException {
		// TODO Auto-generated method stub
		init(param);
		blockDataList = new ArrayList();
		// 强制设置为0
		blockPos = 0;
		// ************************
		beginBlockPos = pageSize * blockSize * blockPos;
		endBloockPos = pageSize * blockSize * (blockPos + 1);
		// 开头是取0-blockSize*pageSize左右的数据
		blockDataList = getData(param);

		list = new ArrayList();
		// 如果blockDataList没有数据，直接返回empty数据
		if(blockDataList.isEmpty()){
			return list;
		}
		beginPos = pageSize * pos;
		endPos = pageSize * (pos + 1);
		// 如果只有一页，那么都不能前后分页
		if(recordCount <= pageSize){
			isFirstPage = true;
			isLastPage = true;
			endPos = blockDataList.size();
		}
		list = blockDataList.subList(beginPos, endPos);
		return list;
	}

	/**
	 * 返回数据列表
	 */
	public abstract List getData(Map param);

	/**
	 * 返回数据总数，如果发生异常返回-1
	 */
	public abstract int getTotalRecordCount(Map param);

	/**
	 * 初始化分页
	 * 
	 * @param t
	 */
	protected void init(Map param) {
		// ******************************
		pos = 0;
		absPos = 1;
		isFirstPage = false;
		isLastPage = false;

		recordCount = getTotalRecordCount(param);

		if(recordCount % pageSize == 0){
			pageCount = recordCount / pageSize;
		}else{
			pageCount = recordCount / pageSize + 1;
		}

		if(pageCount % blockSize == 0){
			blockCount = pageCount / blockSize;
		}else{
			blockCount = pageCount / blockSize + 1;
		}
	}

	// ====================Getters and setters=======================
	@Override
	public int getAbsPos() {
		return absPos;
	}

	@Override
	public int getPageCount() {
		return pageCount;
	}

	@Override
	public int getRecordCount() {
		return recordCount;
	}

	
	@Override
	public int getRecordCount(Map param) {
		return recordCount;
	}
}
