package com.ead.authUser.dtos;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ead.authUser.validation.UserNameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public final class UserDTO {
	
	public interface UserView{
		public static interface RegistrationPost{}
		public static interface UserPut{}
		public static interface PasswordPut{}
		public static interface ImagePut{}
	}

	private UUID userId;

	@UserNameConstraint(message = "{userName.format}",groups = UserView.RegistrationPost.class)
	@JsonView(UserView.RegistrationPost.class)
	@NotBlank(message = "{userName.notBlank}", groups = UserView.RegistrationPost.class)
	@Size(min = 4, max = 50, message = "{userName.size}", groups = UserView.RegistrationPost.class)
	private String userName;
	
	@JsonView(UserView.RegistrationPost.class)
	@NotBlank(message = "{email.notBlank}", groups = UserView.RegistrationPost.class)
	@Email(message = "{email.format}", groups = UserView.RegistrationPost.class)
	private String email;

	@JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
	@NotBlank(message = "{password.notBlank}", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
	@Size(min = 4, max = 10, message = "{password.size}", groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
	private String password;

	@JsonView({UserView.PasswordPut.class})
	@NotBlank(message = "{password.notBlank}", groups = UserView.PasswordPut.class)
	@Size(min = 4, max = 10, message = "{password.size}", groups = UserView.PasswordPut.class)
	private String oldPassword;
	
	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String fullName;
	
	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String phoneNumber;

	@JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
	private String cpf;
	
	@JsonView(UserView.ImagePut.class)
	@NotBlank(groups = UserView.ImagePut.class)	
	private String imageUrl;
}
