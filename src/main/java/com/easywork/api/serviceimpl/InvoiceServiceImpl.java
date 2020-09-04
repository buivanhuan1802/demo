package com.easywork.api.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easywork.api.entity.Invoice;
import com.easywork.api.repos.InvoiceRepos;
import com.easywork.api.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepos invoice;

	@Override
	public Invoice getOne(String userId , String objectId) {
		// TODO Auto-generated method stub
		Optional<Invoice> x = this.invoice.findById(objectId);
		if (!x.isPresent()) {
			return null;
		}
		return x.get();
	}

	@Override
	public List<Invoice> getAll() {
		Iterable<Invoice> it = this.invoice.findAll();
		List<Invoice> result = new ArrayList<Invoice>();
		it.iterator().forEachRemaining(result::add);
		return result;
	}

	@Override
	public List<Invoice> getAllByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.invoice.getAllInProcessingByUserId(userId);
	}

	@Override
	public boolean createOne(Invoice object) {
		// TODO Auto-generated method stub
		return false;
	}

}
