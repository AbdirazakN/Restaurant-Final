package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.MenuItemResponse;
import restaurant.dto.response.StopListResponse;
import restaurant.entity.MenuItem;
import restaurant.entity.StopList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new restaurant.dto.response.StopListResponse(s.reason,s.date) from StopList s where s.id = :stopListId")
    Optional<StopListResponse> findByIdResponse(Long stopListId);

    @Query("select new restaurant.dto.response.StopListResponse(s.reason,s.date) from StopList s")
    List<StopListResponse> findAllResponse();

    Boolean existsByDate(LocalDate date);

    Boolean existsByMenuItem(MenuItem menuItem);
}