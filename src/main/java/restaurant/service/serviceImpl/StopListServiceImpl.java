package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.StopListRequest;
import restaurant.dto.response.StopListResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.MenuItem;
import restaurant.entity.StopList;
import restaurant.repository.StopListRepository;
import restaurant.service.StopListService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;

    @Override
    public SimpleResponse save(StopListRequest stopListRequest) {
        if (stopListRepository.existsByDate(stopListRequest.date()) && stopListRepository.existsByMenuItem(new MenuItem())) {
            throw new IllegalArgumentException("This Stop List Already saved...");
        }
        StopList stopList = new StopList();
        stopList.setReason(validationReason(stopListRequest.reason()));
        stopList.setDate(validationDate(stopListRequest.date()));
        stopListRepository.save(stopList);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", stopListRequest.date())).build();
    }

    @Override
    public SimpleResponse delete(Long stopListId) {
        stopListRepository.deleteById(stopListId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", stopListId)).build();
    }

    @Override
    public StopListResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id).orElseThrow();
        stopList.setReason(validationReason(stopListRequest.reason()));
        stopList.setDate(validationDate(stopListRequest.date()));
        stopListRepository.save(stopList);
        return new StopListResponse(stopListRequest.reason(), stopListRequest.date());
    }

    @Override
    public StopListResponse findById(Long id) {
        return stopListRepository.findByIdResponse(id).orElseThrow();
    }

    @Override
    public List<StopListResponse> findAll() {
        return stopListRepository.findAllResponse();
    }

    public String validationReason(String reason) {
        if (reason.trim().length() < 15 || reason.length() > 100 || reason == null) {
            throw new IllegalArgumentException("Invalid Stop List reason...");
        }
        return reason;
    }

    public LocalDate validationDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())
                || date.isAfter(LocalDate.now().plusDays(10))
                || date == null) {
            throw new IllegalArgumentException("Invalid Stop List date...");
        }
        return date;
    }
}
