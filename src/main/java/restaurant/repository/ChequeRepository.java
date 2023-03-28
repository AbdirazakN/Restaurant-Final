package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restaurant.dto.cheque.ChequeResponse;
import restaurant.dto.cheque.MenuItemForCheque;
import restaurant.entities.Cheque;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new restaurant.dto.cheque.ChequeResponse(c.id, c.createdAt," +
            " concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.user.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    List<ChequeResponse> getAllChequeByUserId (Long userId);

    @Query("select new restaurant.dto.cheque.MenuItemForCheque(m.id, m.name, m.price, " +
            "count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemForCheque> getMeals(Long chequeId);

    @Query("select sum (m.price)from User u join u.cheques c join c.menuItems m where u.id=:id and " +
            "c.createdAt = :date")
    Integer getSumByCreatedAt(LocalDate date, Long id);

    @Query("select avg(m.price) from Restaurant r join r.users u join u.cheques c join c.menuItems m where r.id=1 and c.createdAt=:date")
    Integer avgSumInDay(LocalDate date);

}