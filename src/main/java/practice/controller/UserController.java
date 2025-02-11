package practice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.model.user.UserRequest;
import practice.model.user.UserResponse;
import practice.model.order.validation.Views;
import practice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createNewUser(@Valid @RequestBody UserRequest userRequest){
        service.createNewUser(userRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/userinfo/{userId}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId){
       UserResponse response = service.getUser(userId);
       return ResponseEntity.ok(response);
    }
    @GetMapping("/allusers")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<UserResponse>> getAllUsers(){
       List<UserResponse> users = service.getAllUsers();
       return ResponseEntity.ok(users);
    }
    @PatchMapping("/update/{userId}")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserRequest request,
                                                 @PathVariable Long userId){
        service.updateUser(request, userId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
