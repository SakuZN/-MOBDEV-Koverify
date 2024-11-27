package com.example.koverify.product_list.drugs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.R;
import com.example.koverify.database.drugs.DrugListItem;

import java.util.ArrayList;
import java.util.List;

public class DrugProductAdapter extends RecyclerView.Adapter<DrugProductAdapter.DrugProductViewHolder> {
    private List<DrugListItem> drugProducts = new ArrayList<>(); // Initialize to avoid null checks
    private OnItemClickListener onItemClickListener;

    // Submit a new list to the adapter
    public void submitList(List<DrugListItem> drugProducts) {
        System.out.println("submitList: " + (drugProducts != null ? drugProducts.size() : "0"));
        this.drugProducts = drugProducts != null ? drugProducts : new ArrayList<>();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(DrugListItem drugItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(drugItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugProducts.size();
    }

    // ViewHolder class
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

        // Bind data to the views
        public void bind(DrugListItem drugItem) {
            brandNameTextView.setText(drugItem.getBrand_name() != null ? drugItem.getBrand_name() : "N/A");
            genericNameTextView.setText("- " + (drugItem.getGeneric_name() != null ? drugItem.getGeneric_name() : "N/A"));
            manufacturerTextView.setText(drugItem.getManufacturer() != null ? drugItem.getManufacturer() : "N/A");
            classificationTextView.setText(drugItem.getClassification() != null ? drugItem.getClassification() : "N/A");
        }
    }
}
