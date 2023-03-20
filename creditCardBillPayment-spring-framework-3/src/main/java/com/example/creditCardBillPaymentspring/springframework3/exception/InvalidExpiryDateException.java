package com.example.creditCardBillPaymentspring.springframework3.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Siya
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidExpiryDateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorMessage;

}