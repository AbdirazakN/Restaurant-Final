package restaurant.dto.request;

import restaurant.entity.MenuItem;
import restaurant.entity.User;

import java.time.LocalDate;
import java.util.List;

public record ChequeRequest(
        Long userId,
        Long menuItemId
) {
}
