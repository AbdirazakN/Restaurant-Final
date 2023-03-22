package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.save(restaurantRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long categoryId) {
        return restaurantService.delete(categoryId);
    }

    @PostMapping("/update/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantResponse update(@PathVariable Long restaurantId, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.update(restaurantId, restaurantRequest);
    }

    @GetMapping("/get/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestaurantResponse findById(@PathVariable Long restaurantId) {
        return restaurantService.findById(restaurantId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RestaurantResponse> findAll() {
        return restaurantService.findAll();
    }
}
