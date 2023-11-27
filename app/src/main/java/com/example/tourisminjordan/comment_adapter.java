package com.example.tourisminjordan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class comment_adapter extends ArrayAdapter<comment> {
    TextView tvName,tvComment,tvTime;
    ArrayList<comment> commentArrayList;
    private final Context mContext;
    private final int mResource;


    @Override
    public comment getItem(int position) {
        return commentArrayList.get(position);
    }
    public comment_adapter(Context context, ArrayList<comment> comments, int resource) {
        super(context, 0, comments);
        mContext = context;
        mResource = resource;
        commentArrayList = comments;
    }
    

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }
        tvName = convertView.findViewById(R.id.userName_comment);
        tvComment = convertView.findViewById(R.id.text_comment);
        tvTime = convertView.findViewById(R.id.time_comment);
        comment comment1 = getItem(position);
        tvName.setText(comment1.getUser_name());
        tvComment.setText(comment1.getComment());
        tvTime.setText(comment1.getTime());
        return convertView;
    }


}
