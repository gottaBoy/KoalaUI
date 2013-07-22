package com.foreveross.koala.model;

public class Page {
	private int totalCount;
	private int totalPage;
	private int pageNo;
	private int pageSize;
	private int beginPos;
	private int endPos;
	
	public Page() {
		super();
	}
	
	
	public Page(int totalCount, int totalPage, int pageNo, int pageSize,
			int beginPos, int endPos) {
		super();
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.beginPos = beginPos;
		this.endPos = endPos;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getBeginPos() {
		return beginPos;
	}
	public void setBeginPos(int beginPos) {
		this.beginPos = beginPos;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + beginPos;
		result = prime * result + endPos;
		result = prime * result + pageNo;
		result = prime * result + pageSize;
		result = prime * result + totalCount;
		result = prime * result + totalPage;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (beginPos != other.beginPos)
			return false;
		if (endPos != other.endPos)
			return false;
		if (pageNo != other.pageNo)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalCount != other.totalCount)
			return false;
		if (totalPage != other.totalPage)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Page [totalCount=" + totalCount + ", totalPage=" + totalPage
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", beginPos=" + beginPos + ", endPos=" + endPos + "]";
	}
}
