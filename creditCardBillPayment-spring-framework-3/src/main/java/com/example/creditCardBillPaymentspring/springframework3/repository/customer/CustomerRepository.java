package com.example.creditCardBillPaymentspring.springframework3.repository.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.creditCardBillPaymentspring.springframework3.entity.customer.CustomerRegistration;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerRegistration, Long> {

	Optional<CustomerRegistration> findByEmail(String email);

	Optional<CustomerRegistration> findByUserName(String userName);

	@Query("select h from CustomerRegistration h where h.isDeleted='N' and h.userName=:username")
	CustomerRegistration findByUserNameIs(String username);

	CustomerRegistration findByEmailIs(String email);

	Boolean existsByEmail(String email);

	@Query("select h from CustomerRegistration h where h.isDeleted='N' and h.id=:id")
	CustomerRegistration findByUserRegistrationIdAndIsDeleted(Long id);

	@Query("select h from CustomerRegistration h where h.isDeleted='N'")
	List<CustomerRegistration> listByUserRegistrationId();

	@Query("SELECT a FROM CustomerRegistration a WHERE a.email= :email")
	CustomerRegistration checkMail(@Param("email") String email);
}
