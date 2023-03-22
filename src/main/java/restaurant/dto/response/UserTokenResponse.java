package restaurant.dto.response;

import lombok.Builder;

@Builder
public record UserTokenResponse(
        String email,
        String token
) {
}
