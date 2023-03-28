package restaurant.dto.menuItem;

import lombok.Getter;
import lombok.Setter;
import restaurant.entities.MenuItem;

import java.util.List;

@Getter
@Setter
public class PaginationResponse {
    private List<MenuItem> MenuItems;
    private int currentPage;
    private int pageSize;
}
