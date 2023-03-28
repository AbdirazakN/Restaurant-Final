package restaurant.service;

import org.springframework.data.repository.query.Param;
import restaurant.dto.SimpleResponse;
import restaurant.dto.menuItem.MenuItemRequest;
import restaurant.dto.menuItem.MenuItemResponse;
import restaurant.dto.menuItem.MenuItemSearchResponse;
import restaurant.dto.menuItem.PaginationResponse;
import restaurant.entities.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getAll(Long subCategoryId, String keyWord);
    MenuItemResponse getById(Long menuItemId);

    SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);

    SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long menuItemId);

    List<MenuItemResponse> sort(String ascOrDesc);

    List<MenuItemResponse> isVegetarian(boolean isTrue);

    PaginationResponse getMenuItemPagination(int page, int size);

    List<MenuItemSearchResponse> findAll(String global, String sort, Boolean isVegan);
}
