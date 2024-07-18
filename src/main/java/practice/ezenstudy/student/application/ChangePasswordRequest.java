package practice.ezenstudy.student.application;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
