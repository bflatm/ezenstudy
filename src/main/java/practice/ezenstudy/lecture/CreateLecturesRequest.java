package practice.ezenstudy.lecture;

import java.util.List;

public record CreateLecturesRequest(
        List<CreateLectureRequest> lectures
) {
}
