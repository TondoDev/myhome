package org.tondo.myhome.thyme.presentation;

public class FormAttributes {
	
	public static class FormAttributesBuilder {
		private String method;
		private String action;
		private String backUrl;
		private String submitCaption;
		
		private FormAttributesBuilder() {
		}
		
		public FormAttributesBuilder method(String method) {
			this.method = method;
			return this;
		}
		
		public FormAttributesBuilder action(String action) {
			this.action = action;
			return this;
		}
		
		public FormAttributesBuilder backUrl(String backUrl) {
			this.backUrl =  backUrl;
			return this;
		}
		
		public FormAttributesBuilder submitCaption(String caption) {
			
			this.submitCaption = caption;
			return this;
		}
		
		public FormAttributes create() {
			FormAttributes fa = new FormAttributes();
			
			fa.method = this.method;
			fa.action = this.action;
			fa.backUrl = this.backUrl;
			fa.submitCaption = this.submitCaption;
			
			if (fa.submitCaption == null) {
				fa.submitCaption = "Submit";
			}
			return fa;
		}
	}
	
	
	public static FormAttributesBuilder builder() {
		return new FormAttributesBuilder();
	}
	
	private String method;
	private String action;
	private String backUrl;
	private String submitCaption;
	
	private FormAttributes() {
		
	}

	public String getMethod() {
		return method;
	}

	public String getAction() {
		return action;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public String getSubmitCaption() {
		return submitCaption;
	}
	
	
}
