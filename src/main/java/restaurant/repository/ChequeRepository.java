package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.ChequeResponse;
import restaurant.entity.Cheque;

import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new restaurant.dto.response.ChequeResponse(c.createdAt,concat(c.user.firstName,' ',c.user.lastName),c.menuItem,c.priceAverage,c.priceAverage,c.priceAverage) from Cheque c where c.id = :chequeId")
    Optional<ChequeResponse> findByIdResponse(Long chequeId);

    @Query("select new restaurant.dto.response.ChequeResponse(c.createdAt,concat(c.user.firstName,' ',c.user.lastName),c.menuItem,c.priceAverage,c.priceAverage,c.priceAverage) from Cheque c")
    List<ChequeResponse> findAllResponse();

    @Query("select count (c.priceAverage) from Cheque c where c.createdAt between date('2023-03-20') and date('2023-03-20')")
    Double AllSumOfDay(Long waiterId);

    @Query("select avg (c.priceAverage) from Cheque c where c.createdAt between date('2023-03-20') and date('2023-03-20')")
    Double AllAvgSumOfDay(Long waiterId);
}