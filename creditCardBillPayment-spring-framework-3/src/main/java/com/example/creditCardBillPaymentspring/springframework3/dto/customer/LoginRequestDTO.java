package com.example.creditCardBillPaymentspring.springframework3.dto.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
