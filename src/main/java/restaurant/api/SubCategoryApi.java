package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.subCategory.SubcategoryRequest;
import restaurant.dto.subCategory.SubCategoryResponse;
import restaurant.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/{categoryId}/subcategory")
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    public SubCategoryApi(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<SubCategoryResponse> getAll(@PathVariable Long categoryId) {
        return subCategoryService.getAllByCategoryId(categoryId);
    }

    @GetMapping("/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    SubCategoryResponse findById(@PathVariable Long subcategoryId) {
        return subCategoryService.findById(subcategoryId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@PathVariable Long categoryId,
                        @RequestBody SubcategoryRequest subCategoryRequest) {
        return subCategoryService.save(categoryId, subCategoryRequest);
    }

    @PutMapping("/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long categoryId,
                          @PathVariable Long subcategoryId,
                          @RequestBody SubcategoryRequest subCategoryRequest) {
        return subCategoryService.update(subcategoryId, subCategoryRequest);
    }

    @DeleteMapping("/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId,
                          @PathVariable Long subcategoryId) {
        return subCategoryService.delete(subcategoryId);
    }
}