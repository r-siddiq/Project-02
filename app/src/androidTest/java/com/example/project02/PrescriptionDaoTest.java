package com.example.project02;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02.Database.PharmacyDatabase;
import com.example.project02.Database.PrescriptionDAO;
import com.example.project02.Database.entities.Prescription;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PrescriptionDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PharmacyDatabase db;
    private PrescriptionDAO prescriptionDAO;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        PharmacyDatabase.class)
                .allowMainThreadQueries()
                .build();
        prescriptionDAO = db.prescriptionDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertPrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testUser", 2);
        prescriptionDAO.insert(prescription);

        Prescription retrievedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        assertNotNull(retrievedPrescription);
        assertEquals("Ibuprofen", retrievedPrescription.getDrugName());
    }

    @Test
    public void testUpdatePrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testUser", 2);
        prescriptionDAO.insert(prescription);
        Prescription insertedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        insertedPrescription.setQuantity(10);
        prescriptionDAO.update(insertedPrescription);

        Prescription updatedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        assertNotNull(updatedPrescription);
        assertEquals(10, updatedPrescription.getQuantity());
    }

    @Test
    public void testDeletePrescription() throws InterruptedException {
        Prescription prescription = new Prescription("Ibuprofen", 5, "testUser", 2);
        prescriptionDAO.insert(prescription);
        Prescription insertedPrescription = LiveDataTestUtil.getValue(prescriptionDAO.getPrescriptionByDrugName("Ibuprofen"));
        prescriptionDAO.delete(insertedPrescription);

        List<Prescription> prescriptions = LiveDataTestUtil.getValue(prescriptionDAO.getAllPrescriptions());
        assertFalse(prescriptions.contains(insertedPrescription));
    }

    @Test
    public void testGetAllPrescriptions() throws InterruptedException {
        Prescription prescription1 = new Prescription("Ibuprofen", 5, "testUser1", 2);
        Prescription prescription2 = new Prescription("Aspirin", 10, "testUser2", 1);
        prescriptionDAO.insert(prescription1);
        prescriptionDAO.insert(prescription2);

        List<Prescription> prescriptions = LiveDataTestUtil.getValue(prescriptionDAO.getAllPrescriptions());
        assertNotNull(prescriptions);
        assertEquals(2, prescriptions.size());
        assertTrue(prescriptions.stream().anyMatch(prescription -> prescription.getDrugName().equals("Ibuprofen")));
        assertTrue(prescriptions.stream().anyMatch(prescription -> prescription.getDrugName().equals("Aspirin")));
    }
}
