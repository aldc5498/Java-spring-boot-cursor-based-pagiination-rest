package com.example.CurosrPagination.repo;

import com.example.CurosrPagination.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT id,name, address, phone_number,\n" +
            "            EXISTS (SELECT 1 FROM student WHERE id < ?1) AS has_page\n" +
            "            FROM student\n" +
            "            WHERE id > ?1\n" +
            "            ORDER BY id\n" +
            "            LIMIT ?2", nativeQuery = true)
    List<Map<String, Object>> studentWithNextPageCursor(Integer id, int limit);

    @Query(value = "  SELECT id,name, address, phone_number,\n" +
            "            EXISTS (SELECT 1 FROM student WHERE id > ?1) AS has_page\n" +
            "            FROM student\n" +
            "            WHERE id < ?1\n" +
            "            ORDER BY id\n" +
            "            LIMIT ?2", nativeQuery = true)
    List<Map<String, Object>> studentWithPreviousPageCursor(Integer id, int limit);

    @Query(value = "\n" +
            "            SELECT id,name, address, phone_number,\n" +
            "            false AS has_page\n" +
            "            FROM student\n" +
            "            ORDER BY ID LIMIT ?1", nativeQuery = true)
    List<Map<String, Object>> studentWithoutCursor(int limit);
}
