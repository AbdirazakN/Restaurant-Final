package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.cheque.*;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAll(Long userId);

    SimpleResponse createCheque(Long userId, ChequeRequest chequeRequest);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SimpleResponse delete(Long chequeId);

    SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date);

    SimpleResponse avgCheque(Long userId, LocalDate date);

    SimpleResponse totalSum(Long id,LocalDate date);

    SimpleResponse avg(LocalDate date);

    AllChequesOfRestaurantInDayResponse findAllChequesOneDayTotalAmount(AllChequesOfRestaurantInDayRequest request);
    AllChequesOfRestaurantInDayResponse countRestGrantTotalForDay(AllChequesOfRestaurantInDayRequest chequeOfRestaurantAmountDayRequest);
}
