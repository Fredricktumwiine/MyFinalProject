package com.firstapp.myfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHomeworkActivity extends AppCompatActivity {

    private TextInputEditText homeworkEdt;
    private Button postHomeworkBtn;
    private ProgressBar loadinPB;
    private DatabaseReference databaseReference;
    private String announcementID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
        homeworkEdt = findViewById(R.id.idEdtHomework);
        postHomeworkBtn = findViewById(R.id.idBtnPostHomework);
        loadinPB = findViewById(R.id.idPBLoadin);

        databaseReference = FirebaseDatabase.getInstance().getReference("Homework");

        postHomeworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadinPB.setVisibility(View.VISIBLE);
                String homework = homeworkEdt.getText().toString();
                String homeworkID = "" + System.currentTimeMillis();

                HomeworkRVModal homeworkRVModal = new HomeworkRVModal(homework, homeworkID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadinPB.setVisibility(View.GONE);
                        databaseReference.child(homeworkID).setValue(homeworkRVModal);
                        Toast.makeText(AddHomeworkActivity.this, "Homework posted..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddHomeworkActivity.this, HomeworkActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddHomeworkActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

}