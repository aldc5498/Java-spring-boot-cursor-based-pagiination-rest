package com.example.CurosrPagination.dto;


import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import static java.util.Objects.requireNonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursorInfo {

    private Integer first;
    private String after;
    private Integer last;
    private String before;

    public boolean hasNextPageCursor() {
        return after != null && !after.isEmpty();
    }

    public boolean hasPrevPageCursor() {
        return before != null && !before.isEmpty();
    }

    public boolean hasCursors() {
        return hasPrevPageCursor() || hasNextPageCursor();
    }

    public String getCursor() {
        if (!hasCursors()) return "";

        return hasPrevPageCursor() ? before : after;
    }

    public int pageSize() {
        if (first != null) {
            return first;
        } else if (last != null) {
            return last;
        } else {
            throw new IllegalArgumentException("Invalid arguments");
        }
    }

    public static String encode(Integer id) {
        requireNonNull(id);
        return Base64.getEncoder().encodeToString(String.valueOf(id).getBytes(StandardCharsets.UTF_8));
    }

    public static Integer decode(String cursor) {
        requireNonNull(cursor);
        return Integer.valueOf(
                new String(Base64.getDecoder().decode(cursor.getBytes(StandardCharsets.UTF_8))));
    }

}
