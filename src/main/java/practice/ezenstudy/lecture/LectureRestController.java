package practice.ezenstudy.lecture;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LectureRestController {

    private final LectureService lectureService;

    public LectureRestController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("/lectures")
    public LectureDetailResponse create(@RequestBody CreateLectureRequest request) {
        return lectureService.create(request);
    }

    @PostMapping("/lectures/bulk")
    public void createLectures(@Valid @RequestBody CreateLecturesRequest request) {
        lectureService.createLectures(request);
    }

    @GetMapping("/lectures/{lectureId}")
    LectureDetailResponse findOne(@PathVariable Long lectureId) {
        return lectureService.findById(lectureId);
    }

    @GetMapping("/lectures")
    List<LectureResponse> getAllLectures(LectureSearchParams params) {
        return lectureService.getAllLectures(params);
    }

    @PutMapping("/lectures/{lectureId}")
    void update(@PathVariable Long lectureId, @Valid @RequestBody UpdateLectureRequest body) {
        lectureService.updateById(lectureId, body);
    }

    @PatchMapping("/lectures/{lectureId}")
    public void updateLecturePublicStatus(
            @PathVariable Long lectureId,
            @Valid @RequestBody UpdatePublicStatusRequest request) {
        lectureService.setLecturePublicStatus(lectureId, request);
    }

    @DeleteMapping("/lectures/{id}")
    void delete(@PathVariable Long id) {
        lectureService.deleteById(id);
    }
}
