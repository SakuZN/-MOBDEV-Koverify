package com.example.koverify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.koverify.DashboardItem;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<DashboardItem> dashboardItems;
    private Context context;

    public DashboardAdapter(Context context, List<DashboardItem> dashboardItems) {
        this.context = context;
        this.dashboardItems = dashboardItems;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dashboard_button, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        DashboardItem item = dashboardItems.get(position);
        holder.buttonText.setText(item.getTitle());
        holder.buttonImage.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, item.getTargetActivity());
            if (item.getExtraKey() != null && item.getExtraValue() != null) {
                intent.putExtra(item.getExtraKey(), item.getExtraValue());
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dashboardItems.size();
    }

    public static class DashboardViewHolder extends RecyclerView.ViewHolder {
        ImageView buttonImage;
        TextView buttonText;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonImage = itemView.findViewById(R.id.buttonImage);
            buttonText = itemView.findViewById(R.id.buttonText);
        }
    }
}
