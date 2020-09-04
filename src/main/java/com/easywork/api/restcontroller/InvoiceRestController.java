package com.easywork.api.restcontroller;

import java.security.Principal;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easywork.api.entity.AppUser;
import com.easywork.api.entity.Customer;
import com.easywork.api.entity.Invoice;
import com.easywork.api.entity.InvoiceDetail;
import com.easywork.api.securityconfig.IAuthenticationFacade;
import com.easywork.api.serviceimpl.AppUserServiceImpl;
import com.easywork.api.serviceimpl.InvoiceServiceImpl;

@RestController
@RequestMapping(value = "/invoices", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class InvoiceRestController {

	@Autowired
	private AppUserServiceImpl appUser;
	@Autowired
	private InvoiceServiceImpl invoice;

	@Autowired
	private IAuthenticationFacade authentication;

//create invoices
	@PostMapping("")
	public ResponseEntity<String> create(@RequestBody Invoice object) {
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

//
	@PutMapping("/{invoiceId}")
	public ResponseEntity<String> updateOne(Principal principal) {


		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PutMapping("")
	public ResponseEntity<String> updateAll(Principal principal) {

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(path = { "/{invoiceId}" }, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> readOne(@PathVariable String invoiceId) {

		if (invoiceId == null) {
		}
		AppUser user = appUser.findByUserName(this.authentication.getAuthentication().getName());
		Invoice i = invoice.getOne(invoiceId, user.getUserId());
		return ResponseEntity.ok(i);
	}

	@GetMapping(path = { "" }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> readAll() {
		AppUser prin = this.appUser.findByUserName(this.authentication.getAuthentication().getName());
		List<Invoice> invoices = invoice.getAllByUserId(prin.getUserId());
		if (invoices == null) {

		}
		for(Invoice x : invoices) {
			for(InvoiceDetail k : x.getDetail()) {
				System.out.println(k.getDetailId());}
			}
		return ResponseEntity.ok(invoices);
	}

}
