package fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maprealtime.R;

import java.util.List;

import models.Terreno;

public class TerrenosAdapter extends RecyclerView.Adapter<TerrenosAdapter.ViewHolder> {

    private List<Terreno> terrenosList;
    private OnItemClickListener onItemClickListener;

    public TerrenosAdapter(List<Terreno> terrenosList, OnItemClickListener onItemClickListener) {
        this.terrenosList = terrenosList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_terreno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Terreno terreno = terrenosList.get(position);
        holder.nameTextView.setText(terreno.getNombre());
        holder.detailsTextView.setText(terreno.getDetalles());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(terreno));
    }

    @Override
    public int getItemCount() {
        return terrenosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView detailsTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Terreno terreno);
    }
}