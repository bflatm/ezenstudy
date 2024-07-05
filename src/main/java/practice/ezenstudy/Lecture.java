package practice.ezenstudy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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

    private LocalDateTime createdDatetime;

    private LocalDateTime modifiedDateTime;

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
        return createdDatetime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }
}
