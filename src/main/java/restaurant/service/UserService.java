package restaurant.service;

import restaurant.dto.request.AcceptOrRejectEmployee;
import restaurant.dto.request.CategoryRequest;
import restaurant.dto.request.UserAuthenticateRequest;
import restaurant.dto.request.UserRequest;
import restaurant.dto.response.CategoryResponse;
import restaurant.dto.response.UserResponse;
import restaurant.dto.response.UserTokenResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    SimpleResponse save(UserRequest userRequest);

    SimpleResponse delete(Long userId);

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse findById(Long id);

    List<UserResponse> findAll();

    UserTokenResponse authenticate(UserAuthenticateRequest userRequest);

    SimpleResponse acceptOrRejectEmployee(Long restId, AcceptOrRejectEmployee acceptOrRejectEmployee);
}
