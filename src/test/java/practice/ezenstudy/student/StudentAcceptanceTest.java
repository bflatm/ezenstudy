package practice.ezenstudy.student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import practice.ezenstudy.student.application.ChangePasswordRequest;
import practice.ezenstudy.student.application.LoginRequest;
import practice.ezenstudy.student.application.RegisterStudentRequest;

@Sql("/truncate.sql")
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

    @Test
    void 프로필_조회_성공() {
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

        String accessToken = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(이메일, 비밀번호))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("accessToken");

        // when & then
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get("/me")
                .then().log().all()
                .statusCode(200);
    }

    /*
    * 1. 회원 가입
    * 2. 로그인
    * 3. 비밀번호 변경
    * 4. 변경 전 비밀번호로 로그인 - 실패
    * 5. 변경 후 비밀번호로 로그인 - 성공
    * */
    @Test
    void 비밀번호_변경_성공() {
        // given
        String 이메일 = "doraemon@gmail.com";
        String 기존_비밀번호 = "dora123";
        String 새_비밀번호 = 기존_비밀번호 + "xxx";
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new RegisterStudentRequest(이메일, "도라에몽", 기존_비밀번호))
                .when()
                .post("/students")
                .then().log().all()
                .statusCode(200);

        String accessToken = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(이메일, 기존_비밀번호))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("accessToken");

        // when
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(new ChangePasswordRequest(기존_비밀번호, 새_비밀번호))
                .when()
                .patch("/students")
                .then().log().all()
                .statusCode(200);

        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(이메일, 기존_비밀번호))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(500);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(이메일, 새_비밀번호))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200);
    }
}
