package restaurant.dto.response;

import java.time.LocalDate;

public record ChequesOfDayResponse(
        LocalDate date,
        int quantityOfCheques,
        double sumOfCheques
) {
}
