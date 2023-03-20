package com.example.creditCardBillPaymentspring.springframework3.entity.customer;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerRegistration {

	@Id
	@GeneratedValue
	private Long Id;

	@Column(length = 50)
	private String userName;

	@Column(length = 50)
	private String firstname;

	@Column(length = 50)
	private String lastname;

	@Column(length = 50)
	private String email;

	@Column(length = 120)
	private String password;

	private Date createdDate;

	private Date modifiedDate;

	@Column(length = 1)
	private String isActive;

	@Column(length = 1)
	private String isDeleted;

	@Column(length = 50)
	private String role;

}
