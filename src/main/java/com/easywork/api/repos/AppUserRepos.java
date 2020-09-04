package com.easywork.api.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easywork.api.entity.AppUser;

@Repository
public interface AppUserRepos extends CrudRepository<AppUser, String> {

	@Query("FROM AppUser u WHERE u.userName =:userName ")
	public AppUser findByUserName(@Param("userName") String userName);
	
	@Query(value =  "SELECT r.role_name FROM app_role r\r\n" + 
			"JOIN \r\n" + 
			"(SELECT ur.role_id as role_id FROM user_role ur where ur.user_id =:userId) as p\r\n" + 
			"ON r.role_id = p.role_id",nativeQuery = true)
	public List<String> getUserRoles(@Param("userId")String userId);
	

}
