package practice.service;

import org.springframework.stereotype.Service;
import practice.exception.UserNotFoundException;
import practice.model.user.User;
import practice.model.user.UserRequest;
import practice.model.user.UserResponse;
import practice.model.user.mapper.UserMapper;
import practice.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createNewUser(UserRequest userRequest) {
        repository.save(UserMapper.toEntity(userRequest));
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public void updateUser(UserRequest userRequest, Long userId) {
        User user = repository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        repository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);
    }
}
