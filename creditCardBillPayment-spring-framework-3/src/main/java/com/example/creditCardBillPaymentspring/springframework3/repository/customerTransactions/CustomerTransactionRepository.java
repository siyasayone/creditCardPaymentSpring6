package com.example.creditCardBillPaymentspring.springframework3.repository.customerTransactions;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.creditCardBillPaymentspring.springframework3.entity.customerTransactions.CustomerTransaction;

/**
 * 
 * @author Siya
 *
 */
@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {

	CustomerTransaction findByCustomerTransactionId(Long customerTransactionId);

	@Query(value = "SELECT a FROM CustomerTransaction a WHERE customerId IN :customerId AND transactionDate BETWEEN :startDate AND :endDate")
	List<CustomerTransaction> findByCustomerTransactionsList(List<Long> customerId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query(value = "SELECT a FROM CustomerTransaction a WHERE customerId=:customerId")
	List<CustomerTransaction> findByCustomerTransactions(Long customerId);

}
