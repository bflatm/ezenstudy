package practice.ezenstudy.lecture;

public record CreateLectureRequest(
        String title,
        String description,
        Long teacherId,
        Integer price,
        LectureCategory category
) {
}
