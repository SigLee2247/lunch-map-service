package com.wanted.lunchmapservice.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagingUtil {

    private final long totalElements;
    private final int totalPages;
    private final int pageNumber;
    private final int pageSize;
    private final int totalPageGroups;
    private final int pageGroupSize = 5;
    private final int pageGroup;
    private final int startPage;
    private final int endPage;
    private final boolean existPrePageGroup;
    private final boolean existNextPageGroup;

    public PagingUtil(long totalElements, int totalPages, int pageNumber, int pageSize) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber + 1;
        this.pageSize = pageSize;
        this.totalPageGroups = setTotalPageGroups();
        this.pageGroup = setPageGroup();
        this.startPage = setStartPage();
        this.endPage = setEndPage();
        this.existPrePageGroup = setExistPrePageGroup();
        this.existNextPageGroup = setExistNextPageGroup();
    }

    public int setTotalPageGroups() {
        if (this.totalPages % this.pageGroupSize == 0) {
            return this.totalPages / this.pageGroupSize;
        }
        return this.totalPages / this.pageGroupSize + 1;
    }

    public int setPageGroup() {
        if (this.pageNumber % this.pageGroupSize == 0) {
            return this.pageNumber / this.pageGroupSize;
        }
        return this.pageNumber / this.pageGroupSize + 1;
    }

    private int setStartPage() {
        return (this.pageGroup - 1) * this.pageGroupSize + 1;
    }

    private int setEndPage() {
        int endPage = this.pageGroup * this.pageGroupSize;
        return Math.min(this.totalPages, endPage);
    }

    public boolean setExistPrePageGroup() {
        return this.pageGroup > 1;
    }

    public boolean setExistNextPageGroup() {
        return this.pageGroup < this.totalPageGroups;
    }

    public static <T> PagingUtil getPagingUtil(Page<T> pagenatedData) {
        return new PagingUtil(pagenatedData.getTotalElements(), pagenatedData.getTotalPages()
                , pagenatedData.getNumber(), pagenatedData.getSize());
    }
}
