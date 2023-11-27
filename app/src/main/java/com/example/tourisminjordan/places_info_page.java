package com.example.tourisminjordan;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class places_info_page extends AppCompatActivity {
    TextView comment;
    private ArrayList<comment> commentArrayList = null;
    ProgressBar progressBar;
    ListView list_view;
    DatabaseReference myRef = null;
    FirebaseUser user = null;
    ImageView add_comment;
    String name1,Id;
    ImageView place_image;
    int valueLoginUser;
    Uri photoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_info_page);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        comment = findViewById(R.id.comment);
        list_view = findViewById(R.id.list_view);
        add_comment = findViewById(R.id.add_comment);
        place_image = findViewById(R.id.place_image);
        TextView location_name = findViewById(R.id.location_name);
        TextView time_work = findViewById(R.id.time_work);
        Button see_on_google_map = findViewById(R.id.see_on_google_map);
        ReadMoreTextView location_information = findViewById(R.id.location_information);
        new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 1000);
        String place_name = getIntent().getExtras().getString("place_name");
        String place_description = getIntent().getExtras().getString("place_description");
        String place_working_hours = getIntent().getExtras().getString("place_working_hours");
        String place_location_link = getIntent().getExtras().getString("place_location_link");
        String id1 = getIntent().getStringExtra("id");
        String imageName = getIntent().getStringExtra("imageName");
        FirebaseStorage.getInstance().getReference().child(imageName).child(id1+".jpg").getDownloadUrl()
                .addOnSuccessListener(uri -> Glide.with(getApplicationContext())
                        .load(uri)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                             place_image.setImageDrawable(resource);
                              }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        }))
                .addOnFailureListener(exception -> {
                });

        location_name.setText(place_name);
        location_information.setText(place_description);
        time_work.setText(place_working_hours);
        see_on_google_map.setOnClickListener(v -> {
            Intent go_google_map = new Intent(Intent.ACTION_VIEW);
            go_google_map.setData(Uri.parse(place_location_link));
            startActivity(go_google_map);
        });
        FirebaseDatabase database_admin = FirebaseDatabase.getInstance();
        DatabaseReference myRef_admin = database_admin.getReference("Admin");
        myRef_admin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.child("loginAdmin").child("loginValue").getValue(String.class);
                    if (value != null) {
                        int num = Integer.parseInt(value);
                        if (num == 0){
                            place_info();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //

        //
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        commentArrayList = new ArrayList<>();
         myRef = database.getReference("comments").child(place_name);
         user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            add_comment.setVisibility(View.VISIBLE);
            add_comment.setOnClickListener(v -> showDialog());
            list_view.setOnItemLongClickListener((parent, view, position, id) -> {
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view1 = layoutInflater.inflate(R.layout.edit_remove_dialog , null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
                Button edit = view1.findViewById(R.id.edit_comment);
                Button delete = view1.findViewById(R.id.delete_comment);
                EditText commentItem = view1.findViewById(R.id.comment_item);
                com.example.tourisminjordan.comment comment1 = commentArrayList.get(position);
                commentItem.setText(comment1.getComment());
                if (comment1.getId().equals(Id)) {
                    edit.setOnClickListener(v -> {
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
                        myRef.child(comment1.getId());
                        String newComment = commentItem.getText().toString();
                        com.example.tourisminjordan.comment afterUpdate = new comment(name1,comment1.getId(), newComment, getCurrentData());
                        myRef.child(comment1.getId()).setValue(afterUpdate);
                        alertD.cancel();
                    });
                    delete.setOnClickListener(v -> {
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
                        myRef.child(comment1.getId()).removeValue();
                        alertD.cancel();
                    });
                }
                alertD.setView(view1);
                alertD.show();
                return true;
            });

        }else {
            list_view.setOnItemLongClickListener((parent, view, position, id) -> {
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view1 = layoutInflater.inflate(R.layout.delete_comment, null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
                Button delete = view1.findViewById(R.id.delete_comment);
                EditText commentItem = view1.findViewById(R.id.comment_item);
                com.example.tourisminjordan.comment comment1 = commentArrayList.get(position);
                commentItem.setText(comment1.getComment());
                delete.setOnClickListener(v -> {
                    progressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
                    myRef.child(comment1.getId()).removeValue();
                    alertD.cancel();
                });
                alertD.setView(view1);
                alertD.show();
                return true;
            });
        }
    }
    private void place_info() {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            name1 = snapshot.getValue(String.class);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentArrayList.clear();
                new comment();
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Id = commentSnapshot.getKey();
                        String text = commentSnapshot.child("comment").getValue(String.class);
                        String time = commentSnapshot.child("time").getValue(String.class);
                        String user_name = commentSnapshot.child("user_name").getValue(String.class);
                        comment comment = new comment(photoUrl ,user_name, Id, text, time);
                        commentArrayList.add(0,comment);
                    }
                comment_adapter adapter = new comment_adapter(getApplicationContext(), commentArrayList, R.layout.comment_holder);
                list_view.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                DatabaseError databaseError;
                databaseError = null;
                assert false;
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
    private void showDialog() {
        progressBar = findViewById(R.id.progressBar);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.add_comment , null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef1 = FirebaseDatabase.getInstance().getReference();
        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valueLoginUser1 = snapshot.child("loginUser").child(uid).child("value").getValue(String.class);
                assert valueLoginUser1 != null;
                valueLoginUser = Integer.parseInt(valueLoginUser1);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Button btn = view.findViewById(R.id.post_comment);
        EditText comment = view.findViewById(R.id.comment);
        btn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                String comment1 = comment.getText().toString();
            if (comment1.isEmpty()){
                Toast.makeText(this, R.string.Please_Enter_Your_Comment, Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
                String id = myRef.push().getKey();
                com.example.tourisminjordan.comment comment2;
                    comment2 = new comment(name1, id, comment1, getCurrentData());
                if (id != null) {
                    myRef.child(id).setValue(comment2);
                    alertD.cancel();
                }
            }
        });
        alertD.setView(view);
        alertD.show();
    }
    public String getCurrentData(){
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE hh:mm a");
        return simpleDateFormat.format(calendar.getTime());
    }
}