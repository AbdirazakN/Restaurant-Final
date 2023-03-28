package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.stopList.StopListRequest;
import restaurant.dto.stopList.StopListResponse;
import restaurant.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/{menuItemId}/stopList")
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<StopListResponse> getStopLists(@PathVariable Long menuItemId) {
        return stopListService.getStopLists(menuItemId);
    }

    @GetMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    StopListResponse findStopListById(@PathVariable Long stopListId) {
        return stopListService.findStopListById(stopListId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse create(@PathVariable Long menuItemId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.create(menuItemId, stopListRequest);
    }

    @PutMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(menuItemId, stopListId, stopListRequest);
    }
    @DeleteMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId){
        return stopListService.delete(menuItemId, stopListId);
    }
}
