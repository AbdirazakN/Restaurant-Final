package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.dto.subCategory.SubCategoryResponse;
import restaurant.dto.subCategory.SubcategoryResponsesByCategory;
import restaurant.entities.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select new restaurant.dto.subCategory.SubCategoryResponse(s.id, s.name)" +
            " from Subcategory s where s.category.id = ?1")
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);
    Boolean existsByName(String SubcategoryName);

    @Query("select new restaurant.dto.subCategory.SubCategoryResponse(s.id,s.name) from Subcategory s where s.id=:id")
    Optional<SubCategoryResponse> findSubcategoryResponseById(Long id);

}