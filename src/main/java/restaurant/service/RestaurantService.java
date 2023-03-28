package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.restaurant.RestaurantRequest;
import restaurant.dto.restaurant.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    RestaurantResponse getRestaurant(Long restaurantId);
    List<RestaurantResponse> getAllRestaurants();

    SimpleResponse save(RestaurantRequest restaurantRequest);

    SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long restaurantId);

    String count();
}
