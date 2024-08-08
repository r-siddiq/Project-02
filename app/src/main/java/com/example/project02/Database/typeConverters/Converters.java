package com.example.project02.Database.typeConverters;

import androidx.room.TypeConverter;

import com.example.project02.Database.entities.Pharmacy.DrugCost;
import com.example.project02.Database.entities.Prescription.FillRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Type converters to allow Room to reference complex data types.
 */
public class Converters {

    /**
     * Converts a semicolon-separated string to a list of DrugCost objects.
     *
     * @param value the semicolon-separated string
     * @return the list of DrugCost objects
     */
    @TypeConverter
    public static List<DrugCost> fromString(String value) {
        List<DrugCost> list = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            String[] items = value.split(";");
            for (String item : items) {
                String[] fields = item.split(",");
                if (fields.length == 2) {
                    String drugName = fields[0];
                    double cost = Double.parseDouble(fields[1]);
                    list.add(new DrugCost(drugName, cost));
                }
            }
        }
        return list;
    }

    /**
     * Converts a list of DrugCost objects to a semicolon-separated string.
     *
     * @param list the list of DrugCost objects
     * @return the semicolon-separated string
     */
    @TypeConverter
    public static String fromList(List<DrugCost> list) {
        StringBuilder value = new StringBuilder();
        for (DrugCost drugCost : list) {
            if (value.length() > 0) {
                value.append(";");
            }
            value.append(drugCost.getDrugName()).append(",").append(drugCost.getCost());
        }
        return value.toString();
    }

    /**
     * Converts a semicolon-separated string to a list of FillRequest objects.
     *
     * @param value the semicolon-separated string
     * @return the list of FillRequest objects
     */
    @TypeConverter
    public static List<FillRequest> fromStringToFillRequest(String value) {
        List<FillRequest> list = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            String[] items = value.split(";");
            for (String item : items) {
                String[] fields = item.split(",");
                if (fields.length == 3) {
                    int pharmacyID = Integer.parseInt(fields[0]);
                    String dateFilled = fields[1];
                    String cost = fields[2];
                    list.add(new FillRequest(pharmacyID, dateFilled, cost));
                }
            }
        }
        return list;
    }

    /**
     * Converts a list of FillRequest objects to a semicolon-separated string.
     *
     * @param list the list of FillRequest objects
     * @return the semicolon-separated string
     */
    @TypeConverter
    public static String fromFillRequestList(List<FillRequest> list) {
        StringBuilder value = new StringBuilder();
        for (FillRequest fillRequest : list) {
            if (value.length() > 0) {
                value.append(";");
            }
            value.append(fillRequest.getPharmacyID()).append(",").append(fillRequest.getDateFilled()).append(",").append(fillRequest.getCost());
        }
        return value.toString();
    }
}