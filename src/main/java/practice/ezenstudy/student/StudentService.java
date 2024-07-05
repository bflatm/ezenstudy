package practice.ezenstudy.student;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void create(RegisterStudentRequest request) {
        studentRepository.save(new Student(
                request.email(),
                request.nickname()));
    }
}
