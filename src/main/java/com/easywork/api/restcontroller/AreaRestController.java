package com.easywork.api.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easywork.api.entity.AppUser;
import com.easywork.api.entity.Area;
import com.easywork.api.entity.Customer;
import com.easywork.api.securityconfig.IAuthenticationFacade;
import com.easywork.api.serviceimpl.AppUserServiceImpl;
import com.easywork.api.serviceimpl.AreaServiceImpl;

@RestController
@RequestMapping(value = "/areas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AreaRestController {

	@Autowired
	private IAuthenticationFacade authentication;

	@Autowired
	private AppUserServiceImpl appUser;
	@Autowired
	private AreaServiceImpl area;

	@GetMapping(path = { "/{areaId}" }, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaType.ALL_VALUE })
	public ResponseEntity<?> readOne(@PathVariable("areaId") String areaId) {
		AppUser user = this.appUser.findByUserName(authentication.getAuthentication().getName());
		Area area = this.area.getOne(user.getUserId(), areaId);
		System.out.println(areaId);
		return ResponseEntity.ok(area);
	}

	@GetMapping(path = { "" }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> readAll() {
		AppUser prin = appUser.findByUserName(authentication.getAuthentication().getName());
		List<Area> areas = area.getAllByUserId(prin.getUserId());
		if (areas == null) {

		}
		return ResponseEntity.ok(areas);
	}
}
