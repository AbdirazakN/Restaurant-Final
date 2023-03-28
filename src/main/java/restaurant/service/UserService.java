package restaurant.service;

import restaurant.dto.SimpleResponse;
import restaurant.dto.user.*;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    SimpleResponse register(RegisterRequest registerRequest);

    UserTokenResponse authenticate(UserRequest userRequest);

    List<UserResponse> getApplications();

    SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest);

    SimpleResponse updateUser(Long userId, RegisterRequest request);

    SimpleResponse deleteUser(Long userId);

    SimpleResponse save(UserAdminRequest request);

}
