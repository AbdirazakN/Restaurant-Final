package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.SubcategoryRequest;
import restaurant.dto.response.SubcategoryResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
@RequiredArgsConstructor
public class SubcategoryAPI {
    private final SubcategoryService subcategoryService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.save(subcategoryRequest);
    }

    @DeleteMapping("/delete/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long subcategoryId) {
        return subcategoryService.delete(subcategoryId);
    }

    @PostMapping("/update/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SubcategoryResponse update(@PathVariable Long menuItemId, @RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.update(menuItemId, subcategoryRequest);
    }

    @GetMapping("/get/{subcategoryId}")
    @PreAuthorize("permitAll()")
    public SubcategoryResponse findById(@PathVariable Long subcategoryId) {
        return subcategoryService.findById(subcategoryId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    public List<SubcategoryResponse> findAll() {
        return subcategoryService.findAll();
    }

    @GetMapping("/getByCategory")
    @PreAuthorize("permitAll()")
    public List<SubcategoryResponse> findAllByCategory(@RequestParam String category) {
        return subcategoryService.findAllByCategory(category);
    }

    @GetMapping("/getByGroup")
    @PreAuthorize("permitAll()")
    public List<SubcategoryResponse> findAllByCategoryGroup() {
        return subcategoryService.findAllByCategoryGroup();
    }

    @GetMapping("/globalSearch")
    @PreAuthorize("permitAll()")
    public List<SubcategoryResponse> globalSearch(@RequestParam String keyword) {
        return subcategoryService.globalSearch(keyword);
    }
}
