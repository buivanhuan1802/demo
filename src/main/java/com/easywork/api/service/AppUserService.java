package com.easywork.api.service;

import java.util.List;

import com.easywork.api.entity.AppUser;

public interface AppUserService extends IEntityService<AppUser>{

	public AppUser findByUserName(String userName);
	public List<String> getUserRoles(String userId);

}
