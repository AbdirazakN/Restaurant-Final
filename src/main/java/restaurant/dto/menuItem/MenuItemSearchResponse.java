package restaurant.dto.menuItem;

import java.math.BigDecimal;

public record MenuItemSearchResponse(
        Long id,
        String categoryName,
        String subCategoryName,
        String name,
        BigDecimal price
) {
}
