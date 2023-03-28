package restaurant.dto.cheque;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
@Builder
public record ChequeRequest(
        @NotNull(message = "USER ID IS EMPTY!")
        Long userId,
        @NotNull(message = "MEALS ID CAN'T BE EMPTY!")
        List<Long> mealsId
) {
}
