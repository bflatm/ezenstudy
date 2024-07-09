package practice.ezenstudy.student;

import java.util.List;

public record EnrollRequest(
        List<Long> lectureIds,
        Long studentId
) {
}
