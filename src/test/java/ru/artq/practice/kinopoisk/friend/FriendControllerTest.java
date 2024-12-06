//package ru.artq.practice.kinopoisk.friend;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
//import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
//import ru.artq.practice.kinopoisk.model.FriendshipStatus;
//import ru.artq.practice.kinopoisk.model.User;
//import ru.artq.practice.kinopoisk.storage.FriendshipStorage;
//import ru.artq.practice.kinopoisk.storage.UserStorage;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
//@RequiredArgsConstructor
//abstract class FriendControllerTest {
//    private final UserStorage userStorage;
//    private final FriendshipStorage friendshipStorage;
//    private final MockMvc mockMvc;
//
//    @BeforeEach
//    void start() {
//        for (int i = 1; i <= 10; i++) {
//            userStorage.addUser(
//                    User.builder().email("adf@mail.ru")
//                            .login("A" + i).username("A" + i)
//                            .birthday(LocalDate.now())
//                            .build());
//        }
//    }
//
//    @AfterEach
//    void clear() {
//        friendshipStorage.clear();
//        userStorage.clear();
//    }
//
//    @Test
//    void test1_sendWrongFriendsRequest() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 99))
//                .andExpectAll(
//                        MockMvcResultMatchers.status().isNotFound(),
//                        result -> assertInstanceOf(UserNotExistException.class, result.getResolvedException(), "No user")
//                );
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 99, 2))
//                .andExpectAll(
//                        status().isNotFound(),
//                        result -> assertInstanceOf(UserNotExistException.class, result.getResolvedException(), "No user")
//                );
//    }
//
//    @Test
//    void test2_sendFriendsRequest() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2))
//                .andExpectAll(status().isOk());
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "дубль"));
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 2, 1))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "no friend"));
//    }
//
//    @Test
//    void test3_acceptFriendsRequest() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2))
//                .andExpect(status().isOk());
//        assertEquals(FriendshipStatus.ACCEPTED, friendshipStorage.findFriendship(1, 2).getStatus());
//
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 2, 1))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "empty"));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 5))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "empty"));
//    }
//
//    @Test
//    void test4_rejectFriendsRequest() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 1, 2))
//                .andExpect(status().isOk());
//        assertEquals(FriendshipStatus.REJECTED, friendshipStorage.findFriendship(1, 2).getStatus());
//
//        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 2, 1))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "empty"));
//        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 1, 5))
//                .andExpectAll(status().isConflict(),
//                        result -> assertInstanceOf(FriendshipException.class, result.getResolvedException(), "empty"));
//    }
//
//
//    @Test
//    void test5_getFriends() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 3));
//        mockMvc.perform(get("/users/{id}/friends", 1))
//                .andExpectAll(status().isOk(), jsonPath("$").isEmpty());
//        mockMvc.perform(get("/users/{id}/friends", 2))
//                .andExpectAll(status().isOk(), jsonPath("$").isEmpty());
//        mockMvc.perform(get("/users/{id}/friends", 99))
//                .andExpectAll(status().isNotFound(),
//                        result -> assertInstanceOf(UserNotExistException.class, result.getResolvedException(), "no user")
//                );
//
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(delete("/users/{id}/friends/{friendId}", 1, 3));
//        mockMvc.perform(get("/users/{id}/friends", 1))
//                .andExpectAll(status().isOk(), jsonPath("$[0].id").value(2));
//        mockMvc.perform(get("/users/{id}/friends", 2))
//                .andExpectAll(status().isOk(), jsonPath("$[0].id").value(1));
//        mockMvc.perform(get("/users/{id}/friends", 3))
//                .andExpectAll(status().isOk(), jsonPath("$").isEmpty());
//    }
//
//    @Test
//    void test6_getCommonFriends() throws Exception {
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 3));
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 1, 4));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 2));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 3));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 4));
//
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 2, 3));
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 2, 5));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 2, 3));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 2, 5));
//
//        mockMvc.perform(post("/users/{id}/friends/{friendId}", 3, 5));
//        mockMvc.perform(put("/users/{id}/friends/{friendId}", 3, 5));
//
//        mockMvc.perform(get("/users/{id}/friends/{otherId}/mutual/", 1, 6))
//                .andExpectAll(status().isOk(), jsonPath("$").isEmpty());
//
//        mockMvc.perform(get("/users/{id}/friends/{otherId}/mutual/", 1, 2))
//                .andExpectAll(status().isOk(), jsonPath("$[0].id").value(3));
//        mockMvc.perform(get("/users/{id}/friends/{otherId}/mutual/", 2, 1))
//                .andExpectAll(status().isOk(), jsonPath("$[0].id").value(3));
//
//        mockMvc.perform(get("/users/{id}/friends/{otherId}/mutual/", 2, 3))
//                .andExpectAll(status().isOk(),
//                        jsonPath("$.length()").value(2),
//                        jsonPath("$[0].id").value(1),
//                        jsonPath("$[1].id").value(5)
//                );
//    }
//}
