package restaurant.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotBlank(message = "E-mail is empty!")
        @Email(message = "Invalid E-mail!")
        String login,
        @Size(min = 5, max = 100, message = "Password must be at least 4 characters!")
        @NotBlank(message = "Password is empty!")
        String password
) {
}
