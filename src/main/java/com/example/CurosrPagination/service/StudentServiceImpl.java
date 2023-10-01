package com.example.CurosrPagination.service;

import com.example.CurosrPagination.dto.CursorInfo;
import com.example.CurosrPagination.dto.StudentConnection;
import com.example.CurosrPagination.dto.StudentEdge;
import com.example.CurosrPagination.dto.StudentInfo;
import com.example.CurosrPagination.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    @Override
    public StudentConnection getStudentConnection(CursorInfo cursorInfo) {

        int pageSize = cursorInfo.pageSize();
        var limit = pageSize + 1;
        List<Map<String, Object>> studentResult;
        boolean hasNextPage = false;
        boolean hasPreviousPage;
        String startCursor;
        String endCursor;
        if (cursorInfo.hasCursors() && cursorInfo.hasNextPageCursor()) {
            studentResult = this.studentRepo.studentWithNextPageCursor(CursorInfo.decode(cursorInfo.getCursor()), limit);
            int resultSize = studentResult.size();
            var firstResult = studentResult.get(0);
            hasPreviousPage = Boolean.parseBoolean(firstResult.get("has_page").toString());
            startCursor = CursorInfo.encode(Integer.valueOf(firstResult.get("id").toString()));
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(Integer.valueOf(studentResult.get(endCursorIndex).get("id").toString()));
            hasNextPage = resultSize > pageSize;

        } else if (cursorInfo.hasCursors() && cursorInfo.hasPrevPageCursor()) {
            studentResult = this.studentRepo.studentWithPreviousPageCursor(CursorInfo.decode(cursorInfo.getCursor()), limit);
            int resultSize = studentResult.size();
            System.out.println(studentResult);
            System.out.println(resultSize);
            var firstResult = studentResult.get(0);
            hasPreviousPage = Boolean.parseBoolean(firstResult.get("has_page").toString());
            startCursor = CursorInfo.encode(Integer.valueOf(firstResult.get("id").toString()));
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(Integer.valueOf(studentResult.get(endCursorIndex).get("id").toString()));
            hasPreviousPage = resultSize > pageSize;
        } else {
            studentResult = studentRepo.studentWithoutCursor(limit);
            int resultSize = studentResult.size();
            hasPreviousPage = false;
            var firstResult = studentResult.get(0);
            startCursor = CursorInfo.encode(Integer.valueOf(firstResult.get("id").toString()));
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(Integer.valueOf(studentResult.get(endCursorIndex).get("id").toString()));
            hasNextPage = resultSize > pageSize;
        }

        // TODO check what results are returned when requested out of range
        if (studentResult.size() == 0) {
            return new StudentConnection(null, new StudentInfo(null, null, false, false));
        }

        List<StudentEdge> studentEdges = new ArrayList<>();
        studentResult.stream()
                .limit(cursorInfo.pageSize())
                .forEach(s -> {
                    String cursor = CursorInfo.encode(Integer.valueOf(s.get("id").toString()));
                    StudentEdge s1 = new StudentEdge();
                    s1.setCursor(cursor);
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("id", s.get("id"));
                    map1.put("name", s.get("name"));
                    map1.put("address", s.get("address"));
                    map1.put("phone_number", s.get("phone_number"));
                    s1.setStudent(map1);
                    System.out.println(s1);
                    studentEdges.add(s1);

                });

        return new StudentConnection(
                studentEdges, new StudentInfo(startCursor, endCursor, hasNextPage, hasPreviousPage));


    }
}
