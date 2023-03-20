package com.example.creditCardBillPaymentspring.springframework3.controller.customerCreditCard;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CardDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CardDetailsDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CustomerCardDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerCreditCard.CustomerCreditCard;
import com.example.creditCardBillPaymentspring.springframework3.entity.statement.CustomerStatement;
import com.example.creditCardBillPaymentspring.springframework3.exception.DateException;
import com.example.creditCardBillPaymentspring.springframework3.response.MessageResponse;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;
import com.example.creditCardBillPaymentspring.springframework3.service.customerCards.CustomerCardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/creditcardPayment/card")
public class CustomerCreditCardController {

	@Autowired
	CustomerCardService customerCardService;

	@PostMapping("/addCard")
	public ResponseEntity<?> addCustomerCard(@Valid @RequestBody CardDTO customerCardDTO,
			@AuthenticationPrincipal UserInfoUserDetails loggeduser) {
		customerCardService.addCustomerCard(customerCardDTO, loggeduser);
		return ResponseEntity.ok(new MessageResponse("card added Successfully!"));
	}

	@PostMapping("/updateCard")
	public ResponseEntity<CustomerCreditCard> updateCard(@RequestBody CustomerCardDTO customerCardDTO,
			@AuthenticationPrincipal UserInfoUserDetails loggeduser) throws ParseException, DateException {
		return ResponseEntity.ok().body(customerCardService.updateCard(customerCardDTO, loggeduser));
	}

	@GetMapping("/removeCard")
	public ResponseEntity<Object> removeCard(@RequestParam Long cardNumber,
			@AuthenticationPrincipal UserInfoUserDetails loggeduser) {
		return customerCardService.removeCard(cardNumber, loggeduser);

	}

	@GetMapping("/showCardDetails")
	public CardDetailsDTO showCardDetails(@AuthenticationPrincipal UserInfoUserDetails loggeduser) {
		return customerCardService.showCardDetails(loggeduser);
	}

	@GetMapping("getStatementHistory")
	public List<CustomerStatement> getStatementHistory(@RequestParam Long cardNumber) {
		return customerCardService.getStatementHistory(cardNumber);
	}

	@GetMapping("viewGeneratedBills")
	public CustomerStatement viewGeneratedBills(@RequestParam Long cardNumber) {
		return customerCardService.viewGeneratedBills(cardNumber);
	}

	@PostMapping("/refundAmnt")
	public ResponseEntity<?> refundAmnt(@Valid @RequestParam Long cardNumber, @RequestParam Long amount) {
		return customerCardService.refundAmnt(cardNumber, amount);
	}

}
