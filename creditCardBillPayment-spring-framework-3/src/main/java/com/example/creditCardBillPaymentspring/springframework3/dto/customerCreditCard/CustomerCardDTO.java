package com.example.creditCardBillPaymentspring.springframework3.dto.customerCreditCard;

import java.util.Date;

import com.example.creditCardBillPaymentspring.springframework3.utils.date.JsonDateDeserializer;
import com.example.creditCardBillPaymentspring.springframework3.utils.date.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCardDTO {

	@NotNull
	@Digits(integer = 16, fraction = 0)
	private Long cardNumber;

	@NotNull
	@Digits(integer = 3, fraction = 0)
	private Long cvv;

	@NotBlank
	@NotEmpty
	private String customerName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date expiryDate;

	@Digits(integer = 10, fraction = 0)
	private Long moblileNumber;

	@NotNull
	private Long creditLimit;

	@NotNull
	private Long availableCredit;

	private String isActive;

	private Date createdDate;

	private String isDeleted;

	private Long statementDay;

	private Long extraPaidAmount;

	private Long totalDue;

	private String email;

	private Long customerId;

}
