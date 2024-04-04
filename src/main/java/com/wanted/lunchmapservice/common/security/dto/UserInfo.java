package com.wanted.lunchmapservice.common.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String userName;
    private String authorities;
}
