package com.wanted.lunchmapservice.common.dto;

import com.wanted.lunchmapservice.common.PagingUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ResponseListDto {

    private PagingUtil pagingUtil;
}
