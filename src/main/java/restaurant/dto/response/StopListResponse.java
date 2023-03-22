package restaurant.dto.response;

import restaurant.entity.MenuItem;

import java.time.LocalDate;

public record StopListResponse(
        String reason,
        LocalDate date
) {
}
