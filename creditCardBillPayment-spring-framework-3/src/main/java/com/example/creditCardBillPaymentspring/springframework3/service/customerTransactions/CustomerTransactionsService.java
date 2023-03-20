package com.example.creditCardBillPaymentspring.springframework3.service.customerTransactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.creditCardBillPaymentspring.springframework3.dto.customerTransactions.CustomerTransactionDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customer.CustomerRegistration;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerCreditCard.CustomerCreditCard;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerTransactions.CustomerTransaction;
import com.example.creditCardBillPaymentspring.springframework3.exception.CardNotExistException;
import com.example.creditCardBillPaymentspring.springframework3.exception.CreditLimitException;
import com.example.creditCardBillPaymentspring.springframework3.exception.DateException;
import com.example.creditCardBillPaymentspring.springframework3.exception.ExpiredCreditCardException;
import com.example.creditCardBillPaymentspring.springframework3.exception.InvalidCVVException;
import com.example.creditCardBillPaymentspring.springframework3.exception.InvalidCustomerDetailsException;
import com.example.creditCardBillPaymentspring.springframework3.exception.InvalidExpiryDateException;
import com.example.creditCardBillPaymentspring.springframework3.repository.customer.CustomerRepository;
import com.example.creditCardBillPaymentspring.springframework3.repository.customerCreditCard.CustomerCreditCardRepository;
import com.example.creditCardBillPaymentspring.springframework3.repository.customerTransactions.CustomerTransactionRepository;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;

import jakarta.validation.Valid;

/**
 * 
 * @author Siya
 *
 */
@Service
public class CustomerTransactionsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerTransactionRepository customerTransactionRepository;

	@Autowired
	private CustomerCreditCardRepository customerCreditCardRepository;

	public CustomerTransaction saveCustomerTransactions(@Valid CustomerTransactionDTO customerTransactionDTO,
			UserInfoUserDetails loggeduser) throws ParseException {
		CustomerRegistration user = customerRepository.findByUserNameIs(loggeduser.getUsername());
		CustomerCreditCard card = customerCreditCardRepository
				.findByCardNumberAndCustomerId(customerTransactionDTO.getCustomerCardNumber(), user.getId());
		if (card == null) {
			throw new CardNotExistException("Invalid Card Number");
		}
		if (!user.getId().equals(card.getCustomerId())) {
			throw new InvalidCustomerDetailsException("Customer detsils not matching");
		}
		Calendar cl = Calendar.getInstance();
		if (!card.getCvv().equals(customerTransactionDTO.getCvv())) {
			throw new InvalidCVVException("Cvv number is not matching");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		if (!customerTransactionDTO.getCardExpiryDate().isEmpty()
				&& !customerTransactionDTO.getCardExpiryDate().isBlank()) {
			try {
				Date d1 = format.parse(customerTransactionDTO.getCardExpiryDate());
			} catch (ParseException e) {
				throw new DateException("Given date is not valid according to the pattern yyyy-MM-dd");
			}
		}
		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(card.getExpiryDate());
		if (!Objects.equals(newstring, customerTransactionDTO.getCardExpiryDate())) {
			throw new InvalidExpiryDateException("Expiry date is not matching");
		}

		int result = format.parse(customerTransactionDTO.getCardExpiryDate()).compareTo(cl.getTime());
		if (result < 0) {
			throw new ExpiredCreditCardException("Credit Card is not valid!Validity expired...");
		}

		if (card.getAvailableCredit() < customerTransactionDTO.getAmount() || card.getAvailableCredit() == 0) {
			throw new CreditLimitException("No sufficient credit limit for this request");
		}
		ModelMapper modelMapper = new ModelMapper();
		customerTransactionDTO.setCustomerId(user.getId());
		CustomerTransaction entity = modelMapper.map(customerTransactionDTO, CustomerTransaction.class);
		customerTransactionRepository.save(entity);
		Long availableCredit = card.getAvailableCredit() - customerTransactionDTO.getAmount();
		Long totalDue = card.getTotalDue() + customerTransactionDTO.getAmount();
		customerCreditCardRepository.updateCreditLimit(availableCredit, totalDue, user.getId(), card.getCardNumber());
		return entity;
	}

	public List<CustomerTransaction> getCustomerTransactionList(UserInfoUserDetails loggeduser) {
		CustomerRegistration user = customerRepository.findByUserNameIs(loggeduser.getUsername());
		List<CustomerTransaction> cd = customerTransactionRepository.findByCustomerTransactions(user.getId());
		return cd.stream().collect(Collectors.toList());
	}

}