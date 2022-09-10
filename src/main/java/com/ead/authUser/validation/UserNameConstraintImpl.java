package com.ead.authUser.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class UserNameConstraintImpl implements ConstraintValidator<UserNameConstraint, String>{

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		if (userName == null || userName.trim().isEmpty() || userName.contains(" ")) {
			return false;
		}
		return true;
	}

}