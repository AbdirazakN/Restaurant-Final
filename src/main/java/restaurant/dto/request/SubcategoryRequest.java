package restaurant.dto.request;

import restaurant.entity.Category;
import restaurant.entity.MenuItem;

import java.util.List;

public record SubcategoryRequest(
        String name,
        Category category
) {
}
