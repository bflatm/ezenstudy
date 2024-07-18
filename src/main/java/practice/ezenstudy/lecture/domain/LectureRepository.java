package practice.ezenstudy.lecture.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    // JPA Query Methods
    List<Lecture> findAllByIsPublicIsTrue();
    List<Lecture> findAllByIsPublicIsTrueOrderByCreatedDateTimeDesc();
}
