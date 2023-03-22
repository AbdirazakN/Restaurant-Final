package restaurant.service;

import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse save(RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long restaurantId);

    RestaurantResponse update(Long id, RestaurantRequest restaurantRequest);

    RestaurantResponse findById(Long id);

    List<RestaurantResponse> findAll();
}
