package restaurant.dto.request;

public record UserAuthenticateRequest(
        String email,
        String password
) {
}
