package practice.model.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import practice.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Order {
    @Id
    private Long id;
    private User user;
    private final List<String> items = new ArrayList<>();
    private Integer sum;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order() {
    }

    public Order(Long id, User user, Integer sum, Status status) {
        this.id = id;
        this.user = user;
        this.sum = sum;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getItems() {
        return items;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
