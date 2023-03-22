package restaurant.dto.response;

import restaurant.entity.MenuItem;
import restaurant.entity.User;

import java.time.LocalDate;
import java.util.List;

public record ChequeResponse(

        LocalDate createdAt,
        String userFullName,
        List<MenuItem> items,
        Double averagePrice,
        Double service,
        Double grandTotal
) {
}
