package com.easywork.api.service;

import java.util.List;

import com.easywork.api.entity.IEntity;

public interface IEntityService<T extends IEntity> {

	public T getOne(String userId, String objectId);

	public List<T> getAll();

	public List<T> getAllByUserId(String userId);

	public boolean createOne(T object);
}
