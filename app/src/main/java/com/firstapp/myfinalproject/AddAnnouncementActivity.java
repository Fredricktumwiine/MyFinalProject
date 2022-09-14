package com.firstapp.myfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAnnouncementActivity extends AppCompatActivity {

    private TextInputEditText announcementEdt;
    private Button postAnnouncementBtn;
    private ProgressBar loadinPB;
    private DatabaseReference databaseReference;
    private String announcementID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        announcementEdt = findViewById(R.id.idEdtAnnouncement);
        postAnnouncementBtn = findViewById(R.id.idBtnPostAnnouncement);
        loadinPB = findViewById(R.id.idPBLoadin);

        databaseReference = FirebaseDatabase.getInstance().getReference("Announcements");

        postAnnouncementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadinPB.setVisibility(View.VISIBLE);
                String announcement = announcementEdt.getText().toString();
                String announcementID = "" + System.currentTimeMillis();
                AnnouncementRVModal announcementRVModal = new AnnouncementRVModal(announcement, announcementID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadinPB.setVisibility(View.GONE);
                        databaseReference.child(announcementID).setValue(announcementRVModal);
                        Toast.makeText(AddAnnouncementActivity.this, "Announcement posted..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddAnnouncementActivity.this, AnnouncementsActivity.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddAnnouncementActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}