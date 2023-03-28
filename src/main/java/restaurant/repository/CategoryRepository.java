package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.dto.category.CategoryResponse;
import restaurant.entities.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new restaurant.dto.category.CategoryResponse(c.id, c.name) from Category c")
    List<CategoryResponse> getAll();

    @Query("select new restaurant.dto.category.CategoryResponse(c.id, c.name) from Category c where c.id = :id")
    Optional<CategoryResponse> findByIdResponse(Long id);
}