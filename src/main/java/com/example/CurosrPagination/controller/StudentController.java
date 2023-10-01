package com.example.CurosrPagination.controller;

import com.example.CurosrPagination.dto.CursorInfo;
import com.example.CurosrPagination.dto.StudentConnection;
import com.example.CurosrPagination.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/")
    public ResponseEntity<?> getAllStudents(@RequestParam(required = false, defaultValue = "10") Integer first,@RequestParam(required = false) Integer last,@RequestParam(required = false) String after,@RequestParam(required = false) String before){

        System.out.println(first);
        CursorInfo cursorInfo = new CursorInfo(first,after,last,before);

        if (first !=null)
            cursorInfo.setFirst(first);
        StudentConnection studentConnection = studentService.getStudentConnection(cursorInfo);
        return  ResponseEntity.ok(studentConnection);
    }


}
