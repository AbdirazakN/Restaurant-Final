package restaurant.dto.request;

import restaurant.entity.Cheque;
import restaurant.entity.Restaurant;
import restaurant.entity.StopList;
import restaurant.entity.Subcategory;

import java.util.List;

public record MenuItemRequest(
        String name,
        String image,
        Double price,
        String description,
        Boolean isVegetarian,
        Subcategory subcategory
) {
}
