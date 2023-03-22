package restaurant.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.config.jwt.JwtUtil;
import restaurant.dto.request.AcceptOrRejectEmployee;
import restaurant.dto.request.UserAuthenticateRequest;
import restaurant.dto.request.UserRequest;
import restaurant.dto.response.UserResponse;
import restaurant.dto.response.UserTokenResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.Restaurant;
import restaurant.entity.User;
import restaurant.entity.enums.Role;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public SimpleResponse save(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(validationFirstAndLastname(userRequest.firstName()));
        user.setLastName(validationFirstAndLastname(userRequest.lastName()));
        user.setDateOfBirth(validationDateOfBirth(userRequest.dateOfBirth()));
        user.setEmail(validationEmail(userRequest.email()));
        user.setPassword(passwordEncoder.encode(validationPassword(userRequest.password())));
        user.setPhoneNumber(validationPhoneNumber(userRequest.phoneNumber()));
        user.setExperience(validationExperienceWaiter(userRequest.experience()));
        user.setIsChecked(false);
        user.setRole(Role.WAITER);

        Restaurant restaurant = new Restaurant();
        if (restaurantRepository.existsByName(userRequest.restaurantName())) {
            restaurant = restaurantRepository.findByName(userRequest.restaurantName()).orElseThrow();
        }
        restaurant.setName(userRequest.restaurantName());
        restaurant.setUser(List.of(user));
        if (restaurant.getNumberOfEmployees() >= 15) {
            if (restaurant.getNumberOfEmployees() < 0) {
                return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("Invalid number of Employees...")).build();
            }
            return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("No more vacancies...")).build();
        }
        restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees() + 1);
        user.setRestaurant(restaurant);
        userRepository.save(user);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Saved...", userRequest.firstName())).build();
    }

    @Override
    public SimpleResponse delete(Long userId) {
        userRepository.deleteById(userId);
        return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("%s The Deleted...", userId)).build();
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        Restaurant restaurant = new Restaurant();
        if (restaurantRepository.existsByName(userRequest.restaurantName())) {
            restaurant = restaurantRepository.findByName(userRequest.restaurantName()).orElseThrow();
        }
        restaurant.setName(userRequest.restaurantName());
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(validationFirstAndLastname(userRequest.firstName()));
        user.setLastName(validationFirstAndLastname(userRequest.lastName()));
        user.setDateOfBirth(validationDateOfBirth(userRequest.dateOfBirth()));
        user.setEmail(validationEmail(userRequest.email()));
        user.setPassword(validationPassword(userRequest.password()));
        user.setPhoneNumber(validationPhoneNumber(userRequest.phoneNumber()));
        user.setExperience(validationExperienceWaiter(userRequest.experience()));
        user.setRole(Role.WAITER);
        restaurant.setUser(List.of(user));
        userRepository.save(user);
        return new UserResponse(userRequest.firstName(), userRequest.lastName(), userRequest.dateOfBirth(), userRequest.email(), userRequest.password(), userRequest.phoneNumber(), user.getRole(), userRequest.experience(), userRequest.restaurantName());
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findByIdResponse(id).orElseThrow();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAllResponse();
    }

    @Override
    public UserTokenResponse authenticate(UserAuthenticateRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.email(),
                userRequest.password()
        ));

        User user = userRepository.findByEmail(userRequest.email()).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("User %s is not found!", userRequest.email()));
        });
        if (user.getIsChecked()) {
            return UserTokenResponse.builder()
                    .email(userRequest.email())
                    .token(jwtUtil.generateToken(user))
                    .build();
        }
        return UserTokenResponse.builder()
                .email(userRequest.email())
                .build();
    }

    @Override
    public SimpleResponse acceptOrRejectEmployee(Long restId, AcceptOrRejectEmployee acceptOrRejectEmployee) {
        Restaurant restaurant = restaurantRepository.findById(restId).orElseThrow();
        User user = userRepository.findById(acceptOrRejectEmployee.userId()).orElseThrow();
        if (acceptOrRejectEmployee.isChecked()){
            restaurant.setUser(List.of(user));
            user.setRestaurant(restaurant);
            return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("Employee with ID: %s successfully removed!", acceptOrRejectEmployee.userId())).build();
        }else {
            userRepository.deleteById(acceptOrRejectEmployee.userId());
            return SimpleResponse.builder().status(HttpStatus.OK).message(String.format("Employee with ID: %s successfully removed!", acceptOrRejectEmployee.userId())).build();
        }
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setDateOfBirth(LocalDate.of(2012, 12, 12));
            user.setPhoneNumber("+996700000000");
            user.setRole(Role.ADMIN);
            user.setEmail("admin@mail.ru");
            user.setPassword(passwordEncoder.encode("Admin123"));
            user.setExperience(0);

            userRepository.save(user);
        }
    }

    public String validationFirstAndLastname(String firstLastname) {
        if (firstLastname.trim().length() < 3 || firstLastname.length() > 30 || firstLastname == null) {
            throw new IllegalArgumentException("Invalid User firstLastname...");
        }
        return firstLastname;
    }

    public String validationEmail(String email) {
        if (email.trim().length() < 3
                || email.length() > 30
                || email == null
                || userRepository.existsByEmail(email)
                || !(email.endsWith("@mail.ru")
                || email.endsWith("@gmail.com")
                || email.endsWith("@email.com"))) {
            throw new IllegalArgumentException("Invalid User email...");
        }
        return email;
    }

    public LocalDate validationDateOfBirth(LocalDate dateOfBirth) {
        if (LocalDate.now().getYear() - dateOfBirth.getYear() < 25
                || LocalDate.now().getYear() - dateOfBirth.getYear() > 45
                || dateOfBirth == null) {
            throw new IllegalArgumentException("Invalid User dateOfBirth...");
        }
        return dateOfBirth;
    }

    public LocalDate validationDateOfBirthWaiter(LocalDate dateOfBirth) {
        if (LocalDate.now().getYear() - dateOfBirth.getYear() < 18
                || LocalDate.now().getYear() - dateOfBirth.getYear() > 30
                || dateOfBirth == null) {
            throw new IllegalArgumentException("Invalid User dateOfBirth...");
        }
        return dateOfBirth;
    }

    public int validationExperienceChef(int experience) {
        if (experience < 2 || experience > 30) {
            throw new IllegalArgumentException("Invalid User Experience...");
        }
        return experience;
    }

    public int validationExperienceWaiter(int experience) {
        if (experience < 1 || experience > 30) {
            throw new IllegalArgumentException("Invalid User Experience...");
        }
        return experience;
    }

    public String validationPhoneNumber(String phoneNumber) {
        if (phoneNumber.trim().length() != 13
                || !phoneNumber.startsWith("+996")
                || phoneNumber.charAt(4) == '8'
                || phoneNumber.charAt(4) == '6'
                || phoneNumber.charAt(4) == '4'
                || phoneNumber.charAt(4) == '3'
                || phoneNumber.charAt(4) == '1'
                || phoneNumber.charAt(4) == '0') {
            throw new IllegalArgumentException("Invalid User Phone Number...");
        }
        return phoneNumber;
    }

    public String validationPassword(String password) {
        if (password.trim().length() < 5 || password.length() > 40) {
            throw new IllegalArgumentException("Invalid User Password...");
        }
        return password;
    }
}
