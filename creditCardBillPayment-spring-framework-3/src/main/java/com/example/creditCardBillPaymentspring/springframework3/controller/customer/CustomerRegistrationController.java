package com.example.creditCardBillPaymentspring.springframework3.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creditCardBillPaymentspring.springframework3.dto.customer.CustomerRegistrationDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customer.LoginRequestDTO;
import com.example.creditCardBillPaymentspring.springframework3.service.customerRegistration.CustomerRegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer/registration")
public class CustomerRegistrationController {

	@Autowired
	CustomerRegistrationService customerRegistrationService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerRegistrationDTO customerRegistrationDTO) {

		return ResponseEntity.ok().body(customerRegistrationService.registerCustomer(customerRegistrationDTO));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) throws Exception {

		return ResponseEntity.ok().body(customerRegistrationService.signIn(loginRequest));
	}

}
