package practice.ezenstudy.student.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import practice.ezenstudy.lecture.domain.Lecture;

import java.time.LocalDateTime;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Lecture lecture;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    public Enrollment() {
    }

    public Enrollment(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }

    public Enrollment(Student student, Lecture lecture, LocalDateTime createdDateTime) {
        this.student = student;
        this.lecture = lecture;
        this.createdDateTime = createdDateTime;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public boolean isEnrolledByStudent(Student student) {
        return this.student.getId().equals(student.getId());
    }

    public boolean isWithinCancellationPeriod(LocalDateTime now) {
        // 만으로 7일째 되는 날 자정 전까지 취소 신청을 받아주기로 했다고 가정
        LocalDateTime cancelDeadline = createdDateTime.plusDays(7)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
        return now.isBefore(cancelDeadline) || now.isEqual(cancelDeadline);
    }
}
