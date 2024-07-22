package practice.ezenstudy.student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import practice.ezenstudy.student.application.LoginRequest;
import practice.ezenstudy.student.application.RegisterStudentRequest;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentAcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 회원가입_성공() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new RegisterStudentRequest("doraemon@gmail.com", "도라에몽", "dora123"))
                .when()
                .post("/students")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 로그인_성공() {
        // given
        String 이메일 = "doraemon@gmail.com";
        String 비밀번호 = "dora123";
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new RegisterStudentRequest(이메일, "도라에몽", 비밀번호))
                .when()
                .post("/students")
                .then().log().all()
                .statusCode(200);

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(이메일, 비밀번호))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200);
    }
}
