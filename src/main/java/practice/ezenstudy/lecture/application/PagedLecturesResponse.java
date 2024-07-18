package practice.ezenstudy.lecture.application;

import java.util.List;

public record PagedLecturesResponse(
        int totalPage,
        int totalCount,
        int pageNumber,
        int pageSize,
        List<LectureResponse> items
) {
}
