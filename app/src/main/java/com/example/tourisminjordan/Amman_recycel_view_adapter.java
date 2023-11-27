package com.example.tourisminjordan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Amman_recycel_view_adapter extends RecyclerView.Adapter<Amman_recycel_view_adapter.MyViewHolder> {
    private final ammanItemClick ammanItemclick;
    Context context;
    ArrayList<AminoAcidModel> aminoAcidModelsAmman;

    public Amman_recycel_view_adapter(Context context, ArrayList<AminoAcidModel> aminoAcidModels,
                                      ammanItemClick ammanItemclick) {
        this.context = context;
        this.aminoAcidModelsAmman = aminoAcidModels;
        this.ammanItemclick = ammanItemclick;
    }

    @NonNull
    @Override
    public Amman_recycel_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new Amman_recycel_view_adapter.MyViewHolder(view, ammanItemclick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(aminoAcidModelsAmman.get(position).getThe_place_name());
        holder.tvDiscription.setText(aminoAcidModelsAmman.get(position).getAbout_the_place());
    }


    @Override
    public int getItemCount() {
       return aminoAcidModelsAmman.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvDiscription;
        public MyViewHolder(@NonNull View itemView , ammanItemClick ammanItemclick) {
            super(itemView);
            tvName = itemView.findViewById(R.id.amman_city_name);
            tvDiscription = itemView.findViewById(R.id.discription);

            itemView.setOnClickListener(v -> {
                if (ammanItemclick != null){
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        ammanItemclick.onItemClickAmman(pos);
                    }
                }
            });

        }
    }

}
