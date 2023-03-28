package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.stopList.StopListRequest;
import restaurant.dto.stopList.StopListResponse;

import java.util.List;

public interface StopListService {
    List<StopListResponse> getStopLists(Long menuItemId);

    StopListResponse findStopListById(Long id);

    SimpleResponse create(Long menuItemId, StopListRequest stopListRequest);

    SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest);

    SimpleResponse delete(Long menuItemId, Long stopListId);
}
