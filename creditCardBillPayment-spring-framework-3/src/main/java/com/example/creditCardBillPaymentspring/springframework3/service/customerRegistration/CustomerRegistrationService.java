package com.example.creditCardBillPaymentspring.springframework3.service.customerRegistration;

import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.creditCardBillPaymentspring.springframework3.dto.customer.CustomerRegistrationDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.customer.LoginRequestDTO;
import com.example.creditCardBillPaymentspring.springframework3.dto.mail.MailDTO;
import com.example.creditCardBillPaymentspring.springframework3.entity.customer.CustomerRegistration;
import com.example.creditCardBillPaymentspring.springframework3.entity.customer.Role;
import com.example.creditCardBillPaymentspring.springframework3.repository.customer.CustomerRepository;
import com.example.creditCardBillPaymentspring.springframework3.response.JwtResponse;
import com.example.creditCardBillPaymentspring.springframework3.response.MessageResponse;
import com.example.creditCardBillPaymentspring.springframework3.security.jwt.UserInfoUserDetails;
import com.example.creditCardBillPaymentspring.springframework3.service.jwt.JwtService;
import com.example.creditCardBillPaymentspring.springframework3.utils.mailAndpdf.RegisteredUsersMail;

import jakarta.validation.Valid;

/**
 * 
 * @author Siya
 *
 */
@Service
@Validated
//@Slf4j
public class CustomerRegistrationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> registerCustomer(@Valid CustomerRegistrationDTO customerRegistrationDTO) {

		if (Boolean.TRUE.equals(customerRepository.existsByEmail(customerRegistrationDTO.getEmail()))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		if (!customerRegistrationDTO.getGender().toLowerCase().equalsIgnoreCase("female")
				&& !customerRegistrationDTO.getGender().toLowerCase().equalsIgnoreCase("other")) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Please enter any of these values as Gender Type 'Male','Female','Other'"));
		}

		Calendar cl = Calendar.getInstance();
		customerRegistrationDTO.setRole(Role.ROLE_CUSTOMER);
		customerRegistrationDTO.setIsActive("Y");
		customerRegistrationDTO.setIsDeleted("N");
		customerRegistrationDTO.setCreatedDate(cl.getTime());
		customerRegistrationDTO.setPassword(passwordEncoder.encode(customerRegistrationDTO.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		CustomerRegistration user = modelMapper.map(customerRegistrationDTO, CustomerRegistration.class);

		customerRepository.save(user);

		MailDTO mailDTO = new MailDTO();
		mailDTO.setMailId(customerRegistrationDTO.getEmail());
		mailDTO.setName(customerRegistrationDTO.getFirstName() + " " + customerRegistrationDTO.getLastName());
		customerRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Registration Completed Successfully!"));
	}

	public void registeredUsers(@RequestBody MailDTO mailDTO) {
		RegisteredUsersMail.registeredUsers(mailDTO);
	}

	public Object signIn(@Valid LoginRequestDTO loginRequest) throws Exception {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtService.generateJwtToken(authentication);

		UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
		List<String> roles;
		roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
	}
}