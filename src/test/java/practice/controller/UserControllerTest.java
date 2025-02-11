package practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import practice.exception.UserNotFoundException;
import practice.model.order.Order;
import practice.model.user.UserRequest;
import practice.model.user.UserResponse;
import practice.service.UserService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse("John Doe", "john@example.com");
    }

    @Test
    void createNewUser_ShouldReturnOk() throws Exception {
        UserRequest request = new UserRequest("John Doe", "john@example.com");

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getUser_ShouldReturnUserDetailsView() throws Exception {
        Order order1 = new Order();
        userResponse.getOrders().add(order1);
        when(userService.getUser(1L)).thenReturn(userResponse);

        mockMvc.perform(get("/user/userinfo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.orders").exists());
    }

    @Test
    void getAllUsers_ShouldReturnUserSummaryView() throws Exception {
        Order order1 = new Order();
        userResponse.getOrders().add(order1);
        when(userService.getAllUsers()).thenReturn(List.of(userResponse));

        mockMvc.perform(get("/user/allusers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    @Test
    void updateUser_ShouldReturnNoContent() throws Exception {
        UserRequest request = new UserRequest("Updated Name", "updated@example.com");

        mockMvc.perform(patch("/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(1L);
    }

    @Test
    void errorResponse_shouldNotBeAffectedByJsonViews() throws Exception {
        when(userService.getUser(999L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/user/userinfo/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").value("User not found"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }
    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        UserRequest request = new UserRequest("", "valid@email.com");

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("name: must not be blank"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        UserRequest request = new UserRequest("Valid Name", "invalid-email");

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("email: must be a well-formed email address"));
    }
}
