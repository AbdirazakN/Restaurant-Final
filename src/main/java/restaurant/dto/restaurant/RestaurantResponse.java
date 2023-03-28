package restaurant.dto.restaurant;

import restaurant.entities.enums.RestaurantType;

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        int service
) {
}
