package restaurant.service;

import restaurant.dto.request.CategoryRequest;
import restaurant.dto.request.StopListRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.StopListResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse save(StopListRequest stopListRequest);

    SimpleResponse delete(Long stopListId);

    StopListResponse update(Long id, StopListRequest stopListRequest);

    StopListResponse findById(Long id);

    List<StopListResponse> findAll();
}
