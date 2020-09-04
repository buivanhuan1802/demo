package com.easywork.api.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easywork.api.entity.Customer;
import com.easywork.api.repos.CustomerRepos;
import com.easywork.api.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepos customer;

	@Override
	public Customer getOne(String userId, String objectId) {
		Customer result = this.customer.findOneByIdAndUserId(userId, objectId);
		return result;
	}

	@Override
	public List<Customer> getAll() {
		Iterable<Customer> temp = this.customer.findAll();
		List<Customer> result = new ArrayList<Customer>();
		temp.forEach(result::add);
		return result.isEmpty() ? null : result;
	}

	@Override
	public List<Customer> getAllByUserId(String userId) {
		List<Customer> result = this.customer.findAllByUserId(userId);
		if (result == null)
			return null;
		return result.isEmpty() ? null : result;
	}

	@Override
	public boolean createOne(Customer object) {
		Customer c = this.customer.save(object);
		System.out.println(c.getCustomerId());
		return c != null;
	}

	@Override
	public List<Customer> findAllByAreaIdAndUserId(String userId, String areaId) {
		// TODO Auto-generated method stub
		return this.findAllByAreaIdAndUserId(userId, areaId);
	}

}
