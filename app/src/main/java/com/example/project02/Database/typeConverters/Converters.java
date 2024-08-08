package com.example.project02.Database.typeConverters;

import androidx.room.TypeConverter;

import com.example.project02.Database.entities.Pharmacy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    public String fromDrugCostList(List<Pharmacy.DrugCost> drugCosts) {
        if (drugCosts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Pharmacy.DrugCost drugCost : drugCosts) {
            sb.append(drugCost.getDrugName())
                    .append(",")
                    .append(drugCost.getCost())
                    .append(";");
        }
        return sb.toString();
    }

    @TypeConverter
    public List<Pharmacy.DrugCost> toDrugCostList(String data) {
        List<Pharmacy.DrugCost> drugCosts = new ArrayList<>();
        if (data == null || data.isEmpty()) {
            return drugCosts;
        }
        StringTokenizer tokenizer = new StringTokenizer(data, ";");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] parts = token.split(",");
            if (parts.length == 2) {
                String drugName = parts[0];
                double cost;
                try {
                    cost = Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    continue; // Skip invalid cost values
                }
                drugCosts.add(new Pharmacy.DrugCost(drugName, cost));
            }
        }
        return drugCosts;
    }
}
