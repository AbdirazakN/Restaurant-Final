package restaurant.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.SimpleResponse;
import restaurant.dto.menuItem.MenuItemRequest;
import restaurant.dto.menuItem.MenuItemResponse;
import restaurant.dto.menuItem.MenuItemSearchResponse;
import restaurant.dto.menuItem.PaginationResponse;
import restaurant.entities.MenuItem;
import restaurant.entities.Restaurant;
import restaurant.entities.Subcategory;
import restaurant.exception.ExistsException;
import restaurant.exception.NotFoundException;
import restaurant.repository.MenuItemRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.SubCategoryRepository;
import restaurant.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RestaurantRepository restaurantRepository,
                               SubCategoryRepository subCategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<MenuItemResponse> getAll(Long subCategoryId, String keyWord) {
        List<MenuItemResponse> list = new ArrayList<>();
        if (keyWord == null) {
            list.addAll(menuItemRepository.getAll());
            list.addAll(menuItemRepository.getMenuItemByStopListsNull());
            return list;
        }
        list.addAll(menuItemRepository.globalSearch(keyWord));
        return list;
    }

    @Override
    public MenuItemResponse getById(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new NotFoundException("MenuItem with id - "
                    + menuItemId + " is not found!");
        }
        return menuItemRepository.getByIdResponse(menuItemId).orElseThrow(
                () -> new NotFoundException(String.format("MenuItem with ID: " + menuItemId + " not found!")));
    }

    @Override
    public SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        if (menuItemRepository.existsByName(menuItemRequest.name())) {
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("Menu item - " + menuItemRequest.name() + " is already Saved!")
                    .build();
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new NotFoundException("Sub category not found!"));
        menuItem.setSubcategory(subcategory);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found!"));
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(
                () -> new NotFoundException("Menu with id - " + menuItemId + " is not found!"));
        if (!menuItem.getName().equals(menuItemRequest.name())) {
            if (menuItemRepository.findAll().stream().anyMatch(s -> s.getName().equals(menuItemRequest.name()))) {
                return SimpleResponse.builder()
                        .status(HttpStatus.CONFLICT)
                        .description("Menu item - " + menuItemRequest.name() + " is already Saved!")
                        .build();
            }
        }
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new ExistsException("Menu item with id - " + menuItemId + " doesn't exists!");
        }
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item with id - " + menuItemId + " is deleted!")
                .build();
    }

    @Override
    public List<MenuItemResponse> sort(String ascOrDesc) {
        if (ascOrDesc.equals("desc")) {
            return menuItemRepository.getAllByOrderByPriceDesc();
        } else {
            return menuItemRepository.getAllByOrderByPriceAsc();
        }
    }

    @Override
    public List<MenuItemResponse> isVegetarian(boolean isTrue) {
        return menuItemRepository.findMenuItemByIsVegetarian(isTrue);
    }

    @Override
    public PaginationResponse getMenuItemPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page-1,size);
        Page<MenuItem> pagedMenuItem = menuItemRepository.findAll(pageable);

        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setMenuItems(pagedMenuItem.getContent());
        paginationResponse.setCurrentPage(pageable.getPageNumber()+1);
        paginationResponse.setPageSize(pagedMenuItem.getTotalPages());

        return paginationResponse;
    }

    @Override
    public List<MenuItemSearchResponse> findAll(String global, String sort, Boolean isVegan) {
        if (sort.equalsIgnoreCase("asc")) {
            return menuItemRepository.findAllByGlobalAndSortAndIsVeganAsc(global, isVegan);
        } else if (sort.equalsIgnoreCase("desc")) {
            return menuItemRepository.findAllByGlobalAndSortAndIsVeganDesc(global, isVegan);
        } else {
            return null;
        }
    }

}
