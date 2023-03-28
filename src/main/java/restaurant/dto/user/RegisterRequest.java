package restaurant.dto.user;

import jakarta.validation.constraints.*;
import restaurant.entities.enums.Role;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "FIRSTNAME CAN'T BE EMPTY!")
        String firstName,
        @NotBlank(message = "LASTNAME CAN'T BE EMPTY!")
        String lastName,
        @NotBlank(message = "DATE OF BIRTH CAN'T BE EMPTY!")
        LocalDate dateOfBirth,
        @NotBlank(message = "E-MAIL CAN'T BE EMPTY!")
        @Email(message = "INCORRECT E-MAIL!")
        String email,
        @Size(min = 5, max = 100, message = "PASSWORD MUST BE AT LEAST 4 CHARACTERS!")
        @NotBlank(message = "PASSWORD CAN'T BE EMPTY!")
        String password,
        @NotBlank(message = "PHONE NUMBER CAN'T BE EMPTY!")
        @Pattern(regexp = "\\+996\\d{9}", message = "INVALID PHONE NUMBER!")
        String phoneNumber,
        @NotNull(message = "ROLE CAN'T BE EMPTY!")
        Role role,
        @Positive(message = "EXPERIENCE CAN'T BE EMPTY!")
        int experience
) {
}
