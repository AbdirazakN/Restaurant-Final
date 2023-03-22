package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.ChequesOfDayResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheque")
@RequiredArgsConstructor
public class ChequeAPI {
    private final ChequeService chequeService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public SimpleResponse save(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.save(chequeRequest);
    }

    @DeleteMapping("/delete/{chequeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long chequeId) {
        return chequeService.delete(chequeId);
    }

    @PostMapping("/update/{chequeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequeResponse update(@PathVariable Long chequeId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.update(chequeId, chequeRequest);
    }

    @GetMapping("/get/{chequeId}")
    @PreAuthorize("permitAll()")
    public ChequeResponse findById(@PathVariable Long chequeId) {
        return chequeService.findById(chequeId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    public List<ChequeResponse> findAll() {
        return chequeService.findAll();
    }

    @GetMapping("/getChequesOfDay")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChequesOfDayResponse findChequesOfDay(@RequestParam LocalDate date) {
        return chequeService.findChequesOfDay(date);
    }
}
