package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Category;
import restaurant.repository.CategoryRepository;
import restaurant.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category1 = new Category();
        category1.setName(validationName(categoryRequest.name()));
        categoryRepository.save(category1);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...",categoryRequest.name())).build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Deleted...",categoryId)).build();
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(validationName(categoryRequest.name()));
        categoryRepository.save(category);
        return new CategoryResponse(categoryRequest.name());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.findByIdResponse(id).get();
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllResponse();
    }
    @Override
    public List<CategoryResponse> globalSearch(String keyword){
        return categoryRepository.globalSearch(keyword);
    }

    public String validationName(String name) {
        if (name.trim().length() < 3 || name.length() > 25) {
            throw new IllegalArgumentException("Invalid Category name...");
        }
        return name;
    }
}
