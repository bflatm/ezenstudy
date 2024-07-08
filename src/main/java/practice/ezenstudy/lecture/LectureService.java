package practice.ezenstudy.lecture;

import org.springframework.stereotype.Service;
import practice.ezenstudy.student.Enrollment;
import practice.ezenstudy.student.EnrollmentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    public LectureService(LectureRepository lectureRepository, EnrollmentRepository enrollmentRepository) {
        this.lectureRepository = lectureRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public LectureDetailResponse findById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElse(null);

        if (lecture == null) {
            throw new NoSuchElementException("강의를 찾을 수 없습니다 ID: " + lectureId);
        }

//        List<Enrollment> enrollments = enrollmentRepository.findAll()
//                .stream()
//                .filter(enrollment -> Objects.equals(
//                        enrollment.getLecture().getId(),
//                        lecture.getId()))
//                .toList();
        List<Enrollment> enrollments = enrollmentRepository.findAllByLectureId(lectureId);

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
}
