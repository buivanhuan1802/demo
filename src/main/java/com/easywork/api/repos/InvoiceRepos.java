package com.easywork.api.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.easywork.api.entity.Invoice;

@Repository
public interface InvoiceRepos extends CrudRepository<Invoice, String> {
	@Query(value = "SELECT i.invoice_id,i.customer_id,i.date_created,i.status,i.total FROM\r\n"
			+ "(SELECT invoice_id,customer_id,date_created,status,total FROM invoice) AS i,\r\n"
			+ " (SELECT customer_id FROM customer c where c.user_id =:userId ) AS c\r\n"
			+ " WHERE i.customer_id = c.customer_id AND i.status = 0", nativeQuery = true)
	public List<Invoice> getAllInProcessingByUserId(@Param("userId") String userId);
}
