package com.example.creditCardBillPaymentspring.springframework3.utils.propertyfile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.example.creditCardBillPaymentspring.springframework3.utils.propertyfile.beanfacto.BeanCreator;




@Configuration
@PropertySource(value = { "classpath:config.properties", "classpath:email.smtp.properties" })

public class FrameConfig {

	@Bean
	public BeanCreator getBeanCreator() {
		return new BeanCreator();
	}
}
