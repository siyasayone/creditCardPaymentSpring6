package com.example.creditCardBillPaymentspring.springframework3.utils.propertyfile.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

	/**
	 *
	 * @return
	 */
	@Bean
	public AppContext getAppContext() {
		return new AppContext();
	}
}
