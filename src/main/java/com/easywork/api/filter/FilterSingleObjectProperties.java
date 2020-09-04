package com.easywork.api.filter;

import org.json.JSONException;
import org.json.JSONObject;

import com.easywork.api.entity.Customer;
import com.easywork.api.entity.IEntity;
import com.easywork.api.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class FilterSingleObjectProperties<T extends IEntity> extends IFilterObjectProperties {
	private T entity;

	public FilterSingleObjectProperties(String filterName, String rootName, String[] properties, T entity) {
		super(filterName, rootName, properties);
		this.entity = entity;
	}

	public String doFilterProperties() {
		FilterProvider filters = new SimpleFilterProvider().addFilter(this.getFilterName(),
				SimpleBeanPropertyFilter.filterOutAllExcept(this.getProperties()));

		try {
			String result = new ObjectMapper().writer(filters).writeValueAsString(this.entity);
			if (getRootName() == null || getRootName().equals("")) {
				return result;
			}
			return new JSONObject().put(this.getRootName(), result).toString();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
