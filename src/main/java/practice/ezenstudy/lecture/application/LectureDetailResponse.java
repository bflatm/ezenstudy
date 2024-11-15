package practice.ezenstudy.lecture.application;

import practice.ezenstudy.lecture.domain.LectureCategory;

import java.time.LocalDateTime;
import java.util.List;

public record LectureDetailResponse(
        String title,
        String description,
        Integer price,
        Integer studentCount,
        List<StudentResponse> students,
        LectureCategory category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record StudentResponse(
            String nickname,
            LocalDateTime enrolledAt
    ) {
    }
}
