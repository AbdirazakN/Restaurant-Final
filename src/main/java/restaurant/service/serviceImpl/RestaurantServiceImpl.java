package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.RestaurantRequest;
import restaurant.dto.response.RestaurantResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Restaurant;
import restaurant.entity.enums.RestaurantType;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(validationName(restaurantRequest.name()));
        restaurant.setService(validationService(restaurantRequest.service()));
        restaurant.setLocation(validationLocation(restaurantRequest.location()));
        restaurant.setRestType(validationResType(restaurantRequest.restType()));

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", restaurantRequest.name())).build();
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Deleted...", restaurantId)).build();
    }

    @Override
    public RestaurantResponse update(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        restaurant.setName(validationName(restaurantRequest.name()));
        restaurant.setService(validationService(restaurantRequest.service()));
        restaurant.setLocation(validationLocation(restaurantRequest.location()));
        restaurant.setRestType(validationResType(restaurantRequest.restType()));

        restaurantRepository.save(restaurant);

        return new RestaurantResponse(restaurantRequest.name(),
                restaurantRequest.location(),
                restaurantRequest.restType(),
                restaurant.getNumberOfEmployees(),
                restaurantRequest.service());
    }

    @Override
    public RestaurantResponse findById(Long id) {
        try {
            if (!restaurantRepository.existsById(id)) {
                return new RestaurantResponse("Not found Restaurant with ID: " + id, null, null, 0, 0);
            }
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format
                    ("User with ID: %s doesn't exists", id)));
            return new RestaurantResponse(restaurant.getName(),
                    restaurant.getLocation(),
                    restaurant.getRestType(),
                    userRepository.getQualityOfEmployees(restaurant.getId()),
                    restaurant.getService());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAllResponse();
    }

    public String validationName(String name) {
        if (name.trim().length() < 3 || name.length() > 25) {
            throw new IllegalArgumentException("Invalid Restaurant name...");
        }
        return name;
    }

    public String validationLocation(String location) {
        if (location.trim().length() < 3 || location.length() > 25) {
            throw new IllegalArgumentException("Invalid Restaurant location...");
        }
        return location;
    }

    public RestaurantType validationResType(RestaurantType resType) {
        if (!(resType.name().equals("RESTAURANT")
                || resType.name().equals("FAST_FOOD")
                || resType.name().equals("CAFE")
                || resType.name().equals("BUFFET"))) {
            throw new IllegalArgumentException("Invalid Restaurant resType...");
        }
        return resType;
    }
    public Double validationService(Double service) {
        if (service < 0
                || service > 100
                || service == null) {
            throw new IllegalArgumentException("Invalid Restaurant service...");
        }
        return service;
    }

    public int validationNumberOfEmployees(int numberOfEmployees) {
        if (numberOfEmployees < 0
                || numberOfEmployees > 15
                || numberOfEmployees == 0) {
            if (numberOfEmployees < 0){
                return numberOfEmployees=0;
            }
            throw new IllegalArgumentException("Invalid Restaurant numberOfEmployees...");
        }
        return numberOfEmployees;
    }
}
