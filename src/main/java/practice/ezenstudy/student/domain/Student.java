package practice.ezenstudy.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import practice.ezenstudy.SecurityUtils;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;

    protected Student() {
    }

    public Student(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Student(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public boolean authenticate(String rawPassword) {
        String hashedInputPassword = SecurityUtils.sha256Encrypt(rawPassword);
        return this.password.equals(hashedInputPassword);
    }

    public void changePassword(String rawPassword) {
        this.password = SecurityUtils.sha256Encrypt(rawPassword);
    }
}
