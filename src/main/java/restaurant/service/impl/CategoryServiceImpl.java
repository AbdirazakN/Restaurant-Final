package restaurant.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.SimpleResponse;
import restaurant.dto.category.CategoryRequest;
import restaurant.dto.category.CategoryResponse;
import restaurant.entities.Category;
import restaurant.exception.ExistsException;
import restaurant.exception.NotFoundException;
import restaurant.repository.CategoryRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.service.CategoryService;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public CategoryResponse findById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ExistsException("Category with id - " + id + " doesn't exists!");
        }
        return categoryRepository.findByIdResponse(id).orElseThrow(() ->
                new NotFoundException("Category with id " + id + " is not found!"));
    }

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        if (categoryRepository.findAll().stream().anyMatch(s -> s.getName().equals(categoryRequest.name()))) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description(String.format("Category with name: %s already exists!", categoryRequest.name()))
                    .build();
        }
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " is not found!"));
        if (!category.getName().equals(categoryRequest.name())) {
            if (categoryRepository.findAll().stream().anyMatch(s -> s.getName().equals(categoryRequest.name()))) {
                return SimpleResponse.builder()
                        .status(HttpStatus.CONFLICT)
                        .description(String.format("Category with name: %s already exists!", categoryRequest.name()))
                        .build();
            }
        }
        category.setName(categoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ExistsException("Category with id - " + categoryId + " doesn't exists!");
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category with id - " + categoryId + " is deleted")
                .build();
    }

}
