package com.example.creditCardBillPaymentspring.springframework3.entity.statement;

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
@Table(name = "customerstatement")
public class CustomerStatement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "customerstatementid_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "customerstatementid_seq", sequenceName = "customerstatementid_seq", allocationSize = 1)
	@Column(name = "statementId")
	private Long statementId;

	@Column(length = 50)
	private Long customerId;

	@Column(length = 50)
	private Long customercardNumber;

	@Column(length = 50)
	private Long totalDue;

	@Column(length = 50)
	private Long minimumAmountDue;

	private Date dueDate;

	private Date createdDate;

	private Date paidDate;

	@Column(length = 1)
	private String isPaid;

}
