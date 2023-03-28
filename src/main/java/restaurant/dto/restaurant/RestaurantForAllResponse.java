package restaurant.dto.restaurant;

import restaurant.entities.enums.RestaurantType;

import java.math.BigDecimal;

public record RestaurantForAllResponse(
        String name,
        String location,
        RestaurantType restType,
        Integer service,
        BigDecimal averageCheck
) {
}
