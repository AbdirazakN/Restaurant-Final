package restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restaurant.dto.request.ChequeRequest;
import restaurant.dto.response.ChequeResponse;
import restaurant.dto.response.ChequesOfDayResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Cheque;
import restaurant.entity.MenuItem;
import restaurant.entity.Restaurant;
import restaurant.entity.User;
import restaurant.repository.ChequeRepository;
import restaurant.repository.MenuItemRepository;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse save(ChequeRequest chequeRequest) {
        User user = userRepository.findById(chequeRequest.userId()).orElseThrow();
        MenuItem menuItem = menuItemRepository.findById(chequeRequest.menuItemId()).orElseThrow();
        Cheque cheque = new Cheque();
        cheque.setMenuItem(List.of(menuItem));
        cheque.setCreatedAt(LocalDate.now());
        cheque.setPriceAverage(menuItem.getPrice());
        cheque.setUser(user);
        chequeRepository.save(cheque);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", chequeRequest.userId())).build();
    }

    @Override
    public SimpleResponse delete(Long chequeId) {
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Deleted...", chequeId)).build();
    }

    @Override
    public ChequeResponse update(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        double sum = 0;
        int quantity = 0;
        for (MenuItem item : cheque.getMenuItem()) {
            sum += item.getPrice();
            quantity++;
        }
        sum /= quantity;
        cheque.setCreatedAt(LocalDate.now());
        chequeRepository.save(cheque);
        return new ChequeResponse(cheque.getCreatedAt(), null, null, sum, null, null);
    }

    @Override
    public ChequeResponse findById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow();
        double service = cheque.getUser().getRestaurant().getService();
        double sum = 0;
        int count = 0;
        double avgPrice = 0;
        for (MenuItem menuItem : cheque.getMenuItem()) {
            sum += menuItem.getPrice();
            count++;
        }
        sum = (sum * service) + sum;
        avgPrice = sum / count;
        return new ChequeResponse(cheque.getCreatedAt(),
                cheque.getUser().getFirstName() + " " +
                        cheque.getUser().getLastName(),
                cheque.getMenuItem(),
                avgPrice,
                cheque.getUser().getRestaurant().getService(),
                sum);
    }

    @Override
    public List<ChequeResponse> findAll() {
        List<Cheque> cheques = chequeRepository.findAll();
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        for (Cheque cheque : cheques) {
            double service = cheque.getUser().getRestaurant().getService();
            double sum = 0;
            int count = 0;
            double avgPrice = 0;
            for (MenuItem menuItem : cheque.getMenuItem()) {
                sum += menuItem.getPrice();
                count++;
            }
            sum = (sum * service) + sum;
            avgPrice = sum / count;
            ChequeResponse chequeResponse = new ChequeResponse(cheque.getCreatedAt(),
                    cheque.getUser().getFirstName() + " " +
                            cheque.getUser().getLastName(),
                    cheque.getMenuItem(),
                    avgPrice,
                    cheque.getUser().getRestaurant().getService(),
                    sum);
            chequeResponses.add(chequeResponse);
        }

        return chequeResponses;
    }

    @Override
    public ChequesOfDayResponse findChequesOfDay(LocalDate date) {

        int quantityOfCheques = 0;
        double sumOfCheques = 0;
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow();
        for (User user : restaurant.getUser()) {
            for (Cheque cheque : user.getCheque()) {
                if (date.equals(cheque.getCreatedAt())){
                    sumOfCheques += cheque.getPriceAverage();
                    quantityOfCheques++;
                }
            }
        }
        return new ChequesOfDayResponse(date,quantityOfCheques,sumOfCheques);
    }
}
