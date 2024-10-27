package com.example.koverify.product_list.drugs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.R;
import com.example.koverify.database.drugs.DrugListItem;

import java.util.List;

public class DrugProductAdapter extends RecyclerView.Adapter<DrugProductAdapter.DrugProductViewHolder> {
    private static List<DrugListItem> drugProducts;
    private OnItemClickListener onItemClickListener;

    public void submitList(List<DrugListItem> drugProducts) {
        this.drugProducts = drugProducts;
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
        return new DrugProductViewHolder(v, onItemClickListener);
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

        public DrugProductViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            brandNameTextView = itemView.findViewById(R.id.textView3); // Brand Name
            genericNameTextView = itemView.findViewById(R.id.textView); // Generic Name
            manufacturerTextView = itemView.findViewById(R.id.textView6); // Manufacturer
            classificationTextView = itemView.findViewById(R.id.textView8); // Classification

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    DrugListItem clickedItem = drugProducts.get(position);
                    listener.onItemClick(clickedItem);
                }
            });
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
