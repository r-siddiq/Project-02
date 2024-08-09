package com.example.project02;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.project02.Database.entities.User;

public class UserTest {

    private User user1;
    private User user2;

    @Before
    public void setUp() {
        user1 = new User("testUser1", "password1");
        user2 = new User("testUser2", "password2");
    }

    @Test
    public void testGettersAndSetters() {
        user1.setId(1);
        user1.setUsername("newUsername");
        user1.setPassword("newPassword");
        user1.setAdmin(true);

        assertEquals(1, user1.getId());
        assertEquals("newUsername", user1.getUsername());
        assertEquals("newPassword", user1.getPassword());
        assertTrue(user1.isAdmin());
    }

    @Test
    public void testEqualsAndHashCode() {
        User userA = new User("testUser1", "password1");
        User userB = new User("testUser1", "password1");

        // Ensure two users with the same details are considered equal
        assertTrue(userA.equals(userB));
        assertEquals(userA.hashCode(), userB.hashCode());

        // Change one property and ensure they are not equal
        userB.setUsername("differentUser");
        assertFalse(userA.equals(userB));
        assertNotEquals(userA.hashCode(), userB.hashCode());
    }

    @Test
    public void testConstructor() {
        User user = new User("testUser", "password");

        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertFalse(user.isAdmin()); // Default value should be false
    }
}