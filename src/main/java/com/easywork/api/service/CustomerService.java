package com.easywork.api.service;



import java.util.List;

import com.easywork.api.entity.Customer;

public interface CustomerService extends IEntityService<Customer>{

public List<Customer> findAllByAreaIdAndUserId(String userId,String areaId);
}
