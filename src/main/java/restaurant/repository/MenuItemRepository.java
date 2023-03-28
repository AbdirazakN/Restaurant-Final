package restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.dto.menuItem.MenuItemResponse;
import restaurant.dto.menuItem.MenuItemSearchResponse;
import restaurant.entities.MenuItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select distinct new restaurant.dto.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m join StopList s on s.menuItem.id = m.id where s.date != current date ")
    List<MenuItemResponse> getAll();
    @Query("select distinct new restaurant.dto.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.id = :id")
    Optional<MenuItemResponse> getByIdResponse(Long id);

    List<MenuItemResponse> getMenuItemByStopListsNull();

    @Query("select new restaurant.dto.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.name ilike concat('%', :keyWord, '%') or m.description " +
            "ilike concat('%', :keyWord, '%') or m.subcategory.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.category.name ilike concat('%', :keyWord, '%')")
    List<MenuItemResponse> globalSearch(String keyWord);

    @Query("select new restaurant.dto.menuItem.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.stopLists = null and (m.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.name ilike concat('%', :keyWord, '%') or m.subcategory.category.name " +
            "ilike concat('%', :keyWord, '%'))")
    List<MenuItemResponse> globalSearchStopListsNull(String keyWord);

    List<MenuItemResponse> getAllByOrderByPriceDesc();

    List<MenuItemResponse> getAllByOrderByPriceAsc();

    List<MenuItemResponse> findMenuItemByIsVegetarian(boolean isTrue);
    @Query("select new restaurant.dto.menuItem.MenuItemResponse(" +
            "m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m where m.id=:menuItemId")
    Optional<MenuItemResponse> findMenuItemResponseById(Long menuItemId);

    Boolean existsByName(String MenuItemName);

    @Override
    Page<MenuItem> findAll(Pageable pageable);

    @Query("select new restaurant.dto.menuItem.MenuItemSearchResponse(m.id,m.subcategory.category.name,m.subcategory.name,m.name,m.price) from MenuItem m " +
            "where (m.name ilike %:global% or m.subcategory.name ilike %:global% or m.subcategory.category.name ilike %:global%) " +
            "and (:isVegan is null or m.isVegetarian = :isVegan) order by m.price asc ")
    List<MenuItemSearchResponse> findAllByGlobalAndSortAndIsVeganAsc(@Param("global") String global,
                                                                     @Param("isVegan") Boolean isVegan);

    @Query("select new restaurant.dto.menuItem.MenuItemSearchResponse(m.id,m.subcategory.category.name,m.subcategory.name,m.name,m.price) from MenuItem m " +
            "where (m.name ilike %:global% or m.subcategory.name ilike %:global% or m.subcategory.category.name ilike %:global%) " +
            "and (:isVegan is null or m.isVegetarian = :isVegan) order by  m.price desc ")
    List<MenuItemSearchResponse> findAllByGlobalAndSortAndIsVeganDesc(@Param("global") String global,
                                                                   @Param("isVegan") Boolean isVegan);
}