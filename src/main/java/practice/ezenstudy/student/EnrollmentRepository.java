package practice.ezenstudy.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    void deleteAllByStudentId(Long id);
}
