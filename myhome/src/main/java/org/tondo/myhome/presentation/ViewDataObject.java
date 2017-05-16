package org.tondo.myhome.presentation;

import java.util.HashMap;
import java.util.Map;

public class ViewDataObject<T> {
	
	private T data;
	private Map<String, String> labels;
	
	public ViewDataObject(T obj, Map<String, String> lbls) {
		this.data = obj;
		this.labels = lbls != null ? lbls : new HashMap<String, String>();
	}
	
	public T getData() {
		return data;
	}
	
	public String label(String fieldName) {
		return this.labels.get(fieldName);
	}
}
