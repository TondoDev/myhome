package org.tondo.myhome.svc.data;

import java.util.List;

/**
 * Wrapper over output paging structure from used data framework 
 * 
 * @author Anton Zsíros
 *
 * @param <T> type of elements
 */
public interface PageResult<T> {
	
	int getCurrentPage();
	
	int getPagesCount();
	
	int getPageSize();
	
	int getCurrentPageSize();
	
	List<T> getData();
}
