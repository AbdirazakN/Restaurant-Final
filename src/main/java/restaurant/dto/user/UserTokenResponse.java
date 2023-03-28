package restaurant.dto.user;

import lombok.Builder;

@Builder
public record UserTokenResponse(
        String login,
        String token
) {
}
