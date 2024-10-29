package com.example.koverify.product_list.food;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.R;
import com.example.koverify.database.foods.FoodProduct;

import java.util.ArrayList;
import java.util.List;
public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.FoodProductViewHolder> {
    private List<FoodProduct> foodProducts = new ArrayList<>(); // Initialize to avoid null checks
    private FoodProductAdapter.OnItemClickListener onItemClickListener;

    // Submit a new list to the adapter
    public void submitList(List<FoodProduct> foodProducts) {
        System.out.println("submitList: " + (foodProducts != null ? foodProducts.size() : "0"));
        this.foodProducts = foodProducts != null ? foodProducts : new ArrayList<>();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(FoodProduct foodProduct);
    }

    public void setOnItemClickListener(FoodProductAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public FoodProductAdapter.FoodProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_product, parent, false);
        return new FoodProductAdapter.FoodProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodProductAdapter.FoodProductViewHolder holder, int position) {
        FoodProduct foodItem = foodProducts.get(position);
        holder.bind(foodItem);

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(foodItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodProducts.size();
    }

    // ViewHolder class
    static class FoodProductViewHolder extends RecyclerView.ViewHolder {
        TextView brandNameTextView;
        TextView productNameTextView;
        TextView companyNameTextView;
        TextView productTypeTextView;

        public FoodProductViewHolder(@NonNull View itemView) {
            super(itemView);
            brandNameTextView = itemView.findViewById(R.id.brandNameTextView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            companyNameTextView = itemView.findViewById(R.id.companyTextValue);
            productTypeTextView = itemView.findViewById(R.id.productTypeTextValue);
        }

        // Bind data to the views
        public void bind(FoodProduct foodProduct) {
            brandNameTextView.setText(foodProduct.getBrand_name() != null ? foodProduct.getBrand_name() : "N/A");
            productNameTextView.setText("- " + (foodProduct.getProduct_name() != null ? foodProduct.getProduct_name() : "N/A"));
            companyNameTextView.setText(foodProduct.getCompany_name() != null ? foodProduct.getCompany_name() : "N/A");
            productTypeTextView.setText(foodProduct.getProduct_type() != null ? foodProduct.getProduct_type() : "N/A");
        }
    }
}
