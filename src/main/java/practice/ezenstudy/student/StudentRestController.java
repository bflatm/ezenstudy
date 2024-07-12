package practice.ezenstudy.student;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void enroll(@RequestBody EnrollRequest request) {
        studentService.enroll(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return studentService.authenticateAndGenerateToken(request);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
