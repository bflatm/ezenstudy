package practice.ezenstudy.student.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice.ezenstudy.lecture.domain.Lecture;
import practice.ezenstudy.lecture.domain.LectureCategory;
import practice.ezenstudy.teacher.Teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentTest {

    static Student 학생A;
    static Student 학생B;
    static Lecture 강의A;

    @BeforeAll
    static void beforeAll() {
        학생A = new Student(
                100L,
                "",
                "",
                ""
        );
        학생B = new Student(
                88L,
                "",
                "",
                ""
        );
        강의A = new Lecture(
                "",
                "",
                new Teacher("", LocalDate.now()),
                100,
                LectureCategory.과학
        );
    }

    @DisplayName("수강 신청 내역이 있을 때 수강 신청 학생을 전달하면 true를 return한다")
    @Test
    void 수강신청자_확인() {
        // given
        Enrollment 수강신청내역 = new Enrollment(
                학생A,
                강의A
        );

        // when
        boolean 확인결과 = 수강신청내역.isEnrolledByStudent(학생A);

        // then
        assertThat(확인결과).isTrue();
    }

    @DisplayName("수강 신청 내역이 있을 때 신청자가 아닌 학생을 전달하면 false를 return한다")
    @Test
    void 수강신청자_확인2() {
        // given
        Enrollment 수강신청내역 = new Enrollment(
                학생A,
                강의A
        );

        // when
        boolean 확인결과 = 수강신청내역.isEnrolledByStudent(학생B);

        // then
        assertThat(확인결과).isFalse();
    }

    @DisplayName("신청한 지 일주일이 지나지 않은 수강 신청은 취소할 수 있다")
    @Test
    void 수강취소_가능() {
        // given
        Enrollment 수강신청내역 = new Enrollment(
                학생A,
                강의A,
                LocalDateTime.parse("2024-07-18T14:00:00")
        );

        // when
        boolean 취소가능여부 = 수강신청내역.isWithinCancellationPeriod(
                LocalDateTime.parse("2024-07-19T18:00:00"));

        // then
        assertThat(취소가능여부).isTrue();
    }

    @DisplayName("신청한 지 일주일이 지난 수강 신청은 취소할 수 없다")
    @Test
    void 수강취소_불가능() {
        // given
        LocalDateTime 신청일시 = LocalDateTime.parse("2024-07-10T14:00:00");
        LocalDateTime 취소요청일시 = LocalDateTime.parse("2024-07-18T00:00:00"); // 만 7일째 되는 날까지 지나갔기 때문에 취소 불가
        Enrollment 수강신청내역 = new Enrollment(
                학생A,
                강의A,
                신청일시
        );

        // when & then
        assertThat(수강신청내역.isWithinCancellationPeriod(취소요청일시)).isFalse();
    }
}