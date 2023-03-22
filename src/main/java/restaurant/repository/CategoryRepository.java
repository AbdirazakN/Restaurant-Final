package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.MenuItemResponse;
import restaurant.dto.response.SubcategoryResponse;
import restaurant.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new restaurant.dto.response.CategoryResponse(c.name) from Category c where c.id = :CategoryId")
    Optional<CategoryResponse> findByIdResponse(Long CategoryId);

    @Query("select new restaurant.dto.response.CategoryResponse(c.name) from Category c")
    List<CategoryResponse> findAllResponse();

    @Query("select new restaurant.dto.response.CategoryResponse(c.name) from Category c where c.name ilike concat('%',:keyword,'%')")
    List<CategoryResponse> globalSearch(String keyword);

    Optional<Category> findByName(String categoryName);

    Boolean existsByName(String categoryName);
}