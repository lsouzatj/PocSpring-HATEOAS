package com.ead.authUser.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authUser.dtos.UserDTO;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.services.UserService;
import com.ead.authUser.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("allUsers")
	public ResponseEntity<Object> getAllUser(SpecificationTemplate.UserSpect spec,
										     @PageableDefault(page = 0, 
										     size = 2, 
										     sort = "userName",
										     direction = Sort.Direction.DESC) 
										     Pageable pageable){
		
		Page<UserModel> pageUserModel = userService.findAllPageable(spec, pageable);
		
		if (pageUserModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List users not found");
		}
		
		for (UserModel userModel : pageUserModel.toList()) {
			userModel.add(linkTo(methodOn(UserController.class).getOneUser(userModel.getUserId())).withSelfRel());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(pageUserModel);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId){
		
		var userModel = userService.findById(userId);
		
		if (userModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
											 @JsonView(UserDTO.UserView.UserPut.class)
									 		 @Validated(UserDTO.UserView.UserPut.class)
											 @RequestBody UserDTO userDTO){
		
		var userModel = userService.findById(userId);
		
		if (userModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		userModel.get().setFullName(userDTO.getFullName());
		userModel.get().setPhoneNumber(userDTO.getPhoneNumber());
		userModel.get().setCpf(userDTO.getCpf());
		userModel.get().setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		userService.save(userModel.get());
		
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
		
		var userModel = userService.findById(userId);
		
		if (userModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		userService.delet(userModel.get());
		
		return ResponseEntity.status(HttpStatus.OK).body("User deleted with sucess");
	}
}
