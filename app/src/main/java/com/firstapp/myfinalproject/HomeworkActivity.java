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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeworkActivity extends AppCompatActivity {

    private RecyclerView homeworkRV;
    private FloatingActionButton addHomework;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<HomeworkRVModal> homeworkRVModalArrayList;
    private HomeworkAdapter homeworkRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        homeworkRV = findViewById(R.id.idRVHomework);
        homeworkRV.setHasFixedSize(true);
        loadingPB = findViewById(R.id.idPBLoading);
        addHomework = findViewById(R.id.idAddHomework);

        databaseReference = FirebaseDatabase.getInstance().getReference("Homework");
        homeworkRVModalArrayList = new ArrayList<>();

        homeworkRVAdapter = new HomeworkAdapter(homeworkRVModalArrayList, this);
        homeworkRV.setLayoutManager(new LinearLayoutManager(this));
        homeworkRV.setAdapter(homeworkRVAdapter);

        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeworkActivity.this, AddHomeworkActivity.class));
            }
        });

        getAllHomework();

    }

    private void getAllHomework(){
        homeworkRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
                                                    @Override
                                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                        loadingPB.setVisibility(View.GONE);
                                                        homeworkRVModalArrayList.add(snapshot.getValue(HomeworkRVModal.class));
                                                        homeworkRVAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                        loadingPB.setVisibility(View.GONE);
                                                        homeworkRVAdapter.notifyDataSetChanged();

                                                    }

                                                    @Override
                                                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                                                        loadingPB.setVisibility(View.GONE);
                                                        homeworkRVAdapter.notifyDataSetChanged();

                                                    }

                                                    @Override
                                                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                        loadingPB.setVisibility(View.GONE);
                                                        homeworkRVAdapter.notifyDataSetChanged();

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                }

        );
    }
}