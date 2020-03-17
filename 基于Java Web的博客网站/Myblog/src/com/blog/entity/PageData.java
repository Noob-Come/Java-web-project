package com.blog.entity;

public class PageData {
	private int currIndex=1;
	private int pageSize=10;
	private int maxCount;
	private int sumPages;
	
	public int getCurrIndex() {
		return currIndex;
	}
	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getSumPages() {
		return (maxCount/pageSize+1);
	}
	
}	

