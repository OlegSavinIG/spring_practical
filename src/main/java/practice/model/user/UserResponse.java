package practice.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import practice.model.order.Order;
import practice.model.order.validation.Views;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    @JsonView(Views.UserSummary.class)
    private String name;
    @JsonView(Views.UserSummary.class)
    private String email;
    @JsonView(Views.UserDetails.class)
    private final List<Order> orders = new ArrayList<>();

    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
