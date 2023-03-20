package com.example.creditCardBillPaymentspring.springframework3.controller.customerTransactions;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creditCardBillPaymentspring.springframework3.dto.customerTransactions.CustomerTransactionDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerTransactions.CustomerTransaction;
import com.example.creditCardBillPaymentspring.springframework3.response.MessageResponse;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;
import com.example.creditCardBillPaymentspring.springframework3.service.customerTransactions.CustomerTransactionsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/creditcardPayment/transactions")
public class CustomerTransactionsController {

	@Autowired
	CustomerTransactionsService customerTransactionsService;

	@PostMapping("/saveTransaction")
	public ResponseEntity<?> saveCustomerTransactions(@Valid @RequestBody CustomerTransactionDTO customerTransactionDTO,
			@AuthenticationPrincipal UserInfoUserDetails loggeduser) throws ParseException {
		customerTransactionsService.saveCustomerTransactions(customerTransactionDTO, loggeduser);
		return ResponseEntity.ok(new MessageResponse("Transaction completed Successfully!"));
	}

	@GetMapping("getCustomerTransactionList")
	public List<CustomerTransaction> getCustomerTransactionList(
			@AuthenticationPrincipal UserInfoUserDetails loggeduser) {
		return customerTransactionsService.getCustomerTransactionList(loggeduser);
	}

}
