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
public class PharmacyDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PharmacyDatabase db;
    private UserDAO userDAO;

    @Before
    public void setUp() {
        // Create an in-memory database for testing
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
    public void insertAndRetrieveUser() throws InterruptedException {
        User user = new User("testuser", "password");
        userDAO.insert(user);

        List<User> users = LiveDataTestUtil.getValue(userDAO.getAllUsers());
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    public void updateUser() throws InterruptedException {
        User user = new User("testuser", "password");
        userDAO.insert(user);

        // Update the user's password
        user.setPassword("newpassword");
        userDAO.update(user);
        assertNotNull(user);
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void deleteUser() throws InterruptedException {
        User user = new User("testUserFail", "password");
        userDAO.insert(user);
        assertNotNull(user);

        userDAO.delete(user);

        List<User> users = LiveDataTestUtil.getValue(userDAO.getAllUsers());
        assertNotNull(users);
        assertFalse(users.contains(user));

    }
}
