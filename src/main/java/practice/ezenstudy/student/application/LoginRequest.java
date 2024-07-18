package practice.ezenstudy.student.application;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        String email,
        @NotNull String password
) {
}
