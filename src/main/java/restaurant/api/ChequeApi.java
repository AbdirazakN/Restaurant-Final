package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.cheque.ChequeRequest;
import restaurant.dto.cheque.ChequeResponse;
import restaurant.dto.cheque.SumAllChequeOfDay;
import restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/{userId}/cheque")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<ChequeResponse> getAll(@PathVariable Long userId){
        return chequeService.getAll(userId);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse createCheque(@PathVariable Long userId,
                                @RequestBody ChequeRequest chequeRequest){
        return chequeService.createCheque(userId, chequeRequest);
    }
    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse update(@PathVariable Long userId,
                          @PathVariable Long chequeId,
                          @RequestBody ChequeRequest chequeRequest){
        return chequeService.update(chequeId, chequeRequest);
    }
    @DeleteMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse delete(@PathVariable Long userId,
                          @PathVariable Long chequeId){
        return chequeService.delete(chequeId);
    }
    @GetMapping("/sumAllChequeOfDay")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SumAllChequeOfDay sumAllChequeOfDay(@PathVariable Long userId,
                                        @RequestParam LocalDate date){
        return chequeService.sumAllChequeOfDay(userId, date);
    }
    @GetMapping("/avgChequeSum")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    SimpleResponse avgCheque(@PathVariable Long userId,
                             @RequestParam LocalDate date){
        return chequeService.avgCheque(userId, date);
    }
}
