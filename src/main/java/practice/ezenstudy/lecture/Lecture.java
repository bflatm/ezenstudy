package practice.ezenstudy.lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import practice.ezenstudy.student.Enrollment;
import practice.ezenstudy.teacher.Teacher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 강의 제목 중복 불가
    private String title;

    private String description;

    @ManyToOne
    private Teacher teacher;

    private Integer price;

    @OneToMany(mappedBy = "lecture")
    private List<Enrollment> enrollments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private LectureCategory category;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime modifiedDateTime = LocalDateTime.now();

    // 외부에서 받아야만 하는 데이터들만 constructor의 파라미터로 지정
    public Lecture(String title, String description, Teacher teacher, Integer price, LectureCategory category) {
        this.title = title;
        this.description = description;
        this.teacher = teacher;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Integer getPrice() {
        return price;
    }

    public LectureCategory getCategory() {
        return category;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void updateTitleDescriptionPrice(
            String title,
            String description,
            Integer price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
