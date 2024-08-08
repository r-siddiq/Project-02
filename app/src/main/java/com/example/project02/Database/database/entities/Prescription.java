package com.example.project02.Database.database.entities;

import static com.example.project02.Database.database.PharmacyDatabase.PRESCRIPTION_TABLE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.project02.Database.database.typeConverters.Converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = PRESCRIPTION_TABLE)
@TypeConverters({Converters.class})
public class Prescription {

    @PrimaryKey(autoGenerate = true)
    private int rxid;
    private String drugName;
    private int quantity;
    private int patientId;
    private int doctorId;
    private String dateCreated;
    private int refills;
    private List<FillRequest> fills = new ArrayList<>();

    /**
     * Represents a fill request for a prescription.
     */
    public static class FillRequest {
        private int pharmacyID;
        private String dateFilled;
        private String cost;

        /**
         * Constructs a new FillRequest with the specified details.
         * @param pharmacyID the ID of the pharmacy that filled the prescription
         * @param dateFilled the date the prescription was filled
         * @param cost the cost of the prescription
         */
        public FillRequest(int pharmacyID, String dateFilled, String cost) {
            this.pharmacyID = pharmacyID;
            this.dateFilled = dateFilled;
            this.cost = cost;
        }

        public int getPharmacyID() {
            return pharmacyID;
        }

        public void setPharmacyID(int pharmacyID) {
            this.pharmacyID = pharmacyID;
        }

        public String getDateFilled() {
            return dateFilled;
        }

        public void setDateFilled(String dateFilled) {
            this.dateFilled = dateFilled;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "PrescriptionFill [pharmacyID=" + pharmacyID + ", dateFilled=" + dateFilled + ", cost=" + cost + "]";
        }
    }

    /**
     * Constructs a new Prescription with the specified details.
     * @param drugName    the name of the drug prescribed
     * @param quantity    the quantity of the drug prescribed
     * @param patientId   the ID of the patient receiving the prescription
     * @param doctorId    the ID of the doctor issuing the prescription
     * @param dateCreated the date the prescription was created
     * @param refills     the number of refills available
     * @param fills       the list of fill requests for this prescription
     */
    public Prescription(String drugName, int quantity, int patientId, int doctorId, String dateCreated, int refills, List<FillRequest> fills) {
        this.drugName = drugName;
        this.quantity = quantity;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateCreated = dateCreated;
        this.refills = refills;
        this.fills = fills;
    }

    public Prescription() {
        this.dateCreated = getCurrentDate();
        this.fills = new ArrayList<>();
    }

    public int getRxid() {
        return rxid;
    }

    public void setRxid(int rxid) {
        this.rxid = rxid;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getRefills() {
        return refills;
    }

    public void setRefills(int refills) {
        this.refills = refills;
    }

    public List<FillRequest> getFills() {
        return fills;
    }

    public void setFills(List<FillRequest> fills) {
        this.fills = fills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return rxid == that.rxid &&
                quantity == that.quantity &&
                patientId == that.patientId &&
                doctorId == that.doctorId &&
                refills == that.refills &&
                Objects.equals(drugName, that.drugName) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(fills, that.fills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rxid, drugName, quantity, patientId, doctorId, dateCreated, refills, fills);
    }

    @Override
    public String toString() {
        return "PrescriptionData [rxid=" + rxid + ", drugName=" + drugName + ", quantity=" + quantity + ", patientId="
                + patientId + ", doctorId=" + doctorId + ", dateCreated=" + dateCreated + ", refills=" + refills
                + ", fills=" + fills + "]";
    }

    /**
     * Gets the current date in the format yyyy-MM-dd.
     * @return the current date as a string
     */
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}