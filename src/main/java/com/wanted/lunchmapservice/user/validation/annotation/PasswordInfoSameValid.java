package com.wanted.lunchmapservice.user.validation.annotation;

import com.wanted.lunchmapservice.user.validation.validator.PasswordInfoSameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordInfoSameValidator.class)
public @interface PasswordInfoSameValid {

    String message() default "다른 정보들과 유사한 비밀번호를 사용할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
