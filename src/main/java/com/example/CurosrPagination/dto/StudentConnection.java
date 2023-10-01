package com.example.CurosrPagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentConnection {

    private List<StudentEdge> edges;
    private StudentInfo pageInfo;
}
