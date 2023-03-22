package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryAPI {
    private final CategoryService categoryService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }

    @PostMapping("/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse update(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(categoryId, categoryRequest);
    }

    @GetMapping("/get/{categoryId}")
    @PreAuthorize("permitAll()")
    public CategoryResponse findById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/search")
    @PreAuthorize("permitAll()")
    public List<CategoryResponse> globalSearch(@RequestParam String keyword){
        return categoryService.globalSearch(keyword);
    }
}
