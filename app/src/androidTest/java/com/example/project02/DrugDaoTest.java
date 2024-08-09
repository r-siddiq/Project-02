package com.example.project02;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project02.Database.PharmacyDatabase;
import com.example.project02.Database.DrugDAO;
import com.example.project02.Database.entities.Drug;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DrugDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PharmacyDatabase db;
    private DrugDAO drugDAO;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        PharmacyDatabase.class)
                .allowMainThreadQueries()
                .build();
        drugDAO = db.drugDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertDrug() throws InterruptedException {
        Drug drug = new Drug("Aspirin");
        drugDAO.insert(drug);

        Drug retrievedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNotNull(retrievedDrug);
        assertEquals("Aspirin", retrievedDrug.getName());
    }

    @Test
    public void testUpdateDrug() throws InterruptedException {
        Drug drug = new Drug("Aspirin");
        drugDAO.insert(drug);

        Drug insertedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNotNull(insertedDrug);
        insertedDrug.setName("Ibuprofen");
        drugDAO.update(insertedDrug);

        Drug updatedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Ibuprofen"));
        assertNotNull(updatedDrug);
        assertEquals("Ibuprofen", updatedDrug.getName());
    }

    @Test
    public void testDeleteDrug() throws InterruptedException {
        Drug drug = new Drug("Aspirin");
        drugDAO.insert(drug);

        Drug insertedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNotNull(insertedDrug);

        drugDAO.delete(insertedDrug);

        Drug deletedDrug = LiveDataTestUtil.getValue(drugDAO.getDrugByName("Aspirin"));
        assertNull(deletedDrug);
    }

    @Test
    public void testGetAllDrugs() throws InterruptedException {
        Drug drug1 = new Drug("Aspirin");
        Drug drug2 = new Drug("Citalopram");
        drugDAO.insert(drug1);
        drugDAO.insert(drug2);

        List<Drug> drugs = LiveDataTestUtil.getValue(drugDAO.getAllDrugs());
        assertNotNull(drugs);
        assertEquals(2, drugs.size());
        assertTrue(drugs.stream().anyMatch(drug -> drug.getName().equals("Aspirin")));
        assertTrue(drugs.stream().anyMatch(drug -> drug.getName().equals("Citalopram")));
    }
}
