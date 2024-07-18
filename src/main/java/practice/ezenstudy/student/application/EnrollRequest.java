package practice.ezenstudy.student.application;

import java.util.List;

public record EnrollRequest(
        List<Long> lectureIds
) {
}
