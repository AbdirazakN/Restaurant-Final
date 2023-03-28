package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.dto.restaurant.RestaurantResponse;
import restaurant.entities.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select new restaurant.dto.restaurant.RestaurantResponse(r.id,r.name,r.location,r.restType," +
            "r.numberOfEmployees,r.service) from Restaurant r where r.id = :id and r.name is not null")
    RestaurantResponse getRestaurantById(Long id);

    @Query("select new restaurant.dto.restaurant.RestaurantResponse(r.id,r.name,r.location,r.restType," +
            "r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponse> findAllResponse();
}