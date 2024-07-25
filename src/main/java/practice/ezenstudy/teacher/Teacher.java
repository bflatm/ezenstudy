package practice.ezenstudy.teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import practice.ezenstudy.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Teacher extends BaseEntity {

    private String name;

    private LocalDate birthday;

    protected Teacher() {
    }

    public Teacher(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDateTime getCreatedDateTime() {
        return super.getCreatedDateTime();
    }

    public LocalDateTime getModifiedDateTime() {
        return super.getModifiedDateTime();
    }

    public void updateNameAndBirthday(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
