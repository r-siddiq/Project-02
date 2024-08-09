package com.example.project02;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02.Database.DrugDAO;
import com.example.project02.Database.PharmacyDatabase;
import com.example.project02.Database.PrescriptionDAO;
import com.example.project02.Database.UserDAO;
import com.example.project02.Database.entities.Drug;
import com.example.project02.Database.entities.Prescription;
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
    private DrugDAO drugDAO;
    private PrescriptionDAO prescriptionDAO;

    @Before
    public void setUp() {
        // Create an in-memory database for testing
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        PharmacyDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDAO = db.userDAO();
        drugDAO = db.drugDAO();
        prescriptionDAO = db.prescriptionDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertAndRetrieveUser() throws InterruptedException {
        User user = new User("testUserTrue", "password");
        userDAO.insert(user);

        List<User> users = LiveDataTestUtil.getValue(userDAO.getAllUsers());
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testUserTrue", users.get(0).getUsername());
    }

    @Test
    public void updateUser() throws InterruptedException {
        User user = new User("testUserUpdate", "password");
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
    @Test
    public void insertAndRetrieveDrug() throws InterruptedException {
        Drug drug = new Drug("Aspirin");
        drugDAO.insert(drug);

        Drug insertedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNotNull(insertedDrug);
        assertEquals("Aspirin", insertedDrug.getName());
    }

    @Test
    public void updateDrug() throws InterruptedException {
        Drug drug = new Drug("Citalopram");
        drugDAO.insert(drug);

        // Update the drug name
        drug.setName("Ibuprofen");
        drugDAO.update(drug);

        Drug updatedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Ibuprofen"));
        assertNotNull(updatedDrug);
        assertEquals("Ibuprofen", updatedDrug.getName());
    }

    @Test
    public void deleteDrug() throws InterruptedException {
        Drug drug = new Drug("Aspirin");
        drugDAO.insert(drug);

        drugDAO.delete(drug);

        Drug deletedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNull(deletedDrug);
    }

    // PrescriptionDAO Tests

    @Test
    public void insertAndRetrievePrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testuser", 1);
        prescriptionDAO.insert(prescription);

        Prescription insertedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        assertNotNull(insertedPrescription);
        assertEquals("Ibuprofen", insertedPrescription.getDrugName());

    }

    @Test
    public void updatePrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testuser", 1);
        prescriptionDAO.insert(prescription);

        // Update the prescription quantity
        prescription.setQuantity(10);
        prescriptionDAO.update(prescription);

        Prescription updatedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        assertNotNull(updatedPrescription);
        assertEquals(10, updatedPrescription.getQuantity());
    }

    @Test
    public void deletePrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testuser", 1);
        prescriptionDAO.insert(prescription);

        prescriptionDAO.delete(prescription);

        List<Prescription> prescriptions = LiveDataTestUtil.getValue(prescriptionDAO.getAllPrescriptions());
        assertFalse(prescriptions.contains(prescription));
    }
}
