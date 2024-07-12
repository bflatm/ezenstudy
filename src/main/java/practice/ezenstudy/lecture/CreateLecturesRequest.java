package practice.ezenstudy.lecture;

import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateLecturesRequest(
        @Size(min = 1, max = 10)
        List<CreateLectureRequest> lectures
) {
}
