package practice.ezenstudy;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public LectureDetailResponse findById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElse(null);

        if (lecture == null) {
            throw new NoSuchElementException("강의를 찾을 수 없습니다 ID: " + lectureId);
        }

        return new LectureDetailResponse(
                lecture.getTitle(),
                lecture.getDescription(),
                lecture.getPrice(),
                0,
                new ArrayList<>(),
                lecture.getCategory(),
                lecture.getCreatedDatetime(),
                lecture.getModifiedDateTime()
        );
    }
}
