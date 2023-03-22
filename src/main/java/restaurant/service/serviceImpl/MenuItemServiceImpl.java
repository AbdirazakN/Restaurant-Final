package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.MenuItemRequest;
import restaurant.dto.response.MenuItemResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.MenuItem;
import restaurant.entity.Subcategory;
import restaurant.repository.MenuItemRepository;
import restaurant.repository.SubcategoryRepository;
import restaurant.service.MenuItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest) {
        Subcategory subcategory = new Subcategory();
        if (subcategoryRepository.existsByName(menuItemRequest.subcategory().getName())) {
            subcategory = subcategoryRepository.findByName(menuItemRequest.subcategory().getName()).get();
        }
        subcategory.setName(menuItemRequest.subcategory().getName());
        MenuItem menuItem = new MenuItem();
        menuItem.setName(validationName(menuItemRequest.name()));
        menuItem.setImage(validationImage(menuItemRequest.image()));
        menuItem.setPrice(validationPrice(menuItemRequest.price()));
        menuItem.setDescription(validationDescription(menuItemRequest.description()));
        menuItem.setIsVegetarian(validationIsVegetarian(menuItemRequest.isVegetarian()));
        menuItem.setSubcategory(subcategory);
        subcategory.setMenuItem(List.of(menuItem));

        menuItemRepository.save(menuItem);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", menuItemRequest.name())).build();
    }

    @Override
    public SimpleResponse delete(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Deleted...", menuItemId)).build();
    }

    @Override
    public MenuItemResponse update(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
        menuItem.setName(validationName(menuItemRequest.name()));
        menuItem.setImage(validationImage(menuItemRequest.image()));
        menuItem.setPrice(validationPrice(menuItemRequest.price()));
        menuItem.setDescription(validationDescription(menuItemRequest.description()));
        menuItem.setIsVegetarian(validationIsVegetarian(menuItemRequest.isVegetarian()));

        menuItemRepository.save(menuItem);
        return new MenuItemResponse(menuItemRequest.name(), menuItemRequest.image(), menuItemRequest.price(), menuItemRequest.description(), menuItemRequest.isVegetarian());
    }

    @Override
    public MenuItemResponse findById(Long id) {
        return menuItemRepository.findByIdResponse(id).orElseThrow();
    }

    @Override
    public List<MenuItemResponse> findAll() {
        return menuItemRepository.findAllResponse();
    }

    @Override
    public List<MenuItemResponse> globalSearch(String keyword) {
        return menuItemRepository.globalSearch(keyword);
    }

    @Override
    public List<MenuItemResponse> orderByPrice() {
        return menuItemRepository.orderByPrice();
    }

    @Override
    public List<MenuItemResponse> orderByPriceDesc() {
        return menuItemRepository.orderByPriceDesc();
    }

    @Override
    public List<MenuItemResponse> isVegetarian(Boolean isVegetarian) {
        return menuItemRepository.isVegetarian(isVegetarian);
    }

    public Double validationPrice(Double price) {
        if (price <= 0 || price > 10000) {
            throw new IllegalArgumentException("Invalid Menu Item Price...");
        }
        return price;
    }

    public String validationName(String menuItemName) {
        if (menuItemName.trim().length() < 3 || menuItemName.length() > 30 || menuItemName == null) {
            throw new IllegalArgumentException("Invalid Menu Item Name...");
        }
        return menuItemName;
    }

    public String validationImage(String image) {
        if (image.trim().length() < 15
                || image.length() > 300
                || image == null
                || !(image.startsWith("http://")
                || image.startsWith("https://"))) {
            throw new IllegalArgumentException("Invalid Menu Item image...");
        }
        return image;
    }

    public String validationDescription(String description) {
        if (description.trim().length() < 15 || description.length() > 250 || description == null) {
            throw new IllegalArgumentException("Invalid Menu Item description...");
        }
        return description;
    }

    public Boolean validationIsVegetarian(Boolean isVegetarian){
        if (!(isVegetarian == true || isVegetarian == false)){
            throw new IllegalArgumentException("Invalid Menu Item is vegetarian...");
        }
        return isVegetarian;
    }
}
