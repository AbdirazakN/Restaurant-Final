package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.restaurant.RestaurantRequest;
import restaurant.dto.restaurant.RestaurantResponse;
import restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantApi {
    private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    RestaurantResponse getById(@PathVariable Long restaurantId){
        return restaurantService.getRestaurant(restaurantId);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse save(@RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.save(restaurantRequest);
    }
    @PutMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse update(@PathVariable Long restaurantId,
                          @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.update(restaurantId, restaurantRequest);
    }
    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse delete(@PathVariable Long restaurantId){
        return restaurantService.delete(restaurantId);
    }

}
