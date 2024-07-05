package practice.ezenstudy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LectureRestController {


    @GetMapping("/lectures/{lectureId}")
    LectureDetailResponse findOne(@PathVariable Long lectureId) {
        return new LectureDetailResponse(
                "",
                "",
                0,
                0,
                List.of(),
                LectureCategory.과학,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}
