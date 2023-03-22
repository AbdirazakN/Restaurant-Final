package restaurant.service;

import restaurant.dto.request.CategoryRequest;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.ChequesOfDayResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse save(ChequeRequest chequeRequest);

    SimpleResponse delete(Long chequeId);

    ChequeResponse update(Long id, ChequeRequest chequeRequest);

    ChequeResponse findById(Long id);

    List<ChequeResponse> findAll();

    ChequesOfDayResponse findChequesOfDay(LocalDate date);
}
