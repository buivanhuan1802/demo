package com.easywork.api.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easywork.api.entity.Area;

@Repository
public interface AreaRepos extends CrudRepository<Area, String> {

	@Query(value = "SELECT a.area_id,a.area_name FROM area a WHERE a.user_id =:userId", nativeQuery = true)
	public List<Area> findAllByUserId(@Param("userId") String userId);

	@Query(value = "SELECT a.area_id,a.area_name FROM area a "
			+ "WHERE a.user_id =:userId AND a.area_id =:areaId", nativeQuery = true)
	public Area findOneByIdAndUserId(@Param("userId") String userId, @Param("areaId") String areaId);

}
