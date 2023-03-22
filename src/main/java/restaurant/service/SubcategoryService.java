package restaurant.service;

import org.springframework.data.jpa.repository.Query;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.request.SubcategoryRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.SubcategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;

import java.util.List;

public interface SubcategoryService {
    SimpleResponse save(SubcategoryRequest subcategoryRequest);

    SimpleResponse delete(Long subcategoryId);

    SubcategoryResponse update(Long id, SubcategoryRequest subcategoryRequest);

    SubcategoryResponse findById(Long id);

    List<SubcategoryResponse> findAll();
    List<SubcategoryResponse> findAllByCategory(String category);
    List<SubcategoryResponse> findAllByCategoryGroup();
    List<SubcategoryResponse> globalSearch(String keyword);
}
