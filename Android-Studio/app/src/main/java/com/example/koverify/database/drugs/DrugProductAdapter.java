package com.example.koverify.database.drugs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.R;

import java.util.List;

public class DrugProductAdapter extends RecyclerView.Adapter<DrugProductAdapter.DrugProductViewHolder> {
    private List<DrugListItem> drugProducts;

    public void submitList(List<DrugListItem> drugProducts) {
        this.drugProducts = drugProducts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DrugProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug_product, parent, false);
        return new DrugProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugProductViewHolder holder, int position) {
        DrugListItem drugItem = drugProducts.get(position);
        holder.bind(drugItem);
    }

    @Override
    public int getItemCount() {
        return drugProducts != null ? drugProducts.size() : 0;
    }

    static class DrugProductViewHolder extends RecyclerView.ViewHolder {
        TextView brandNameTextView;
        TextView genericNameTextView;
        TextView manufacturerTextView;
        TextView classificationTextView;

        public DrugProductViewHolder(@NonNull View itemView) {
            super(itemView);
            brandNameTextView = itemView.findViewById(R.id.textView3); // Brand Name
            genericNameTextView = itemView.findViewById(R.id.textView); // Generic Name
            manufacturerTextView = itemView.findViewById(R.id.textView6); // Manufacturer
            classificationTextView = itemView.findViewById(R.id.textView8); // Classification
        }

        public void bind(DrugListItem drugItem) {
            brandNameTextView.setText(drugItem.getBrand_name() != null ? drugItem.getBrand_name() : "N/A");
            genericNameTextView.setText("- " + (drugItem.getGeneric_name() != null ? drugItem.getGeneric_name() : "N/A"));
            manufacturerTextView.setText(drugItem.getManufacturer() != null ? drugItem.getManufacturer() : "N/A");

            // Get classification based on drug_type
            String classification = drugItem.getClassification();
            classificationTextView.setText(classification != null ? classification : "N/A");
        }
    }
}
