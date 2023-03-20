package com.example.creditCardBillPaymentspring.springframework3.repository.customerCreditCard;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.creditCardBillPaymentspring.springframework3.entity.customerCreditCard.CustomerCreditCard;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerCreditCardRepository extends JpaRepository<CustomerCreditCard, Long> {

	@Query("select h from CustomerCreditCard h where h.isDeleted='N' and h.cardNumber=:cardNumber")
	CustomerCreditCard findByCardNumber(Long cardNumber);

	@Query("select h from CustomerCreditCard h where h.isDeleted='N' and h.cardNumber IN :cardNumber")
	List<CustomerCreditCard> listByCardNumber(List<Long> cardNumber);

	@Query("select h from CustomerCreditCard h where h.isDeleted='N' and h.cardNumber=:cardNumber and h.customerId=:customerId")
	CustomerCreditCard findByCardNumberAndCustomerId(Long cardNumber, Long customerId);

	@Query("select h from CustomerCreditCard h where h.isDeleted='N' and h.cardNumber=:cardNumber and h.expiryDate=:expiryDate")
	CustomerCreditCard checkExpiryDate(Long cardNumber, Date expiryDate);

	@Query("select h from CustomerCreditCard h where h.isDeleted='N' and h.customerId=:customerId")
	List<CustomerCreditCard> findByCustomerId(Long customerId);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerCreditCard a SET a.availableCredit=:availableCredit,a.totalDue=:totalDue WHERE a.customerId=:customerId and a.cardNumber=:cardNumber AND a.isDeleted = 'N'")
	void updateCreditLimit(Long availableCredit, Long totalDue, Long customerId, Long cardNumber);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerCreditCard a SET a.extraPaidAmount=:extraPaidAmount,a.totalDue=:totalDue WHERE a.customerId=:customerId and a.cardNumber=:cardNumber AND a.isDeleted = 'N'")
	void updateExtraAmntPaidAndtotalDue(Long extraPaidAmount, Long totalDue, Long customerId, Long cardNumber);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerCreditCard a SET a.extraPaidAmount=:extraPaidAmount WHERE a.cardNumber=:cardNumber AND a.isDeleted = 'N'")
	void updateExtraAmntPaid(Long extraPaidAmount, Long cardNumber);

	@Transactional
	@Query(value = "select h from CustomerCreditCard h where h.isDeleted='N' and h.statementDay=:statementDay")
	List<CustomerCreditCard> findByStatementDay(Long statementDay);

	@Transactional
	@Modifying
	@Query(value = "Update CustomerCreditCard a SET a.totalDue=:totalDue,a.availableCredit=:availableCredit WHERE a.cardNumber=:cardNumber AND a.isDeleted = 'N'")
	void updateTotalDueAndAvailablecreditLimit(Long totalDue, Long availableCredit, Long cardNumber);

}
