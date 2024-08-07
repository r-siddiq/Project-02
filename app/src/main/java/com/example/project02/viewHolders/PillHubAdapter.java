package com.example.project02.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.project02.Database.entities.Patient;

public class PillHubAdapter extends ListAdapter<Patient, PillHubViewHolder> {
    public PillHubAdapter(@NonNull DiffUtil.ItemCallback<Patient> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public PillHubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return PillHubViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PillHubViewHolder holder, int position) {
        Patient current = getItem(position);
        holder.bind(current.toString());
    }

    public static class PatientDiff extends DiffUtil.ItemCallback<Patient>{
        @Override
        public boolean areItemsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
            return oldItem.equals(newItem);
        }
    }
}
