package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.StopListRequest;
import restaurant.dto.response.StopListResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
@RequiredArgsConstructor
public class StopListAPI {
    private final StopListService stopListService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody StopListRequest stopListRequest) {
        return stopListService.save(stopListRequest);
    }

    @DeleteMapping("/delete/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long stopListId) {
        return stopListService.delete(stopListId);
    }

    @PostMapping("/update/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse update(@PathVariable Long menuItemId, @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(menuItemId, stopListRequest);
    }

    @GetMapping("/get/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse findById(@PathVariable Long stopListId) {
        return stopListService.findById(stopListId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public List<StopListResponse> findAll() {
        return stopListService.findAll();
    }
}
