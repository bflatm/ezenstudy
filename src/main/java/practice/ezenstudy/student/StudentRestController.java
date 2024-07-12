package practice.ezenstudy.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import practice.ezenstudy.JwtProvider;

@RestController
public class StudentRestController {

    private final StudentService studentService;
    private final JwtProvider jwtProvider;

    public StudentRestController(StudentService studentService, JwtProvider jwtProvider) {
        this.studentService = studentService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/students")
    public void register(@RequestBody RegisterStudentRequest request) {
        studentService.create(request);
    }

    @PostMapping("/enrollments")
    public void enroll(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody EnrollRequest request
    ) {
        String[] tokenFormat = authorization.split(" ");
        String tokenType = tokenFormat[0];
        String token = tokenFormat[1];

        if (tokenType.equals("Bearer") == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        String userEmail = jwtProvider.getSubject(token);

        studentService.enroll(userEmail, request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return studentService.authenticateAndGenerateToken(request);
    }

    @GetMapping("/me")
    public StudentResponse getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        // HTTP 요청 헤더의 Authorization의 값(Bearer eyJhbGci...)이
        // 파라미터 authorization으로 전달됨

        // 먼저 전달받은 String 값을 Bearer와 eyJhbGci... 둘로 분리해야 함 - split() 사용
        String[] tokenFormat = authorization.split(" ");
        // split()은 배열을 return하는데,
        // 이 배열의 첫 번째 값이 "Bearer", 두 번째 값이 "eyJhbGci..."
        String tokenType = tokenFormat[0];
        String token = tokenFormat[1];

        // Bearer 토큰인지 검증
        if (tokenType.equals("Bearer") == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // 유효한 JWT 토큰인지 검증
        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        // JWT 토큰에서 email을 끄집어냄
        String userEmail = jwtProvider.getSubject(token);

        return studentService.getCurrentUser(userEmail);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
