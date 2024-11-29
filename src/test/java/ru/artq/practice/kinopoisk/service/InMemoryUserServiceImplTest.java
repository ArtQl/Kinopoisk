package ru.artq.practice.kinopoisk.service;

public class InMemoryUserServiceImplTest {
//    UserServiceImpl userServiceImpl;
//
//    @BeforeEach
//    void start() {
//        userServiceImpl = new UserServiceImpl(new InMemoryUserStorage());
//        for (int i = 1; i <= 10; i++) {
//            userServiceImpl.getUserStorage().addUser(User.builder().email("adf@mail.ru").login("a" + i).birthday(LocalDate.now()).build());
//        }
//    }
//
//    @Test
//    void sendFriendsRequest() {
//        assertThrows(UserNotExistException.class, () -> userServiceImpl.sendFriendRequest(1, 99), "No that user");
//        assertThrows(UserNotExistException.class, () -> userServiceImpl.sendFriendRequest(88, 7), "No that user");
//
//        assertTrue(userServiceImpl.sendFriendRequest(1, 2));
//        assertNotNull(userServiceImpl.getFriendshipStorage().findFriendship(1, 2), "add friend 1 to 2");
//        assertThrows(FriendshipException.class, () -> userServiceImpl.getFriendshipStorage().findFriendship(2, 1), "friendship not found");
//        assertThrows(FriendshipException.class, () -> userServiceImpl.getFriendshipStorage().findFriendship(1, 99));
//
//        assertFalse(userServiceImpl.sendFriendRequest(1, 2));
//        assertFalse(userServiceImpl.sendFriendRequest(2, 1));
//    }
//
//    @Test
//    void rejectFriendsRequest() {
//        userServiceImpl.sendFriendRequest(1, 2);
////        assertTrue(userService.rejectFriendRequest(1, 2));
//        assertEquals(FriendshipStatus.REJECTED, userServiceImpl.getFriendshipStorage().findFriendship(1,2).getStatus());
//        assertThrows(FriendshipException.class, () -> userServiceImpl.rejectFriendRequest(1, 5), "Empty");
//        assertThrows(FriendshipException.class, () -> userServiceImpl.rejectFriendRequest(2, 1), "Empty");
//    }
//
//    @Test
//    void acceptFriendsRequest() {
//        userServiceImpl.sendFriendRequest(1, 2);
////        assertTrue(userService.acceptFriendRequest(1, 2));
//        assertEquals(FriendshipStatus.ACCEPTED, userServiceImpl.getFriendshipStorage().findFriendship(1,2).getStatus());
//        assertThrows(FriendshipException.class, () -> userServiceImpl.acceptFriendRequest(1, 5), "Empty");
//        assertThrows(FriendshipException.class, () -> userServiceImpl.acceptFriendRequest(2, 1), "Empty");
//    }
//
//    @Test
//    void getListFriends() {
//        userServiceImpl.sendFriendRequest(1, 2);
//        userServiceImpl.sendFriendRequest(1, 3);
//
//        assertTrue(userServiceImpl.getListFriends(1).isEmpty());
//        assertTrue(userServiceImpl.getListFriends(2).isEmpty());
//        assertThrows(UserNotExistException.class, () -> userServiceImpl.getListFriends(99), "No user");
//
//        userServiceImpl.acceptFriendRequest(1, 2);
//        userServiceImpl.rejectFriendRequest(1, 3);
//
//        assertEquals(1, userServiceImpl.getListFriends(1).size(), "accept");
//        assertEquals(1, userServiceImpl.getListFriends(2).size(), "accept");
//        assertEquals(0, userServiceImpl.getListFriends(3).size(), "reject");
//    }
//
//    @Test
//    void getCommonFriends() {
//        userServiceImpl.sendFriendRequest(1,2);
//        userServiceImpl.sendFriendRequest(1,3);
//        userServiceImpl.sendFriendRequest(1,4);
//        userServiceImpl.acceptFriendRequest(1, 2);
//        userServiceImpl.acceptFriendRequest(1, 3);
//        userServiceImpl.acceptFriendRequest(1, 4);
//
//        userServiceImpl.sendFriendRequest(2, 3);
//        userServiceImpl.sendFriendRequest(2, 5);
//        userServiceImpl.acceptFriendRequest(2,3);
//        userServiceImpl.acceptFriendRequest(2,5);
//
//        userServiceImpl.sendFriendRequest(3, 5);
//        userServiceImpl.acceptFriendRequest(3, 5);
//
//        assertTrue(userServiceImpl.getCommonFriends(1,6).isEmpty());
//
//        assertEquals(userServiceImpl.getUserStorage().getUser(3), userServiceImpl.getCommonFriends(1,2).getFirst());
//        assertEquals(userServiceImpl.getUserStorage().getUser(3), userServiceImpl.getCommonFriends(2,1).getFirst());
//
//        assertEquals(2, userServiceImpl.getCommonFriends(2,3).size());
//    }
}
