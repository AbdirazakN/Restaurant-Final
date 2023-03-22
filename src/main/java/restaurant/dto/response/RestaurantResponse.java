package restaurant.dto.response;

import jakarta.persistence.Column;
import restaurant.entity.enums.RestaurantType;

public record RestaurantResponse(
        String name,
        String location,
        RestaurantType restType,
        int numberOfEmployees,
        double service
) {
}
