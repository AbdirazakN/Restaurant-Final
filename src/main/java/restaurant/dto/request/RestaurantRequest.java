package restaurant.dto.request;

import restaurant.entity.enums.RestaurantType;

public record RestaurantRequest(
        String name,
        String location,
        RestaurantType restType,
        double service
) {
}
