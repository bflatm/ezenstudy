package practice.ezenstudy.teacher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void createdDateTime테스트() {
        Teacher 김선생 = new Teacher("김선생", LocalDate.parse("1988-08-08"));
        assertThat(김선생.getCreatedDateTime()).isNull();

        Teacher 저장된_김선생 = teacherRepository.save(김선생);
        assertThat(저장된_김선생.getCreatedDateTime()).isNotNull();
    }
}