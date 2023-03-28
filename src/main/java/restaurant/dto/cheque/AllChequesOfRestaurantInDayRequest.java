package restaurant.dto.cheque;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record AllChequesOfRestaurantInDayRequest(
        @NotNull(message = "DATE MOST BE NOT NULL!")
        LocalDate date
) {
}
