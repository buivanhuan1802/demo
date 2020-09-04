package com.easywork.api.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easywork.api.entity.Customer;

@Repository
public interface CustomerRepos extends CrudRepository<Customer, String> {

	@Query(value = "SELECT P.customer_id,P.customer_name,P.phone_number,P.address,P.active,P.area_id,P.user_id\r\n"
			+ "from (select * from customer c\r\n"
			+ "where c.user_id = \"5e29b203-e08d-11ea-82f5-28f10e069ebe\") as P\r\n"
			+ "JOIN (select i.customer_id from invoice i where status = 0) as K \r\n"
			+ "where P.customer_id = K.customer_id", nativeQuery = true)
	public List<Customer> findAllByUserId(@Param("userId") String userId);

	@Query(value = "SELECT P.customer_id,P.customer_name,P.phone_number,P.address,P.active,P.area_id,P.user_id\r\n"
			+ "FROM (SELECT * FROM customer c) AS P\r\n" 
			+ "JOIN (SELECT a.area_id FROM area a) AS K \r\n"
			+ "ON  P.area_id = K.area_id \r\n" + "WHERE \r\n"
			+ "p.user_id =:userId AND p.area_id=:areaId",nativeQuery = true)
	public List<Customer> findAllByAreaIdAndUserId(@Param("userId") String userId, @Param("areaId") String areaId);
	
	@Query("FROM Customer c WHERE c.customerId =:customerId AND c.user.userId =:userId ")
	public Customer findOneByIdAndUserId(@Param("customerId") String customerId, @Param("userId") String userId);
}
