package practice.ezenstudy.lecture.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.ezenstudy.lecture.domain.Lecture;
import practice.ezenstudy.lecture.domain.LectureRepository;
import practice.ezenstudy.student.domain.Enrollment;
import practice.ezenstudy.teacher.Teacher;
import practice.ezenstudy.teacher.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final TeacherRepository teacherRepository;
    private final LectureMapper lectureMapper;

    public LectureService(LectureRepository lectureRepository, TeacherRepository teacherRepository, LectureMapper lectureMapper) {
        this.lectureRepository = lectureRepository;
        this.teacherRepository = teacherRepository;
        this.lectureMapper = lectureMapper;
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

    public PagedLecturesResponse getAllLectures(LectureSearchParams params) {
        int totalCount = lectureMapper.countAll(params);
        return new PagedLecturesResponse(
                totalCount / params.pageSize() + 1,
                totalCount,
                params.pageNumber(),
                params.pageSize(),
                lectureMapper.findAll(params)
        );
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

    public void deleteById(Long id) {
//        Lecture lecture = lectureRepository.findById(id)
//                .orElse(null);
//        if (lecture == null) {
//            throw new NoSuchElementException("강의 못 찾음");
//        }

        lectureRepository.deleteById(id);
    }

    public LectureDetailResponse create(CreateLectureRequest request) {
        /*
         * 강의 오브젝트를 만들 때는 강사 ID가 아니라 강사 오브젝트를 전달해야 하기 때문에
         * 강의 등록 요청과 함께 받은 강사 ID로 강사 오브젝트를 찾아야 함
         * */
        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElse(null);
        if (teacher == null) {
            throw new IllegalArgumentException("잘못된 강사 ID: " + request.teacherId());
        }

        // 강의 등록
        Lecture lecture = lectureRepository.save(
                new Lecture(
                        request.title(),
                        request.description(),
                        teacher,
                        request.price(),
                        request.category()
                )
        );

        // 등록된 강의 데이터를 응답으로 보냄
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

    @Transactional
    public void setLecturePublicStatus(Long lectureId, UpdatePublicStatusRequest request) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NoSuchElementException(""));

        lecture.setPublic(request.isPublic());
    }

    public void createLectures(CreateLecturesRequest request) {
        for (CreateLectureRequest lecture : request.lectures()) {
            // create() 안에 강사를 찾는 코드가 있는데 JPA가 한 번 찾은 강사는 캐싱해 두기 때문에
            // 반복해서 같은 강사를 찾아도 SQL 쿼리가 추가로 실행되지 않음
            this.create(lecture);
        }
    }
}
