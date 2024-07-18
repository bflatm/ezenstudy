package practice.ezenstudy.lecture.application;

import jakarta.validation.constraints.NotNull;

public record UpdatePublicStatusRequest(
        @NotNull Boolean isPublic
) {
}
