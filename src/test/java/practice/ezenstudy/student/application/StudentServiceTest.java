package practice.ezenstudy.student.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.ezenstudy.SecurityUtils;
import practice.ezenstudy.student.domain.Student;
import practice.ezenstudy.student.domain.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @DisplayName("요청의 `현재 비밀번호`가 정확하면 학생의 비밀번호를 요청의 `새 비밀번호`로 변경한다")
    @Test
    void 비밀번호_변경_성공() {
        // Given: studentRepository.findByEmail() 호출 시 도라에몽_학생이 return되도록 고정
        String 현재_비밀번호 = "pw";
        String 새_비밀번호 = "newpw";
        String 현재_비밀번호_해시 = SecurityUtils.sha256Encrypt(현재_비밀번호);
        String 새_비밀번호_해시 = SecurityUtils.sha256Encrypt(새_비밀번호);

        Student 도라에몽_학생 = new Student(
                "dora@gmail.com",
                "도라에몽",
                현재_비밀번호_해시
        );

        given(studentRepository.findByEmail(any()))
                .willReturn(Optional.of(도라에몽_학생));

        // When: 일치하는 `현재_비밀번호`와 함께 비밀번호 변경을 요청하면
        studentService.changePassword(
                도라에몽_학생.getEmail(), // 사실 어떤 email을 넣든 `도라에몽_학생`의 비밀번호를 변경함
                new ChangePasswordRequest(현재_비밀번호, 새_비밀번호) // API 클라이언트의 요청을 흉내냄
        );

        // Then: 비밀번호가 `새_비밀번호`로 바뀐다
        assertThat(도라에몽_학생.getPassword())
                .isEqualTo(새_비밀번호_해시);
    }

    @DisplayName("요청의 `현재 비밀번호`가 부정확하면 학생의 비밀번호 변경에 실패한다")
    @Test
    void 비밀번호_변경_실패() {
        // Given
        String 현재_비밀번호 = "pw";
        String 새_비밀번호 = "newpw";
        String 현재_비밀번호_해시 = SecurityUtils.sha256Encrypt(현재_비밀번호);
        String 새_비밀번호_해시 = SecurityUtils.sha256Encrypt(새_비밀번호);

        Student 도라에몽_학생 = new Student(
                "dora@gmail.com",
                "도라에몽",
                현재_비밀번호_해시
        );

        given(studentRepository.findByEmail(any()))
                .willReturn(Optional.of(도라에몽_학생));

        // When & Then
        assertThatThrownBy(() ->
                        studentService.changePassword(
                                도라에몽_학생.getEmail(),
                                new ChangePasswordRequest(현재_비밀번호 + "XX", 새_비밀번호)
                        ))
                .isInstanceOf(IllegalArgumentException.class);
    }
}