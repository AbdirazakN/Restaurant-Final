package restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.dto.request.AcceptOrRejectEmployee;
import restaurant.dto.request.UserAuthenticateRequest;
import restaurant.dto.request.UserRequest;
import restaurant.dto.response.UserResponse;
import restaurant.dto.response.UserTokenResponse;
import restaurant.dto.response.simpleResponse.SimpleResponse;
import restaurant.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public SimpleResponse save(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public SimpleResponse delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    @PostMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public UserResponse update(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return userService.update(userId, userRequest);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("permitAll()")
    public UserResponse findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/getAll")
    @PreAuthorize("permitAll()")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PostMapping("/authenticate")
    public UserTokenResponse authenticate(@RequestBody UserAuthenticateRequest userAuthenticateRequest){
        return userService.authenticate(userAuthenticateRequest);
    }

    @PostMapping("/accept/{restId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse accept(@PathVariable Long restId, @RequestBody AcceptOrRejectEmployee acceptOrRejectEmployee){
        return userService.acceptOrRejectEmployee(restId,acceptOrRejectEmployee);
    }
}
