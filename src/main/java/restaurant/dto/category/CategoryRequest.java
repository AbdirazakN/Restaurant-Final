package restaurant.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @NotBlank(message = "CATEGORY'S NAME CAN'T BE EMPTY!")
        String name
) {
}
