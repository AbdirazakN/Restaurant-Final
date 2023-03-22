package restaurant.dto.request;

import restaurant.entity.MenuItem;

import java.time.LocalDate;

public record StopListRequest(
        String reason,
        LocalDate date
) {
}
