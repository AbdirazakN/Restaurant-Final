package restaurant.service;

import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuItemResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse save(MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long menuItemId);

    MenuItemResponse update(Long id, MenuItemRequest menuItemRequest);

    MenuItemResponse findById(Long id);

    List<MenuItemResponse> findAll();

    List<MenuItemResponse> globalSearch(String keyword);

    List<MenuItemResponse> orderByPrice();

    List<MenuItemResponse> orderByPriceDesc();

    List<MenuItemResponse> isVegetarian(Boolean isVegetarian);
}
