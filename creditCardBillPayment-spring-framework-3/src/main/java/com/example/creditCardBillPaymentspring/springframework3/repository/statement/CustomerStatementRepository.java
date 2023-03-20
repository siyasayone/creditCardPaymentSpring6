package com.example.creditCardBillPaymentspring.springframework3.repository.statement;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.creditCardBillPaymentspring.springframework3.entity.statement.CustomerStatement;

import jakarta.transaction.Transactional;

/**
 * 
 * @author Siya
 *
 */
@Repository
public interface CustomerStatementRepository extends JpaRepository<CustomerStatement, Long> {

	CustomerStatement findByStatementId(Long customerStatement);

	@Query(value = "SELECT a FROM CustomerStatement a WHERE a.isPaid='N' and a.customerId=:customerId")
	List<CustomerStatement> findLastStatementByCustomerId(Long customerId);

	@Query(value = "SELECT a FROM CustomerStatement a WHERE a.isPaid='N'")
	List<CustomerStatement> listAllStatements();

	@Query(value = "SELECT a FROM CustomerStatement a WHERE customercardNumber=:customercardNumber")
	List<CustomerStatement> listPayments(Long customercardNumber);

	@Query(value = "SELECT a FROM CustomerStatement a WHERE customercardNumber=:customercardNumber and a.isPaid='N'  ORDER BY createdDate DESC LIMIT 1", nativeQuery = true)
	CustomerStatement viewLastGeneratedbillByCardNumber(Long customercardNumber);

	@Query(value = "SELECT a FROM CustomerStatement a WHERE a.customercardNumber=:customercardNumber  and a.isPaid='N'")
	CustomerStatement findLastStatementByCustomercardNumber(Long customercardNumber);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerStatement a SET a.totalDue=:totalDue,a.minimumAmountDue=:minimumAmountDue WHERE a.customercardNumber=:customercardNumber AND a.isPaid = 'N'")
	void updateStatement(Long totalDue, Long minimumAmountDue, Long customercardNumber);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerStatement a SET a.isPaid='Y' WHERE a.customercardNumber=:customercardNumber AND a.totalDue =0")
	void updateIsPaid(Long customercardNumber);

}
