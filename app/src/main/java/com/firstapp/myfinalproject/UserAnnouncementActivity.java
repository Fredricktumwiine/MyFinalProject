package com.firstapp.myfinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAnnouncementActivity extends AppCompatActivity {

    private RecyclerView announcementRV;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<AnnouncementRVModal> announcementRVModalArrayList;
    private AnnouncementAdapter announcementRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_announcements);

        announcementRV = findViewById(R.id.idRVAnnouncements);
        announcementRV.setHasFixedSize(true);
        loadingPB = findViewById(R.id.idPBLoading);

        databaseReference = FirebaseDatabase.getInstance().getReference("Announcements");
        announcementRVModalArrayList = new ArrayList<>();

        announcementRVAdapter = new AnnouncementAdapter(announcementRVModalArrayList, this);
        announcementRV.setLayoutManager(new LinearLayoutManager(this));
        announcementRV.setAdapter(announcementRVAdapter);

        getAllAnnouncements();

    }

    private void getAllAnnouncements(){
        announcementRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                announcementRVModalArrayList.add(snapshot.getValue(AnnouncementRVModal.class));
                announcementRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                announcementRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                announcementRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                announcementRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }

        );
    }
}
