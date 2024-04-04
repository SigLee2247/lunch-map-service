package com.wanted.lunchmapservice.user.dto.request;


import com.wanted.lunchmapservice.user.validation.annotation.CommonPasswordValid;
import com.wanted.lunchmapservice.user.validation.annotation.PasswordInfoSameValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@PasswordInfoSameValid
public class UserPostRequestDto {

    @NotNull(message = "계정 이름은 빈 값 일 수 없 습니다.")
    private String userName;


    @Setter
    @Length(min = 10, message = "10자리 이상 되어야 합니다.")
    @Pattern(regexp = "^[^가-힣]*$", message = "한글은 비밀번호로 사용할 수 없습니다.")
    @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)$)[A-Za-z\\d~!@#$%^&*()_+=]+$",
            message = "문자 숫자 특수문자 중 2가지 종류 이상을 사용 해야 합니다.")
    @Pattern(regexp = "^(?!.*([A-Za-z\\d~!@#$%^&*()_+=])\\1{2,}).*$",
            message = "3회 이상 연속 되는 문자를 사용할 수 없 습니다.")
    @CommonPasswordValid
    private String password;
}
