package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.RestaurantResponse;
import restaurant.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select new restaurant.dto.response.RestaurantResponse(r.name,r.location,r.restType," +
            "r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponse> findAllResponse();

    Boolean existsByName(String restaurantName);

    Optional<Restaurant> findByName(String restaurantName);
}