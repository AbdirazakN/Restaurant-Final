package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.menuItem.MenuItemRequest;
import restaurant.dto.menuItem.MenuItemResponse;
import restaurant.dto.menuItem.MenuItemSearchResponse;
import restaurant.dto.menuItem.PaginationResponse;
import restaurant.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/{subCategoryId}/menuItem")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> getAll(@PathVariable Long subCategoryId,
                                  @RequestParam(required = false) String keyWord){
        return menuItemService.getAll(subCategoryId, keyWord);
    }
    @GetMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    MenuItemResponse getById(@PathVariable Long menuItemId){
        return menuItemService.getById(menuItemId);
    }

    @PostMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@PathVariable Long restaurantId,
                        @PathVariable Long subCategoryId,
                        @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.save(restaurantId, subCategoryId, menuItemRequest);
    }
    @PutMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long subCategoryId,
                          @PathVariable Long menuItemId,
                          @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.update(menuItemId, menuItemRequest);
    }
    @DeleteMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long subCategoryId,
                          @PathVariable Long menuItemId){
        return menuItemService.delete(menuItemId);
    }
    @GetMapping("/sort")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> sort(@PathVariable Long subCategoryId,
                                @RequestParam(required = false) String ascOrDesc){
        return menuItemService.sort(ascOrDesc);
    }
    @GetMapping("/vegetarian")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<MenuItemResponse> isVegetarian(@PathVariable Long subCategoryId,
                                        @RequestParam boolean isTrue){
        return menuItemService.isVegetarian(isTrue);
    }
    @GetMapping("/pagination")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    public PaginationResponse getMenuItemPagination(@RequestParam int page, @RequestParam  int size){
        return menuItemService.getMenuItemPagination(page, size);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public List<MenuItemSearchResponse> findAll(@RequestParam(required = false) String global,
                                                @RequestParam(required = false,defaultValue = "asc") String sort,
                                                @RequestParam(required = false) Boolean isVegan) {

        return menuItemService.findAll(global, sort, isVegan);
    }
}
