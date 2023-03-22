package restaurant.dto.request;

public record AcceptOrRejectEmployee(
        Long userId,
        Boolean isChecked
) {
}
