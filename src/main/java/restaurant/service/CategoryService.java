package restaurant.service;

import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    List<CategoryResponse> globalSearch(String keyword);
}
