package restaurant.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.SimpleResponse;
import restaurant.dto.subCategory.SubcategoryRequest;
import restaurant.dto.subCategory.SubCategoryResponse;
import restaurant.entities.Subcategory;
import restaurant.exception.ExistsException;
import restaurant.exception.NotFoundException;
import restaurant.repository.CategoryRepository;
import restaurant.repository.SubCategoryRepository;
import restaurant.service.SubCategoryService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SubCategoryImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryImpl(SubCategoryRepository subCategoryRepository,
                           CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SubCategoryResponse> getAllByCategoryId(Long categoryId) {
        return subCategoryRepository.getAllByCategoryId(categoryId);
    }

    @Override
    public SubCategoryResponse findById(Long subcategoryId) {
        if (!subCategoryRepository.existsById(subcategoryId)) {
            log.error("Sub category with id - " + subcategoryId + " doesn't exists!");
            throw new ExistsException("Sub category with id - " + subcategoryId + " doesn't exists!");
        }
        return subCategoryRepository.findSubcategoryResponseById(subcategoryId)
                .orElseThrow(()-> new ExistsException("Sub category with id - " + subcategoryId + " doesn't exists!"));
    }

    @Override
    public SimpleResponse save(Long categoryId, SubcategoryRequest subCategoryRequest) {
        if (subCategoryRepository.existsByName(subCategoryRequest.name())){
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description(String.format("Subcategory with name: %s already exists!", subCategoryRequest.name()))
                    .build();
        }
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(categoryRepository.findById(categoryId).orElseThrow(()-> {
            log.error("Category with id - " + categoryId + " is not found!");
            throw new NotFoundException("Category with id - " + categoryId + " is not found!");
        }));
        subcategory.setName(subCategoryRequest.name());
        subCategoryRepository.save(subcategory);
        log.info("Sub category - " + subcategory.getName() + " is saved!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Subcategory - " + subcategory.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long subCategoryId, SubcategoryRequest subCategoryRequest) {
        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> {
            log.error("Sub category with id - " + subCategoryId + " is not found!");
            throw new NotFoundException("Sub category with id - " + subCategoryId + " is not found!");
        });
        if ((!subcategory.getName().equals(subCategoryRequest.name()))
        && subCategoryRepository.findAll().stream().anyMatch(s->s.getName().equals(subCategoryRequest.name()))){
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("Sub category with Name - " + subcategory.getName() + " is already exist!")
                    .build();
        }
        subcategory.setName(subCategoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Sub category - " + subcategory.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            log.error("Sub category with id - " + subCategoryId + " doesn't exists!");
            throw new ExistsException("Sub category with id - " + subCategoryId + " doesn't exists!");
        }
        subCategoryRepository.deleteById(subCategoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Sub category with id - " + subCategoryId + " is deleted!")
                .build();
    }
}
