package practice.service;

import practice.model.user.UserRequest;
import practice.model.user.UserResponse;

import java.util.List;

public interface UserService {
    void createNewUser(UserRequest userRequest);

    UserResponse getUser(Long userId);

    List<UserResponse> getAllUsers();

    void updateUser(UserRequest request, Long userId);

    void deleteUser(Long userId);
}
