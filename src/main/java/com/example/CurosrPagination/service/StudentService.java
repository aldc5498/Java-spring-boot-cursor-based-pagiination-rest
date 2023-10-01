package com.example.CurosrPagination.service;

import com.example.CurosrPagination.dto.CursorInfo;
import com.example.CurosrPagination.dto.StudentConnection;

public interface StudentService {

    StudentConnection getStudentConnection(CursorInfo cursorInfo);

}
