package practice.ezenstudy.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.ezenstudy.SecurityUtils;
import practice.ezenstudy.lecture.Lecture;
import practice.ezenstudy.lecture.LectureRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, LectureRepository lectureRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lectureRepository = lectureRepository;
    }

    public void create(RegisterStudentRequest request) {
        studentRepository.save(new Student(
                request.email(),
                request.nickname(),
                SecurityUtils.sha256Encrypt(request.password())));
    }

    public void enroll(EnrollRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElse(null);
        if (student == null) {
            throw new IllegalArgumentException("잘못된 학생 ID: " + request.studentId());
        }

        List<Lecture> lectures = lectureRepository.findAllById(request.lectureIds());
        if (request.lectureIds().size() != lectures.size()) {
            throw new IllegalArgumentException("잘못된 강의 ID가 포함되어 있음");
        }

        List<Enrollment> enrollments = lectures.stream()
                .map(lecture -> new Enrollment(
                        student,
                        lecture))
                .toList();

        enrollmentRepository.saveAll(enrollments);
    }

    public void checkEmailPassword(LoginRequest request) {
        Student student = studentRepository.findByEmail(request.email())
                .orElse(null);

        if (student == null) {
            throw new IllegalArgumentException("ID 또는 PW가 틀립니다");
        }

        if (!student.getPassword()
                .equals(SecurityUtils.sha256Encrypt(request.password()))) {
            throw new IllegalArgumentException("ID 또는 PW가 틀립니다");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        enrollmentRepository.deleteAllByStudentId(id);
        studentRepository.deleteById(id);
    }
}
