package com.easywork.api.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easywork.api.entity.AppUser;
import com.easywork.api.entity.Customer;
import com.easywork.api.entity.Invoice;
import com.easywork.api.exception.CustomerNotFoundException;
import com.easywork.api.exception.IdentityNotGivenException;
import com.easywork.api.exception.NoResponseContentException;
import com.easywork.api.securityconfig.IAuthenticationFacade;
import com.easywork.api.serviceimpl.AppUserServiceImpl;
import com.easywork.api.serviceimpl.CustomerServiceImpl;

@RestController
@RequestMapping(value = "/customers", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CustomerRestController {

	@Autowired
	private CustomerServiceImpl customer;

	@Autowired
	private AppUserServiceImpl user;

	@Autowired
	private IAuthenticationFacade authentication;

	@GetMapping(path = { "/{customerId}" }, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> readOne(@PathVariable("customerId") String customerId)
			throws CustomerNotFoundException, IdentityNotGivenException {

		AppUser user = (AppUser) this.authentication.getAuthentication().getPrincipal();
		if (customerId == null || customerId.equals("")) {
			throw new IdentityNotGivenException(HttpStatus.NOT_FOUND, "Please gives customerID to get informations",
					new String[] { "not found" }, "/customers/" + customerId);
		}
		System.out.println("user id " + user.getUserId());
		Customer result = this.customer.getOne(customerId, user.getUserId());
		if (result == null) {
			throw new CustomerNotFoundException(HttpStatus.NOT_FOUND, "Cannot find Customer with ID =" + customerId,
					new String[] { "not found" }, "/customers/" + customerId);
		}
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	@GetMapping(path = { "" }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> readAll() throws NoResponseContentException {
		AppUser prin = this.user.findByUserName(this.authentication.getAuthentication().getName());
		List<Customer> result = this.customer.getAllByUserId(prin.getUserId());
		if (result == null) {
			throw new NoResponseContentException(HttpStatus.NO_CONTENT, "Sorry, resouce not available now",
					new String[] { "not found" }, "/customers");
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> addCustomer(@RequestBody Customer object) {
		AppUser prin = this.user.findByUserName(this.authentication.getAuthentication().getName());
		object.setUser(prin);
		 boolean saved = customer.createOne(object);
		System.out.println(saved);
		return ResponseEntity.ok(null);
	}
	
}
