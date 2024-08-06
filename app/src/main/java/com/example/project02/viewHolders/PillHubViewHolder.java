package com.example.project02.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project02.R;

public class PillHubViewHolder extends RecyclerView.ViewHolder {
    private final TextView pillHubViewItem;

    private PillHubViewHolder(View pillHubView){
        super(pillHubView);
        pillHubViewItem = pillHubView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text){
        pillHubViewItem.setText(text);
    }

    static PillHubViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pillhub_recycler_item, parent, false);
        return new PillHubViewHolder(view);
    }
}
