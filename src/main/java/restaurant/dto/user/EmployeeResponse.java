package restaurant.dto.user;

import restaurant.entities.enums.Role;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        int experience
) {
}
