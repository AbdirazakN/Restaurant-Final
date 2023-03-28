package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.dto.stopList.StopListResponse;
import restaurant.entities.StopList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {

    List<StopListResponse> findAllByMenuItemId(Long menuItemId);
    @Query("select new restaurant.dto.stopList.StopListResponse(s.id,s.menuItem.name,s.reason,s.date) from StopList s")
    List<StopListResponse> findAllStopList();
    @Query("select new restaurant.dto.stopList.StopListResponse(s.id,s.menuItem.name,s.reason,s.date) from StopList s where s.id=:id")
    Optional<StopListResponse> findStopListById(Long id);

}