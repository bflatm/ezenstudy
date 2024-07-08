package practice.ezenstudy.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import practice.ezenstudy.teacher.Teacher;

import java.time.LocalDateTime;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private Teacher teacher;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private LectureCategory category;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime modifiedDateTime = LocalDateTime.now();

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
}
