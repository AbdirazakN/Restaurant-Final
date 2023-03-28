package restaurant.dto.subCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SubcategoryRequest(
        @NotBlank(message = "SUBCATEGORY NAME CAN'T BE EMPTY!")
        String name
) {
}
