package com.ead.authUser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authUser.dtos.UserDTO;
import com.ead.authUser.enums.UserStatus;
import com.ead.authUser.enums.UserType;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("auth")
public final class AuthenticationController {

	@Autowired
	UserService userService;
	
	@PostMapping("signup")
	public ResponseEntity<Object> registerUser(@JsonView(UserDTO.UserView.RegistrationPost.class)
											   @RequestBody 
											   @Validated(UserDTO.UserView.RegistrationPost.class)
											   UserDTO userDTO){
		
		if (userService.existsUserByUserName(userDTO.getUserName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User name is exitst");
		}
		
		if (userService.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail is exitst");
		}
		
		var userModel = new UserModel();
		
		BeanUtils.copyProperties(userDTO, userModel);
		
		userModel.setUserStatus(UserStatus.ACTIVE);
		userModel.setUserType(UserType.STUDENT);
		userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		userService.save(userModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
	}
}
