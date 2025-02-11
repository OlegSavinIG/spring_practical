package practice.model.user.mapper;

import org.springframework.stereotype.Component;
import practice.model.user.User;
import practice.model.user.UserRequest;
import practice.model.user.UserResponse;

@Component
public class UserMapper {
    public static User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        return new User(null, request.getName(), request.getEmail());
    }

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse(user.getName(), user.getEmail());
        response.getOrders().addAll(user.getOrders());
        return response;
    }
}

