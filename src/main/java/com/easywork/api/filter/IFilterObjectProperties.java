package com.easywork.api.filter;



public abstract class IFilterObjectProperties {
	private String filterName;
	private String[] properties;
	private String rootName;
	public IFilterObjectProperties(String filterName,String rootName, String[] properties) {
		super();
		this.filterName = filterName;
		this.properties = properties;
		this.rootName = rootName;
	}
	
	
	public String getFilterName() {
		return filterName;
	}



	public String[] getProperties() {
		return properties;
	}


	
	public String getRootName() {
		return rootName;
	}


	public abstract String doFilterProperties();

}
