package practice.ezenstudy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    protected Student() {
    }

    public Student(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
