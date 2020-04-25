package com.auth.springjwt.authjwtapi.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth.springjwt.authjwtapi.model.Client;
import com.auth.springjwt.authjwtapi.repository.ClientRepo;
import com.auth.springjwt.authjwtapi.request.Login;
import com.auth.springjwt.authjwtapi.request.SignUp;
import com.auth.springjwt.authjwtapi.response.JwtResponse;
import com.auth.springjwt.authjwtapi.response.MessageResponse;
import com.auth.springjwt.authjwtapi.safety.jwt.JwtControl;
import com.auth.springjwt.authjwtapi.safety.resources.ClientDetail;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Auth {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtControl jwtControl;
	
	// Sign in on system
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login login) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtControl.generateJwtToken(authentication);
		
		ClientDetail clientDetail = (ClientDetail) authentication.getPrincipal();		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 clientDetail.getId(), 
												 clientDetail.getUsername(), 
												 clientDetail.getEmail()));
	}
	
	@PostMapping("/signup")
	// Verify email and username
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUp signUp) {
		if (clientRepo.existsByUsername(signUp.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already exists!"));
		}

		if (clientRepo.existsByEmail(signUp.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already exists!"));
		}

		// Create new user's account
		Client client = new Client(signUp.getUsername(), 
							 signUp.getEmail(),
							 encoder.encode(signUp.getPassword()));


		clientRepo.save(client);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

}
