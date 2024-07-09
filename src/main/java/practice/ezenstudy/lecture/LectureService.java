package practice.ezenstudy.lecture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.ezenstudy.student.Enrollment;

import java.util.List;
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

        List<Enrollment> enrollments = lecture.getEnrollments();

        return new LectureDetailResponse(
                lecture.getTitle(),
                lecture.getDescription(),
                lecture.getPrice(),
                enrollments.size(),
                enrollments.stream()
                        .map(enrollment -> new LectureDetailResponse.StudentResponse(
                                enrollment.getStudent().getNickname(),
                                enrollment.getCreatedDateTime()))
                        .toList(),
                lecture.getCategory(),
                lecture.getCreatedDatetime(),
                lecture.getModifiedDateTime()
        );
    }

    public List<LectureResponse> findAll(String sort) {
        List<Lecture> lectures = lectureRepository.findAll();

        if (sort != null && sort.equalsIgnoreCase("recent")) {
            lectures = lectureRepository.findAllByOrderByCreatedDateTimeDesc();
        }

        return lectures
                .stream()
                .map(lecture -> new LectureResponse(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getTeacher().getName(),
                        lecture.getPrice(),
                        lecture.getEnrollments().size(),
                        lecture.getCategory(),
                        lecture.getCreatedDatetime()))
                .toList();
    }

    @Transactional
    public void updateById(Long lectureId, UpdateLectureRequest body) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElse(null);

        if (lecture == null) {
            throw new NoSuchElementException("없는 강의 ID: " + lectureId);
        }

        lecture.updateTitleDescriptionPrice(body.title(), body.description(), body.price());
    }
}