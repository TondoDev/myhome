package org.tondo.myhome.svc.data;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageResultSpringDataImpl<T> implements PageResult<T> {
	
	private Page<T> page;
	
	public PageResultSpringDataImpl(Page<T> page) {
		this.page = page;
	}

	@Override
	public int getCurrentPage() {
		return this.page.getNumber() + 1;
	}

	@Override
	public int getPagesCount() {
		return this.page.getTotalPages();
	}

	@Override
	public int getPageSize() {
		return this.page.getSize();
	}

	@Override
	public int getCurrentPageSize() {
		return this.page.getNumberOfElements();
	}
	
	@Override
	public List<T> getData() {
		return this.page.getContent();
	};

}
