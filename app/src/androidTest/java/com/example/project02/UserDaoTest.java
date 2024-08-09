package com.example.project02;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02.Database.PharmacyDatabase;
import com.example.project02.Database.UserDAO;
import com.example.project02.Database.entities.User;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PharmacyDatabase db;
    private UserDAO userDAO;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        PharmacyDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDAO = db.userDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertUser() throws InterruptedException {
        User user = new User("testUser", "password");
        userDAO.insert(user);

        User retrievedUser = LiveDataTestUtil.getValue(userDAO.getUserByUsername("testUser"));
        assertNotNull(retrievedUser);
        assertEquals("testUser", retrievedUser.getUsername());
    }

    @Test
    public void testUpdateUser() throws InterruptedException {
        User user = new User("testUserUpdate", "password");
        userDAO.insert(user);

        User insertedUser = LiveDataTestUtil.getValue(userDAO.getUserByUsername("testUserUpdate"));
        // Update the user's password
        insertedUser.setPassword("newPassword");
        userDAO.update(insertedUser);

        User updatedUser = LiveDataTestUtil.getValue(userDAO.getUserByUsername("testUserUpdate"));
        assertNotNull(updatedUser);
        assertEquals("newPassword", updatedUser.getPassword());
    }

    @Test
    public void testDeleteUser() throws InterruptedException {
        User user = new User("testUserRemove", "password");
        userDAO.insert(user);

        userDAO.delete(user);

        List<User> users = LiveDataTestUtil.getValue(userDAO.getAllUsers());
        assertFalse(users.contains(user));
    }

    @Test
    public void testGetAllUsers() throws InterruptedException {
        User user1 = new User("testUser1", "password1");
        User user2 = new User("testUser2", "password2");
        userDAO.insert(user1);
        userDAO.insert(user2);

        List<User> users = LiveDataTestUtil.getValue(userDAO.getAllUsers());
        assertNotNull(users);
        assertEquals(2, users.size());
    }
}
