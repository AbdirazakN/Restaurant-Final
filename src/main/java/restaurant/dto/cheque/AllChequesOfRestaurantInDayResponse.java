package restaurant.dto.cheque;

import lombok.Builder;

@Builder
public record AllChequesOfRestaurantInDayResponse(
        int numberOfWaiters,
        int numberOfCheques,
        int totalAmount
) {
}
