package practice.ezenstudy.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.ezenstudy.JwtProvider;
import practice.ezenstudy.SecurityUtils;
import practice.ezenstudy.lecture.Lecture;
import practice.ezenstudy.lecture.LectureRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final JwtProvider jwtProvider;

    public StudentService(
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository,
            LectureRepository lectureRepository,
            JwtProvider jwtProvider
    ) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lectureRepository = lectureRepository;
        this.jwtProvider = jwtProvider;
    }

    public void create(RegisterStudentRequest request) {
        studentRepository.save(new Student(
                request.email(),
                request.nickname(),
                SecurityUtils.sha256Encrypt(request.password())));
    }

    public void enroll(String userEmail, EnrollRequest request) {
        Student student = studentRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 학생 email: " + userEmail));

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

    public LoginResponse authenticateAndGenerateToken(LoginRequest request) {
        Student student = authenticate(request);
        String token = generateToken(student);
        return new LoginResponse(token);
    }

    private Student authenticate(LoginRequest request) {
        // email 검증
        Student student = studentRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("ID 또는 PW가 틀립니다"));

        // password 검증
        if (!student.authenticate(request.password())) {
            throw new IllegalArgumentException("ID 또는 PW가 틀립니다");
        }

        return student;
    }

    public String generateToken(Student student) {
        // 주입받은 JwtProvider 오브젝트를 통해 토큰 발급
        return jwtProvider.createToken(student.getEmail());
    }

    @Transactional
    public void deleteById(Long id) {
        enrollmentRepository.deleteAllByStudentId(id);
        studentRepository.deleteById(id);
    }

    public StudentResponse getCurrentUser(String userEmail) {
        Student student = studentRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException(""));
        return new StudentResponse(
                student.getEmail(),
                student.getNickname()
        );
    }
}
