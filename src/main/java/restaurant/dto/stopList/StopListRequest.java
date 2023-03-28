package restaurant.dto.stopList;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record StopListRequest(
        @NotBlank(message = "WRITE SOME REASONS!")
        String reason,
        @NotBlank(message = "DATE CAN'T BE EMPTY!")
        LocalDate date
) {
}
