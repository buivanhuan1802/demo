package com.easywork.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.easywork.api.entity.IEntity;

public class CollectionCustom<T extends IEntity> {

	private HashMap<String, T> table;

	public CollectionCustom() {
		table = new HashMap<>();
	}

	public HashMap<String, T> getList() {
		return this.table;
	}

	public void add(String key, T value) {
		table.put(key, value);
	}

	public String toJSONString(String key) {

		

		return null;

	}

}
