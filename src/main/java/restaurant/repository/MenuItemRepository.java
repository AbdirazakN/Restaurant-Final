package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.MenuItemResponse;
import restaurant.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id = :chequeId")
    Optional<MenuItemResponse> findByIdResponse(Long chequeId);
    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m")
    List<MenuItemResponse> findAllResponse();
    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.name ilike concat('%',:keyword,'%')")
    List<MenuItemResponse> globalSearch(String keyword);

    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price")
    List<MenuItemResponse> orderByPrice();
    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m order by m.price desc")
    List<MenuItemResponse> orderByPriceDesc();
    @Query("select new restaurant.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.isVegetarian = :isVegetarian")
    List<MenuItemResponse> isVegetarian(Boolean isVegetarian);
}