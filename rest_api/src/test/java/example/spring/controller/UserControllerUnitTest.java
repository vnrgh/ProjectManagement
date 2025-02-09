//package example.spring.controller;
//
//import example.spring.model.dto.UserResponseDTO;
//import example.spring.security.jwt.TokenProvider;
//import example.spring.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@MockBean(TokenProvider.class)
//@AutoConfigureMockMvc(addFilters = false)  // disabling spring security
//public class UserControllerUnitTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void getUserByIdTest() throws Exception {
//        Long userId = 1L;
//        UserResponseDTO responseDTO = new UserResponseDTO(userId, "John", "test@example.com", null);
//
//        Mockito.when(userService.getUserById(userId)).thenReturn(responseDTO);
//
//        mockMvc.perform(get("/api/user/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(userId))
//                .andExpect(jsonPath("$.username").value("John"))
//                .andExpect(jsonPath("$.email").value("test@example.com"));
//
//        Mockito.verify(userService).getUserById(userId);
//    }
//
//    @Test
//    void getAllUsersTest() throws Exception{
//
//        List<UserResponseDTO> users = List.of(
//                new UserResponseDTO(1L, "user1", "user1@example.com", null),
//                new UserResponseDTO(2L, "user2", "user2@example.com", null));
//
//        Mockito.when(userService.getAllUsers()).thenReturn(users);
//
//        mockMvc.perform(get("/api/user"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].username").value("user1"))
//                .andExpect(jsonPath("$[1].username").value("user2"));
//
//        Mockito.verify(userService).getAllUsers();
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
