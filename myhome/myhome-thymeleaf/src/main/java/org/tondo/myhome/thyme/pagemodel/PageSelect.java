package org.tondo.myhome.thyme.pagemodel;

import org.tondo.myhome.svc.data.PageResult;

public class PageSelect {
	
	private int shownPages;
	private int currentPage;
	private int pageCount;
	private int firstShown;
	private int lastShown;
	private int pageSize;

	public PageSelect(PageResult<?> paging, int shownPages) {
		this.currentPage = paging.getCurrentPage();
		this.pageCount = paging.getPagesCount();
		this.pageSize = paging.getPageSize();
		this.shownPages = shownPages;
			
		int centeringOffset = this.shownPages % 2 == 0 ? (this.shownPages/2 - 1) : (this.shownPages/2);
		if (this.pageCount <= this.shownPages) {
			this.firstShown = 1;
			this.lastShown = this.pageCount;
		} else {
			int begin = this.currentPage - centeringOffset;
			this.firstShown = begin < 1 ? 1 : begin;
			
			int end = this.firstShown + this.shownPages - 1;
			this.lastShown = end > this.pageCount ? this.pageCount : end;
			if (this.lastShown - this.firstShown <= this.shownPages) {
				this.firstShown = this.lastShown - this.shownPages + 1;
			}
		}
	}
	
	public int getFirstShown() {
		return firstShown;
	}
	
	public int getLastShown() {
		return lastShown;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getPageCount() {
		return pageCount;
	}
}
