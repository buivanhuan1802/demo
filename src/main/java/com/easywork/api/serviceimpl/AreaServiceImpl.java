package com.easywork.api.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easywork.api.entity.Area;
import com.easywork.api.repos.AreaRepos;
import com.easywork.api.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaRepos area;

	@Override
	public Area getOne(String userId, String objectId) {
		Area area = this.area.findOneByIdAndUserId(userId, objectId);

		return area;
	}

	@Override
	public List<Area> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Area> getAllByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.area.findAllByUserId(userId);
	}

	@Override
	public boolean createOne(Area object) {
		// TODO Auto-generated method stub
		return false;
	}

}
