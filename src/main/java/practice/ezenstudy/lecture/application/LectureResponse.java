package practice.ezenstudy.lecture.application;

import practice.ezenstudy.lecture.domain.LectureCategory;

import java.time.LocalDateTime;

public record LectureResponse(
        Long lectureId,
        String title,
        String teacher,
        Integer price,
        Integer studentCount,
        LectureCategory category,
        LocalDateTime createdAt
) {
}
