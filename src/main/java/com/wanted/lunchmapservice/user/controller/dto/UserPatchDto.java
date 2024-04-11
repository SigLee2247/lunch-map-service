package com.wanted.lunchmapservice.user.controller.dto;

import com.wanted.lunchmapservice.user.entity.enums.ServiceAccess;

public record UserPatchDto(String username, Double lat, double lon, ServiceAccess serviceAccess) {

}
