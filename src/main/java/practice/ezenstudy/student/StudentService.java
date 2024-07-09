package practice.ezenstudy.student;

import org.springframework.stereotype.Service;
import practice.ezenstudy.SecurityUtils;
import practice.ezenstudy.lecture.Lecture;
import practice.ezenstudy.lecture.LectureRepository;

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
        Lecture lecture = lectureRepository.findById(request.lectureId())
                .orElse(null);

        if (student == null || lecture == null) {
            throw new IllegalArgumentException("강의 또는 회원 ID가 잘못됨");
        }

        enrollmentRepository.save(new Enrollment(
                student,
                lecture
        ));
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
}
