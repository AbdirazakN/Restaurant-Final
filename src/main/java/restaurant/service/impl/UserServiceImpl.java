package restaurant.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.dto.SimpleResponse;
import restaurant.dto.user.*;
import restaurant.entities.User;
import restaurant.entities.enums.Role;
import restaurant.exception.ExistsException;
import restaurant.exception.NoVacancyException;
import restaurant.exception.NoValidException;
import restaurant.exception.NotFoundException;
import restaurant.repository.RestaurantRepository;
import restaurant.repository.UserRepository;
import restaurant.security.jwt.JwtUtil;
import restaurant.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@Builder
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RestaurantRepository restaurantRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void init() {
        if ((!userRepository.existsByEmail("admin@gmail.com"))
                && (!userRepository.existsByRole(Role.ADMIN.name()))) {
            User user = new User();
            user.setFirstName("ADMIN");
            user.setLastName("ADMIN");
            user.setExperience(0);
            user.setDateOfBirth(LocalDate.of(2012, 12, 12));
            user.setPhoneNumber("+996200000000");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("Admin2023"));
            user.setRole(Role.ADMIN);
            user.setAccepted(true);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(registerRequest.email())
                    || registerRequest.email().toLowerCase().contains("admin")) {
                log.error(String.format("User with login: %s is already exists!", registerRequest.email()));
                throw new ExistsException(String.format(
                        "User with login: %s is already exists!", registerRequest.email()
                ));
            }
        }

        if (userRepository.getAllUsers().size() == 15) {
            log.error("Don't have vacancies!");
            throw new NoVacancyException("Don't have vacancies!");
        }
        if (registerRequest.role().equals(Role.ADMIN)) {
            log.error(String.format("Sorry you can't be with Role ADMIN!"));
            throw new ExistsException(String.format(
                    "Sorry you can't be with Role ADMIN!"));
        }
        if (userRepository.existsByEmail(registerRequest.email())) {
            log.error(String.format("User with login: %s is exists", registerRequest.email()));
            throw new ExistsException(String.format(
                    "User with login: %s is exists", registerRequest.email()));
        }
        checkForValid(registerRequest);
        User user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .dateOfBirth(registerRequest.dateOfBirth())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phoneNumber(registerRequest.phoneNumber())
                .role(registerRequest.role())
                .experience(registerRequest.experience())
                .accepted(false)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("User successfully Registered!")
                .build();
    }

    @Override
    public UserTokenResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.login(),
                userRequest.password()
        ));

        User user = userRepository.findByEmail(userRequest.login()).orElseThrow(() -> {
            log.error(String.format("User %s is not found!", userRequest.login()));
            throw new NotFoundException(String.format("User %s is not found!", userRequest.login()));
        });
        if (user.isAccepted()) {
            return UserTokenResponse.builder()
                    .login(userRequest.login())
                    .token(jwtUtil.generateToken(user))
                    .build();
        }
        return UserTokenResponse.builder()
                .login(userRequest.login())
                .token("User not found in Database!")
                .build();
    }

    @Override
    public List<UserResponse> getApplications() {
        return userRepository.getAllApplication();
    }

    @Override
    public SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest) {
        User user = userRepository.findById(acceptOrRejectRequest.userId())
                .orElseThrow(() -> {
                    log.error(String.format("User with id - %s is not found!",
                            acceptOrRejectRequest.userId()));
                    throw new NotFoundException(String.format("User with id - %s is not found!",
                            acceptOrRejectRequest.userId()));
                });
        if (acceptOrRejectRequest.accept()) {
            user.setAccepted(true);
            user.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow(() -> {
                log.error("Restaurant not found!");
                throw new NotFoundException("Restaurant not found!");
            }));
            log.info(String.format("User - %s is accepted!", user.getEmail()));
            return new SimpleResponse(
                    HttpStatus.ACCEPTED,
                    String.format("User - %s is accepted!", user.getEmail())
            );
        } else {
            log.info(String.format("User - %s is rejected!", user.getEmail()));
            userRepository.delete(user);
            return new SimpleResponse(
                    HttpStatus.NOT_ACCEPTABLE,
                    String.format("User - %s is rejected!", user.getEmail())
            );
        }
    }

    @Override
    public SimpleResponse updateUser(Long userId, RegisterRequest registerRequest) {
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(userId) && user.getEmail().equals(registerRequest.email())
                    || registerRequest.email().toLowerCase().contains("admin")) {
                log.error(String.format("User with login: %s is exists", registerRequest.email()));
                throw new ExistsException(String.format(
                        "User with login: %s is exists", registerRequest.email()
                ));
            }
        }
        if (registerRequest.role().equals(Role.ADMIN)) {
            log.error(String.format("Sorry you can't be with Role ADMIN!"));
            throw new ExistsException(String.format(
                    "Sorry you can't be with Role ADMIN!"));
        }
        checkForValid(registerRequest);
        User oldUser = userRepository.findById(userId).orElseThrow(() -> {
            log.error(String.format("User with id - %s is not found!", userId));
            throw new NotFoundException(String.format("User with id - %s is not found!", userId));
        });
        oldUser.setFirstName(registerRequest.firstName());
        oldUser.setLastName(registerRequest.lastName());
        oldUser.setDateOfBirth(registerRequest.dateOfBirth());
        oldUser.setEmail(registerRequest.email());
        oldUser.setPhoneNumber(registerRequest.phoneNumber());
        oldUser.setRole(registerRequest.role());
        oldUser.setExperience(registerRequest.experience());

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description(String.format("User with id - %s is updated!", userId))
                .build();
    }

    private void checkForValid(RegisterRequest registerRequest) {

        int age = LocalDate.now().minusYears(registerRequest.dateOfBirth().getYear()).getYear();
        if (registerRequest.role() == Role.CHEF) {
            if (age < 25 || age > 45) {
                log.error("Chef's age must be between 25 and 45");
                throw new NoValidException("Chef's age must be between 25 and 45");
            }
            if (registerRequest.experience() < 2) {
                log.error("Chef experience must be more than 2 years");
                throw new NoValidException("Chef experience must be more than 2 years");
            }
        } else if (registerRequest.role() == Role.WAITER) {
            if (age < 18 || age > 30) {
                log.error("Waiter's age must be between 18 and 30");
                throw new NoValidException("Waiter's age must be between 18 and 30");
            }
            if (registerRequest.experience() < 1) {
                log.error("Waiter experience must be more than 1 year");
                throw new NoValidException("Waiter experience must be more than 1 year");
            }
        }
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.info(String.format("User with id - %s is not found!", userId));
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description(String.format("User with id - %s is not found!", userId))
                    .build();
        }
        userRepository.deleteById(userId);
        log.info(String.format("User with id - %s is deleted!", userId));
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id - %s is deleted!", userId)
        );
    }

    @Override
    public SimpleResponse save(UserAdminRequest request) {
        if (request.isAccepted().equals(false)){
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .description("USER DON'T ACCEPTED!")
                    .build();
        }
        for (User user : userRepository.findAll()) {
            if (user.getEmail().equals(request.email())
                    || request.email().toLowerCase().contains("admin")) {
                log.error(String.format("User with login: %s is already exists!", request.email()));
                throw new ExistsException(String.format(
                        "User with login: %s is already exists!", request.email()
                ));
            }
        }

        if (userRepository.getAllUsers().size() == 15) {
            log.error("Don't have vacancies!");
            throw new NoVacancyException("Don't have vacancies!");
        }
        if (request.role().equals(Role.ADMIN)) {
            log.error(String.format("Sorry you can't be with Role ADMIN!"));
            throw new ExistsException(String.format(
                    "Sorry you can't be with Role ADMIN!"));
        }
        if (userRepository.existsByEmail(request.email())) {
            log.error(String.format("User with login: %s is exists", request.email()));
            throw new ExistsException(String.format(
                    "User with login: %s is exists", request.email()));
        }

        checkForValid(new RegisterRequest(
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.email(),
                request.password(),
                request.phoneNumber(),
                request.role(),
                request.experience()));
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(request.role())
                .experience(request.experience())
                .accepted(true)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("User successfully Saved!")
                .build();
    }
}
