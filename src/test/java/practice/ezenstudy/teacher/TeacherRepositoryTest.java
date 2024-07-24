package practice.ezenstudy.teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository teacherRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void createdDateTime테스트() {
        Teacher 김선생 = new Teacher("김선생", LocalDate.parse("1988-08-08"));
        assertThat(김선생.getCreatedDateTime()).isNull();

        Teacher 저장된_김선생 = teacherRepository.save(김선생);
        assertThat(저장된_김선생.getCreatedDateTime()).isNotNull();
    }

    @Test
    void modifiedDateTime테스트() {
        // given: 저장되어 있는 강사가 있고
        Teacher 저장된_김선생 = teacherRepository.save(new Teacher("김선생", LocalDate.parse("1988-08-08")));
        LocalDateTime 기존_마지막_수정일시 = 저장된_김선생.getModifiedDateTime();

        // when: 그 강사 오브젝트의 데이터를 변경할 때
        저장된_김선생.updateNameAndBirthday(저장된_김선생.getName(), LocalDate.parse("1999-09-09"));
        em.flush(); // update 쿼리가 실행되게 함
        em.clear(); // 캐시에서 삭제

        // then: 데이터베이스의 마지막 수정일시가 자동으로 바뀌어서 기존 마지막 수정일시와 다르다
        Teacher 찾은_김선생 = teacherRepository.findById(저장된_김선생.getId())
                .orElse(null);
        assertThat(찾은_김선생.getModifiedDateTime()).isNotEqualTo(기존_마지막_수정일시);
        assertThat(기존_마지막_수정일시.isBefore(찾은_김선생.getModifiedDateTime())).isTrue();
    }
}