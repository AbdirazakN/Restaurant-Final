package restaurant.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.SimpleResponse;
import restaurant.dto.user.*;
import restaurant.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<UserResponse> getAll(){
        return userService.getAll();
    }
    @PostMapping("/register")
    SimpleResponse register(@RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveFromAdmin")
    public SimpleResponse saveUser(@RequestBody  UserAdminRequest request) {
        return userService.save(request);

    }

    @PostMapping("/authenticate")
    UserTokenResponse authenticate(@RequestBody UserRequest userRequest){
        return userService.authenticate(userRequest);
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> applications(){
        return userService.getApplications();
    }
    @PostMapping("/accept/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse acceptResponse(@PathVariable Long restaurantId,
                                  @RequestBody AcceptOrRejectRequest acceptOrRejectRequest){
        return userService.acceptResponse(restaurantId, acceptOrRejectRequest);
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    SimpleResponse updateUser(@PathVariable Long userId,
                            @RequestBody RegisterRequest request){
        return userService.updateUser(userId, request);
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }
}
