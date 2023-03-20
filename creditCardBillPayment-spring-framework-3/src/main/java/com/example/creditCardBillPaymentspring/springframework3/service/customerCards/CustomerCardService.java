package com.example.creditCardBillPaymentspring.springframework3.service.customerCards;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CardDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CardDetailsDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard.CustomerCardDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customer.CustomerRegistration;
import com.example.creditCardBillPaymentspring.springframework3.entity.customerCreditCard.CustomerCreditCard;
import com.example.creditCardBillPaymentspring.springframework3.entity.statement.CustomerStatement;
import com.example.creditCardBillPaymentspring.springframework3.exception.CardException;
import com.example.creditCardBillPaymentspring.springframework3.exception.DateException;
import com.example.creditCardBillPaymentspring.springframework3.exception.InvalidCustomerDetailsException;
import com.example.creditCardBillPaymentspring.springframework3.repository.customer.CustomerRepository;
import com.example.creditCardBillPaymentspring.springframework3.repository.customerCreditCard.CustomerCreditCardRepository;
import com.example.creditCardBillPaymentspring.springframework3.repository.statement.CustomerStatementRepository;
import com.example.creditCardBillPaymentspring.springframework3.response.MessageResponse;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;

import jakarta.validation.Valid;

/**
 * 
 * @author Siya
 *
 */
@Service
//@Slf4j
public class CustomerCardService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerCreditCardRepository customerCreditCardRepository;

	@Autowired
	private CustomerStatementRepository customerStatementRepository;

	private ModelMapper modelMapper;

	public static int statementDate() {
		Random r = new Random();
		return r.nextInt(15, 30);
	}

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public CustomerCreditCard addCustomerCard(@Valid CardDTO customerCardDTO, UserInfoUserDetails loggeduser)
			throws DateException {
		CustomerRegistration user = customerRepository.findByUserNameIs(loggeduser.getUsername());
		Calendar cl = Calendar.getInstance();
		CustomerCreditCard yr = new CustomerCreditCard();
		for (CustomerCardDTO c : customerCardDTO.getCustomerCardDetailsDTO()) {
			CustomerCreditCard card = customerCreditCardRepository.findByCardNumber(c.getCardNumber());
			modelMapper = new ModelMapper();
			if (card == null) {
				c.setIsActive("Y");
				c.setIsDeleted("N");
				c.setCreatedDate(cl.getTime());
				int day = statementDate();
				c.setStatementDay(Long.valueOf(day));
				c.setExtraPaidAmount(0L);
				c.setTotalDue(0L);
				c.setCustomerId(user.getId());
				c.setEmail(user.getEmail());
			}
			yr = modelMapper.map(c, CustomerCreditCard.class);
			customerCreditCardRepository.save(yr);
		}
		return yr;
	}

	public CustomerCreditCard updateCard(CustomerCardDTO customerCardDTO, UserInfoUserDetails loggeduser) {

		if (customerCardDTO.getCardNumber() == null) {
			throw new CardException("");
		}
		Calendar cl = Calendar.getInstance();
		CustomerCreditCard card = customerCreditCardRepository.findByCardNumber(customerCardDTO.getCardNumber());
		if (card != null) {
			card.setCardNumber(customerCardDTO.getCardNumber());
			if (customerCardDTO.getCustomerName() != null) {
				card.setCustomerName(customerCardDTO.getCustomerName());
			}

			if (customerCardDTO.getMoblileNumber() != null) {
				card.setMobileNumber(customerCardDTO.getMoblileNumber());
			}

			card.setModifiedDate(cl.getTime());
			customerCreditCardRepository.save(card);
		} else {
			throw new InvalidCustomerDetailsException("details not matching");
		}

		return card;
	}

	public ResponseEntity<Object> removeCard(Long cardNumber, UserInfoUserDetails loggeduser) {
		Calendar cl = Calendar.getInstance();
		CustomerRegistration user = customerRepository.findByUserNameIs(loggeduser.getUsername());
		CustomerCreditCard yr = customerCreditCardRepository.findByCardNumberAndCustomerId(cardNumber, user.getId());
		if (yr != null) {
			yr.setIsActive("N");
			yr.setIsDeleted("Y");
			yr.setModifiedDate(cl.getTime());
			customerCreditCardRepository.save(yr);

		} else {
			return new ResponseEntity<>("Please check the card number", HttpStatus.OK);
		}
		return new ResponseEntity<>("Card removed Successfully", HttpStatus.OK);
	}

	public CardDetailsDTO showCardDetails(UserInfoUserDetails loggeduser) {
		CustomerRegistration user = customerRepository.findByUserNameIs(loggeduser.getUsername());
		List<CustomerCreditCard> card = customerCreditCardRepository.findByCustomerId(user.getId());
		List<CustomerStatement> statement = customerStatementRepository.findLastStatementByCustomerId(user.getId());
		CardDetailsDTO dto = new CardDetailsDTO();
		dto.setCustomerName(user.getFirstname() + " " + user.getLastname());
		ArrayList<CustomerCreditCard> cardDetails = new ArrayList<>();
		card.stream().toList();
		cardDetails.addAll(card);
		ArrayList<CustomerStatement> statements = new ArrayList<>();
		statement.stream().toList();
		statements.addAll(statement);
		dto.setCustomerCardDetailsDTO(cardDetails);
		dto.setStatementRemainderDTO(statements);
		return dto;
	}

	public List<CustomerStatement> getStatementHistory(Long cardNumber) {
		List<CustomerStatement> cd = customerStatementRepository.listPayments(cardNumber);
		return cd.stream().toList();
	}

	public CustomerStatement viewGeneratedBills(Long cardNumber) {
		return customerStatementRepository.viewLastGeneratedbillByCardNumber(cardNumber);
	}

	public ResponseEntity<?> refundAmnt(Long cardNumber, Long amount) {
		CustomerCreditCard card = customerCreditCardRepository.findByCardNumber(cardNumber);
		Long extraAmnt;
		if (card.getExtraPaidAmount() < amount) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("maximum amount to refund is" + card.getExtraPaidAmount()));
		} else {
			extraAmnt = card.getExtraPaidAmount() - amount;
			customerCreditCardRepository.updateExtraAmntPaid(extraAmnt, cardNumber);
		}
		return ResponseEntity.ok(new MessageResponse("Refund Completed Successfully!"));
	}

}