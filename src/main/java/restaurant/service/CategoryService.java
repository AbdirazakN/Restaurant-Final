package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.category.CategoryRequest;
import restaurant.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse findById(Long id);

    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse update(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);

}
