package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.StopListResponse;
import restaurant.dto.response.SubcategoryResponse;
import restaurant.entity.Category;
import restaurant.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select new restaurant.dto.response.SubcategoryResponse(s.name) from Subcategory s where s.id = :subcategoryId")
    Optional<SubcategoryResponse> findByIdResponse(Long subcategoryId);

    @Query("select new restaurant.dto.response.SubcategoryResponse(s.name) from Subcategory s")
    List<SubcategoryResponse> findAllResponse();

    @Query("select s from Subcategory s where s.category.name = :category order by s.name")
    List<SubcategoryResponse> findAllByCategory(String category);

    @Query("select new restaurant.dto.response.SubcategoryResponse(s.name) from Subcategory s order by s.category.name")
    List<SubcategoryResponse> findAllByCategoryGroup();

    @Query("select new restaurant.dto.response.SubcategoryResponse(s.name) from Subcategory s where s.name ilike concat('%',:keyword,'%')")
    List<SubcategoryResponse> globalSearch(String keyword);

    Boolean existsByName(String subcategoryName);

    Optional<Subcategory> findByName(String subcategoryName);
}