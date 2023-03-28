package restaurant.dto.cheque;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record TotalAmountWaiterInDayChequesResponse(
        String waiterFullName,
        int numberOfCheques,
        BigDecimal totalAmount
) {
}
