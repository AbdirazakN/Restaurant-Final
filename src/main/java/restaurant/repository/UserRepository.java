package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.dto.response.UserResponse;
import restaurant.entity.User;
import restaurant.entity.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByRole(Role role);

    @Query("select new restaurant.dto.response.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.password,u.phoneNumber,u.role,u.experience,u.restaurant.name) from User u where u.id = :userId")
    Optional<UserResponse> findByIdResponse(Long userId);

    @Query("select new restaurant.dto.response.UserResponse(u.firstName,u.lastName,u.dateOfBirth,u.email,u.password,u.phoneNumber,u.role,u.experience,u.restaurant.name) from User u")
    List<UserResponse> findAllResponse();
    @Query("select count(u) from User u where u.id = :restId")
    int getQualityOfEmployees(Long restId);
}