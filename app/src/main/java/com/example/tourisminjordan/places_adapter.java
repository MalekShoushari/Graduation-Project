package com.example.tourisminjordan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class places_adapter extends ArrayAdapter<AminoAcidModel> {
    private final Context mContext;
    private final int mResource;
    private final ArrayList<AminoAcidModel> aminoAcidModelsList;

    public places_adapter(Context context, ArrayList<AminoAcidModel> aminoAcidModels, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        aminoAcidModelsList = aminoAcidModels;

    }

    @Override
    public int getCount() {
        return aminoAcidModelsList.size();
    }

    @Override
    public AminoAcidModel getItem(int position) {
        return aminoAcidModelsList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvCityName = convertView.findViewById(R.id.amman_city_name);
            viewHolder.tvAboutPlaces = convertView.findViewById(R.id.discription);
            viewHolder.placeImage = convertView.findViewById(R.id.background_image_card_city);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AminoAcidModel aminoAcidModel = getItem(position);

        viewHolder.tvCityName.setText(aminoAcidModel.getThe_place_name());
        viewHolder.tvAboutPlaces.setText(aminoAcidModel.getAbout_the_place());

        if (aminoAcidModel.getmImageUri() != null) {
            viewHolder.placeImage.setImageDrawable(aminoAcidModel.getmImageUri());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvCityName;
        TextView tvAboutPlaces;
        ImageView placeImage;
    }

}
