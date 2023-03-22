package restaurant.dto.response;

import lombok.Builder;
import restaurant.entity.Restaurant;
import restaurant.entity.enums.Role;

import java.time.LocalDate;
@Builder
public record UserResponse(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        Role role,
        int experience,
        String restaurantName
) {
}
