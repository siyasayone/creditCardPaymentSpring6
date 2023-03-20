package com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard;

import java.util.List;

import com.example.creditCardBillPaymentspring.springframework3.entity.customerCreditCard.CustomerCreditCard;
import com.example.creditCardBillPaymentspring.springframework3.entity.statement.CustomerStatement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsDTO {

	@Valid
	private List<CustomerCreditCard> customerCardDetailsDTO;

	private String customerName;

	private List<CustomerStatement> statementRemainderDTO;

}
