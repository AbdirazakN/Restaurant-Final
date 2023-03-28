package restaurant.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.SimpleResponse;
import restaurant.dto.restaurant.RestaurantRequest;
import restaurant.dto.restaurant.RestaurantResponse;
import restaurant.entities.Restaurant;
import restaurant.exception.NotFoundException;
import restaurant.repository.RestaurantRepository;
import restaurant.service.RestaurantService;

import java.util.List;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantResponse getRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)){
            throw new NotFoundException("Restaurant with id - "
                    + restaurantId + " is not found!");
        }
        return restaurantRepository.getRestaurantById(restaurantId);
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        if (!restaurantRepository.findAll().isEmpty()) {
            return restaurantRepository.findAllResponse();
        }
        return null;
    }

    @Override
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        if (restaurantRepository.findAll().isEmpty()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(restaurantRequest.name());
            restaurant.setLocation(restaurantRequest.location());
            restaurant.setRestType(restaurantRequest.restType().name());
            restaurant.setService(restaurantRequest.service());
            restaurantRepository.save(restaurant);
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .description("Restaurant - " + restaurant.getName() + " is saved!")
                    .build();
        }else {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("Sorry you can save only One Restaurant!")
                    .build();
        }
    }

    @Override
    public SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest) {
        if (!restaurantRepository.existsById(restaurantId)){
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description("Restaurant with ID -  " + restaurantId + " is not found!")
                    .build();
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id - "
                        + restaurantId + " is not found!"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType().name());
        restaurant.setService(restaurantRequest.service());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Restaurant - " + restaurant.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description("Restaurant with id - " + restaurantId + " is not found!")
                    .build();
        }
        restaurantRepository.deleteById(restaurantId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Restaurant with id - " + restaurantId + " is deleted!")
                .build();
    }

    @Override
    public String count() {
        return null;
    }
}
