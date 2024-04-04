package com.wanted.lunchmapservice.user.validation.annotation;

import com.wanted.lunchmapservice.user.validation.validator.CommonPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CommonPasswordValidator.class)
public @interface CommonPasswordValid {

    String message() default "해당 비밀 번호는 사용할 수 없 습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
