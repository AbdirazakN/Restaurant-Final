package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuItemResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuItem")
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService menuItemService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.save(menuItemRequest);
    }

    @DeleteMapping("/delete/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long menuItemId) {
        return menuItemService.delete(menuItemId);
    }

    @PostMapping("/update/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public MenuItemResponse update(@PathVariable Long menuItemId, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.update(menuItemId, menuItemRequest);
    }

    @GetMapping("/get/{menuItemId}")
    @PreAuthorize("permitAll()")
    public MenuItemResponse findById(@PathVariable Long menuItemId) {
        return menuItemService.findById(menuItemId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    public List<MenuItemResponse> findAll() {
        return menuItemService.findAll();
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public List<MenuItemResponse> globalSearch(@RequestParam String keyword) {
        return menuItemService.globalSearch(keyword);
    }

    @GetMapping("/orderBy")
    @PreAuthorize("permitAll()")
    public List<MenuItemResponse> orderByPrice() {
        return menuItemService.orderByPrice();
    }

    @GetMapping("/orderByDesc")
    @PreAuthorize("permitAll()")
    public List<MenuItemResponse> orderByPriceDesc() {
        return menuItemService.orderByPriceDesc();
    }

    @GetMapping("/isVegetarian")
    @PreAuthorize("permitAll()")
    public List<MenuItemResponse> isVegetarian(@RequestParam Boolean isVegetarian) {
        return menuItemService.isVegetarian(isVegetarian);
    }
}
