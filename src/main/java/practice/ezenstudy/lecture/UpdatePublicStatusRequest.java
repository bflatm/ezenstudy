package practice.ezenstudy.lecture;

import jakarta.validation.constraints.NotNull;

public record UpdatePublicStatusRequest(
        @NotNull Boolean isPublic
) {
}
