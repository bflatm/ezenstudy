package practice.ezenstudy.student.application;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.ezenstudy.JwtProvider;
import practice.ezenstudy.SecurityUtils;
import practice.ezenstudy.lecture.domain.Lecture;
import practice.ezenstudy.lecture.domain.LectureRepository;
import practice.ezenstudy.student.domain.Enrollment;
import practice.ezenstudy.student.domain.EnrollmentRepository;
import practice.ezenstudy.student.domain.Student;
import practice.ezenstudy.student.domain.StudentRepository;

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

    public void cancelEnrollment(String userEmail, Long enrollmentId) {
        // email로 학생을 찾는다. 못 찾으면 에러 발생
        Student student = studentRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("학생을 찾을 수 없습니다 email: " + userEmail));

        // 정말 본인이 수강 신청한 것이 맞는지 확인하기 위해 수강 신청 내역을 조회
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EntityNotFoundException("수강 신청 내역을 찾을 수 없습니다 id: " + enrollmentId));

        // 본인이 신청한 것이 맞는지 확인
        if (!enrollment.isEnrolledByStudent(student)) {
            throw new IllegalStateException("수강 신청 취소할 권한이 없는 학생입니다");
        }

        // 데이터베이스에서 수강 신청 내역 삭제
        enrollmentRepository.deleteById(enrollmentId);
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
    public void deleteByEmail(String email) {
        enrollmentRepository.deleteAllByStudentEmail(email);
        studentRepository.deleteByEmail(email);
    }

    public StudentResponse getCurrentUser(String userEmail) {
        Student student = studentRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException(""));
        return new StudentResponse(
                student.getEmail(),
                student.getNickname()
        );
    }

    @Transactional
    public void changePassword(String userEmail, ChangePasswordRequest request) {
        Student student = studentRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        if (!student.authenticate(request.oldPassword())) {
            throw new IllegalArgumentException("비밀번호 변경에 실패했습니다. 현재 비밀번호를 확인해 주세요.");
        }

        student.changePassword(request.newPassword());
    }
}
