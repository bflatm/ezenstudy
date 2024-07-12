package practice.ezenstudy.student;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}
