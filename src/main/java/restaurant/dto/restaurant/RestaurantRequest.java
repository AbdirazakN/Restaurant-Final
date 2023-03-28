package restaurant.dto.restaurant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import restaurant.entities.enums.RestaurantType;

public record RestaurantRequest(
        @NotBlank(message = "RESTAURANT NAME CAN'T BE EMPTY!")
        String name,
        @NotBlank(message = "RESTAURANT LOCATION CAN'T BE EMPTY!")
        String location,
        @NotBlank(message = "RESTAURANT TYPE CAN'T BE EMPTY!")
        RestaurantType restType,
        @NotBlank(message = "RESTAURANT NAME CAN'T BE EMPTY!")
        @Min(value = 1,message = "SERVICE CAN'T BE NEGATIVE!")
        int service
) {
}
