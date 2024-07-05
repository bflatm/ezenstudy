package practice.ezenstudy.lecture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureRestController {

    private final LectureService lectureService;

    public LectureRestController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/lectures/{lectureId}")
    LectureDetailResponse findOne(@PathVariable Long lectureId) {
        return lectureService.findById(lectureId);
    }

}
