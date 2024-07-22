package practice.ezenstudy.student.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import practice.ezenstudy.SecurityUtils;

class StudentTest {

    @Test
    void 동일_패스워드_비교() {
        // given
        String 비밀번호 = "abcd";
        String 비밀번호_해시 = SecurityUtils.sha256Encrypt(비밀번호);
        Student student = new Student("", "", 비밀번호_해시);

        // when
        boolean 결과 = student.authenticate(비밀번호);

        // then
        Assertions.assertThat(결과).isTrue();
    }

    @Test
    void 다른_패스워드_비교() {
        // given
        String 비밀번호 = "abcd";
        String 비밀번호_해시 = SecurityUtils.sha256Encrypt(비밀번호);
        Student student = new Student("", "", 비밀번호_해시);

        // when
        boolean 결과 = student.authenticate(비밀번호 + "x");

        // then
        Assertions.assertThat(결과).isFalse();
    }
}