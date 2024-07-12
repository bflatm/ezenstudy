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
import org.hibernate.annotations.ColumnDefault;
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

    /*
    * 강의는 등록되면 기본적으로 비공개여야 한다는 요구 사항이 있음
    * */
    @ColumnDefault("false") // DB 테이블을 만드는 SQL이 만들어질 때 컬럼에 기본값 지정
    private boolean isPublic = false; // 기본값이 false임을 명시함

    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime modifiedDateTime = LocalDateTime.now();

    // JPA가 SELECT 후 조회한 데이터로 강의 오브젝트를 만들 때 사용됨
    protected Lecture() {
    }

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

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
