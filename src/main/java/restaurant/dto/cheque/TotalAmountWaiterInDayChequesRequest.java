package restaurant.dto.cheque;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record TotalAmountWaiterInDayChequesRequest(
        @NotNull(message = "WAITER ID CAN'T BE NULL!")
        Long waiterId,
        @NotNull(message = "DATE CAN'T BE NULL!")
        LocalDate date
) {
}
