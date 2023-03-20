package com.example.creditCardBillPaymentspring.springframework3.dto.customer;

import java.util.Date;

import org.springframework.validation.annotation.Validated;

import com.example.creditCardBillPaymentspring.springframework3.entity.customer.Role;
import com.example.creditCardBillPaymentspring.springframework3.utils.date.JsonDateDeserializer;
import com.example.creditCardBillPaymentspring.springframework3.utils.date.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CustomerRegistrationDTO {

	private Long customerId;

	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min = 3, max = 20, message = "Username cannot be empty")
	private String userName;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@NotEmpty
	private String gender;

	private String isActive;

	private Date createdDate;

	private String isDeleted;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotBlank
	private String password;

	private Role role;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date dateOfBirth;

}
