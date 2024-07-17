package practice.ezenstudy.lecture;

public record LectureSearchParams(
        String sort,
        String title,
        String teacherName,
        String category,
        String studentId
) {
}
