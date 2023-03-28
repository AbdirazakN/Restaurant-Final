package restaurant.dto.menuItem;

import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record MenuItemRequest(
        String name,
        String image,
        @Min(value = 1,message = "PRICE CAN'T BE NEGATIVE!")
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
