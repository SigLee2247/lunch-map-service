package com.wanted.lunchmapservice.common.enums;

import lombok.Getter;

@Getter
public enum CommonEnum {

    YES("Y"),
    NO("N");

    private final String value;

    CommonEnum(String value) {
        this.value = value;
    }
}
