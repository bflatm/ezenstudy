package practice.ezenstudy.lecture;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateLectureRequest(
        @NotNull String title,
        String description,
        @PositiveOrZero Integer price
) {
}
