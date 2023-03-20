package com.example.creditCardBillPaymentspring.springframework3.service.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.creditCardBillPaymentspring.springframework3.entity.customer.CustomerRegistration;
import com.example.creditCardBillPaymentspring.springframework3.repository.customer.CustomerRepository;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<CustomerRegistration> userInfo = repository.findByUserName(userName);
		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + userName));

	}
}