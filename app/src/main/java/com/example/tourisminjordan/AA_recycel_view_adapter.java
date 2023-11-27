package com.example.tourisminjordan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AA_recycel_view_adapter extends RecyclerView.Adapter<AA_recycel_view_adapter.MyViewHolder> {
    private final Recycler_view_interface_provinces recycler_view_interface_provinces;
    Context context;
    ArrayList<AminoAcidModel> aminoAcidModels;
    public AA_recycel_view_adapter(Context context, ArrayList<AminoAcidModel> aminoAcidModels,
                                   Recycler_view_interface_provinces recycler_view_interface_provinces){
        this.context = context;
        this.aminoAcidModels = aminoAcidModels;
        this.recycler_view_interface_provinces = recycler_view_interface_provinces;
    }
    @NonNull
    @Override
    public AA_recycel_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent , false);
        return new AA_recycel_view_adapter.MyViewHolder(view , recycler_view_interface_provinces);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_recycel_view_adapter.MyViewHolder holder, int position) {
        holder.tvName.setText(aminoAcidModels.get(position).getCityName());
        holder.imageView.setImageResource(aminoAcidModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return aminoAcidModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvName;
        public MyViewHolder(@NonNull View itemView , Recycler_view_interface_provinces recycler_view_interface_provinces) {
            super(itemView);
            imageView = itemView.findViewById(R.id.background_image_card);
            tvName = itemView.findViewById(R.id.city_name);
            itemView.setOnClickListener(v -> {
             if (recycler_view_interface_provinces != null){
                 int pos = getAdapterPosition();

                 if (pos != RecyclerView.NO_POSITION){
                     recycler_view_interface_provinces.onItemClick(pos);
                 }
             }
            });

        }
    }
}
