package com.example.creditCardBillPaymentspring.springframework3.dto.statement;

import java.io.Serializable;
import java.util.List;

import com.example.creditCardBillPaymentspring.springframework3.dto.customerTransactions.CustomerTransactionDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerTransactions.CustomerTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatementDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long customerId;

	private String mailId;

	private List<String> mailIds;

	private String username;

	private String name;

	private Long totalDue;

	private Long minimumAmountDue;

	private Long availableCash;

	private Long availableCredit;

	private Long creditLimit;

	private String mobile;

	private Long customerCardNo;

	private Long billNumber;

	//private List<CustomerTransactionDTO> customerTransactionDTO;
	
	private List<CustomerTransaction> customerTransactions;
	

}
