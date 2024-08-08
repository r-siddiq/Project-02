package com.example.project02.Database.typeConverters;

import androidx.room.TypeConverter;

import com.example.project02.Database.entities.Pharmacy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public long convertDateToLong(LocalDateTime date) {
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    @TypeConverter
    public LocalDateTime convertLongToDate(Long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant,ZoneId.systemDefault());

    }
    @TypeConverter
    public static String fromDrugCostList(List<Pharmacy.DrugCost> drugCosts) {
        if (drugCosts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Pharmacy.DrugCost drugCost : drugCosts) {
            sb.append(drugCost.getDrugName()).append(":").append(drugCost.getCost()).append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<Pharmacy.DrugCost> toDrugCostList(String drugCostsString) {
        if (drugCostsString == null) {
            return null;
        }
        List<Pharmacy.DrugCost> drugCosts = new ArrayList<>();
        String[] pairs = drugCostsString.split(",");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length == 2) {
                String drugName = parts[0];
                double cost = Double.parseDouble(parts[1]);
                drugCosts.add(new Pharmacy.DrugCost(drugName, cost));
            }
        }
        return drugCosts;
    }

    //TODO: Add type converter for fills
    //pharmacyID, dateFilled, and cost.
}
