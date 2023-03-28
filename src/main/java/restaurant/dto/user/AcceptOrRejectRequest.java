package restaurant.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AcceptOrRejectRequest(
        @NotBlank(message = "USER ID IS EMPTY!")
        Long userId,
        @NotBlank(message = "ACCEPT MOST BE ONLY TRUE OR FALSE!")
        boolean accept
) {
}
