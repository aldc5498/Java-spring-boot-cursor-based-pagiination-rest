package com.example.CurosrPagination.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentEdge {

    private String cursor;
    private Map<String, Object> student;
}
