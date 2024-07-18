package practice.ezenstudy.student.application;

public record RegisterStudentRequest(
        String email,
        String nickname,
        String password
) {
}
