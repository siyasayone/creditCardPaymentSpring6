package com.example.creditCardBillPaymentspring.springframework3.entity.customerTransactions;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Siya
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customerTransaction")
public class CustomerTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "customerTransactionid_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "customerTransactionid_seq", sequenceName = "customerTransactionid_seq", allocationSize = 1)
	@Column(name = "customerTransactionId")
	private Long customerTransactionId;

	@Column(length = 50)
	private Long customerId;

	@Column(length = 100)
	private Long customerCardNumber;

	@Column(length = 100)
	private Long cvv;

	@Column(length = 50)
	private Long amount;

	private Date transactionDate;

}
