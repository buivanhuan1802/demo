package com.easywork.api.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easywork.api.entity.AppUser;
import com.easywork.api.repos.AppUserRepos;
import com.easywork.api.service.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

	@Autowired
	private AppUserRepos appUser;

	@Override
	public AppUser findByUserName(String userName) {
		// TODO Auto-generated method stub
		return this.appUser.findByUserName(userName);
	}

	@Override
	public List<String> getUserRoles(String userId) {
		// TODO Auto-generated method stub
		return appUser.getUserRoles(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AppUser appUser = this.findByUserName(userName);
		if (appUser == null) {
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}
		// [ROLE_USER, ROLE_ADMIN,..]
		List<String> roleNames = this.getUserRoles(appUser.getUserId());
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
			System.out.println(role);
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
				appUser.getEncryptedPasword(), grantList);
		return userDetails;
	}

	@Override
	public AppUser getOne(String userId, String objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUser> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppUser> getAllByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createOne(AppUser object) {
		// TODO Auto-generated method stub
		return false;
	}
}
