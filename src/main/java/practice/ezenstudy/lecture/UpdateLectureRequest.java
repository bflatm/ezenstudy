package practice.ezenstudy.lecture;

public record UpdateLectureRequest(
        String title,
        String description,
        Integer price
) {
}
