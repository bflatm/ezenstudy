package practice.ezenstudy.student.ui;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.ezenstudy.LoginStudent;
import practice.ezenstudy.student.application.ChangePasswordRequest;
import practice.ezenstudy.student.application.EnrollRequest;
import practice.ezenstudy.student.application.LoginRequest;
import practice.ezenstudy.student.application.LoginResponse;
import practice.ezenstudy.student.application.RegisterStudentRequest;
import practice.ezenstudy.student.application.StudentResponse;
import practice.ezenstudy.student.application.StudentService;

@RestController
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public void register(@RequestBody RegisterStudentRequest request) {
        studentService.create(request);
    }

    @PostMapping("/enrollments")
    public void enroll(
            @LoginStudent String userEmail,
            @RequestBody EnrollRequest request
    ) {
        studentService.enroll(userEmail, request);
    }

    @DeleteMapping("/enrollments/{enrollmentId}")
    public void cancelEnrollment(@LoginStudent String userEmail, @PathVariable Long enrollmentId) {
        studentService.cancelEnrollment(userEmail, enrollmentId);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return studentService.authenticateAndGenerateToken(request);
    }

    @GetMapping("/me")
    public StudentResponse getCurrentUser(@LoginStudent String userEmail) {
        return studentService.getCurrentUser(userEmail);
    }

    @PatchMapping("/students")
    public void changePassword(@LoginStudent String userEmail, @RequestBody ChangePasswordRequest request) {
        studentService.changePassword(userEmail, request);
    }

    @DeleteMapping("/students")
    public void delete(@LoginStudent String userEmail) {
        studentService.deleteByEmail(userEmail);
    }
}
