package restaurant.dto.request;

import restaurant.entity.Restaurant;
import restaurant.entity.enums.Role;

import java.time.LocalDate;

public record UserRequest(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        int experience,
        String restaurantName
) {
}
