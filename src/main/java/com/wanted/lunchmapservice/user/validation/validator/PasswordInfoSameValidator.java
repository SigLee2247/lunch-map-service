package com.wanted.lunchmapservice.user.validation.validator;


import com.wanted.lunchmapservice.user.dto.request.UserPostRequestDto;
import com.wanted.lunchmapservice.user.validation.annotation.PasswordInfoSameValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordInfoSameValidator implements ConstraintValidator<PasswordInfoSameValid, UserPostRequestDto> {

    @Override
    public void initialize(PasswordInfoSameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserPostRequestDto value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String userName = value.getUserName();

        return !password.contains(userName);
    }
}
