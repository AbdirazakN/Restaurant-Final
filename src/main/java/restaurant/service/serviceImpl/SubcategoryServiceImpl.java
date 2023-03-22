package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.SubcategoryRequest;
import restaurant.dto.response.SubcategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Category;
import restaurant.entity.Subcategory;
import restaurant.repository.CategoryRepository;
import restaurant.repository.SubcategoryRepository;
import restaurant.service.SubcategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse save(SubcategoryRequest subcategoryRequest) {
        Category category = new Category();
        if (categoryRepository.existsByName(subcategoryRequest.category().getName())){
            category = categoryRepository.findByName(subcategoryRequest.category().getName()).get();
        }
        Subcategory subcategory = new Subcategory();

        subcategory.setName(validationName(subcategoryRequest.name()));
        category.setSubcategory(List.of(subcategory));
        category.setName(subcategoryRequest.category().getName());
        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", subcategoryRequest.name())).build();
    }

    @Override
    public SimpleResponse delete(Long subcategoryId) {
        subcategoryRepository.deleteById(subcategoryId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", subcategoryId)).build();
    }

    @Override
    public SubcategoryResponse update(Long id, SubcategoryRequest subcategoryRequest) {
        Category category = new Category();
        if (categoryRepository.existsByName(subcategoryRequest.category().getName())){
            category = categoryRepository.findByName(subcategoryRequest.category().getName()).get();
        }else {
            category.setName(subcategoryRequest.category().getName());
        }
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow();
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(category);
        subcategoryRepository.save(subcategory);
        return new SubcategoryResponse(subcategoryRequest.name());
    }

    @Override
    public SubcategoryResponse findById(Long id) {
        return subcategoryRepository.findByIdResponse(id).orElseThrow();
    }

    @Override
    public List<SubcategoryResponse> findAll() {
        return subcategoryRepository.findAllResponse();
    }

    @Override
    public List<SubcategoryResponse> findAllByCategory(String category) {
        return subcategoryRepository.findAllByCategory(category);
    }

    @Override
    public List<SubcategoryResponse> findAllByCategoryGroup() {
        return subcategoryRepository.findAllByCategoryGroup();
    }

    @Override
    public List<SubcategoryResponse> globalSearch(String keyword) {
        return subcategoryRepository.globalSearch(keyword);
    }


    public String validationName(String subcategoryName) {
        if (subcategoryName.trim().length() < 3 || subcategoryName.length() > 30 || subcategoryName == null) {
            throw new IllegalArgumentException("Invalid Subcategory Name...");
        }
        return subcategoryName;
    }

}
