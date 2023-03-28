package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.category.CategoryRequest;
import restaurant.dto.category.CategoryResponse;
import restaurant.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    CategoryResponse findById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long categoryId,
                          @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(categoryId, categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }
}
