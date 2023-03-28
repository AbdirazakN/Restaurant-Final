package restaurant.dto.menuItem;

public record MenuItemListResponse(
        Long id,
        String categoryName,
        String subcategoryName,
        String name,
        Integer price
) {
}
