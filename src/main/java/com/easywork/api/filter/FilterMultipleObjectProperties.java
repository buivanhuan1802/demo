package com.easywork.api.filter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.easywork.api.entity.IEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class FilterMultipleObjectProperties<T extends IEntity> extends IFilterObjectProperties {
	private List<T> listObject;

	public FilterMultipleObjectProperties(String filterName, String rootName, String[] properties, List<T> listObject) {
		super(filterName, rootName, properties);
		this.listObject = listObject;
	}

	@Override
	public String doFilterProperties() {
		List<String> result = new ArrayList<String>();
		for (T x : listObject) {
			FilterProvider filters = new SimpleFilterProvider().addFilter(this.getFilterName(),
					SimpleBeanPropertyFilter.filterOutAllExcept(this.getProperties()));

			try {
				String expected = new ObjectMapper().writer(filters).writeValueAsString(x);
				result.add(expected);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result.isEmpty()) {
			return null;
		}
		return new JSONArray(result).toString();
	}
}
