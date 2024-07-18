package practice.ezenstudy.lecture.application;

import practice.ezenstudy.lecture.domain.LectureCategory;

public record CreateLectureRequest(
        String title,
        String description,
        Long teacherId,
        Integer price,
        LectureCategory category
) {
}
