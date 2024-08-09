package com.example.project02;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.project02.Database.entities.Prescription;

public class PrescriptionTest {

    private Prescription prescription1;
    private Prescription prescription2;

    @Before
    public void setUp() {
        // Create test instances
        prescription1 = new Prescription("Aspirin", 30, "john_doe", 2);
        prescription2 = new Prescription("Aspirin", 30, "john_doe", 2);
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(prescription1.equals(prescription1));
    }

    @Test
    public void testEqualsDifferentObjectSameValues() {
        assertTrue(prescription1.equals(prescription2));
    }

    @Test
    public void testEqualsDifferentValues() {
        prescription2.setQuantity(35); // Change one value
        assertFalse(prescription1.equals(prescription2));
    }

    @Test
    public void testHashCode() {
        assertEquals(prescription1.hashCode(), prescription2.hashCode());
    }

    @Test
    public void testHashCodeDifferentValues() {
        prescription2.setQuantity(35); // Change one value
        assertNotEquals(prescription1.hashCode(), prescription2.hashCode());
    }

    @Test
    public void testToString() {
        // Set up the expected string according to the new format
        String expectedString = "Prescription for Aspirin:\n" +
                "Quantity: 30, Refills: 2";

        // Assert that the actual toString output matches the expected string
        assertEquals(expectedString, prescription1.toString());
    }
}