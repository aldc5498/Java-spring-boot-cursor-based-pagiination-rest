package com.example.CurosrPagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentInfo {
    private String startCursor;
    private String endCursor;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;

}
