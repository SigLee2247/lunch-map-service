package com.wanted.lunchmapservice.user.utils;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

public class UriCreator {

    private UriCreator() {
    }

    public static URI createUri(String defaultUrl1, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl1 + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
