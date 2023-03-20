package com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard;

import java.util.List;

import com.example.creditCardBillPaymentspring.springframework3.dto.statement.StatementRemainderDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

	@Valid
	private List<CustomerCardDTO> customerCardDetailsDTO;

	private String customerName;

	private String email;

	private List<StatementRemainderDTO> statementRemainderDTO;

}
