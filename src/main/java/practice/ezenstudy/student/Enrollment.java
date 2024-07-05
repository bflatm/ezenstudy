package practice.ezenstudy.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import practice.ezenstudy.lecture.Lecture;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Lecture lecture;

    public Enrollment() {
    }

    public Enrollment(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
