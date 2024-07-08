package practice.ezenstudy.lecture;

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
