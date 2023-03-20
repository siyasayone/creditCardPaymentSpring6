package com.example.creditCardBillPaymentspring.springframework3.dto.statement;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayBillsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long customerTransactionId;

	@NotNull
	private Long customerCardNumber;

	@NotNull
	private Long fromAccount;

	@NotEmpty
	private String branchName;

	@NotEmpty
	private String cardType;

	@NotNull
	private Long amount;

}
