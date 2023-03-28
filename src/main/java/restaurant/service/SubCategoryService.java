package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.subCategory.SubcategoryRequest;
import restaurant.dto.subCategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    SubCategoryResponse findById(Long subcategoryId);

    SimpleResponse save(Long categoryId, SubcategoryRequest subCategoryRequest);

    SimpleResponse update(Long subCategoryId, SubcategoryRequest subCategoryRequest);

    SimpleResponse delete(Long subCategoryId);
}
