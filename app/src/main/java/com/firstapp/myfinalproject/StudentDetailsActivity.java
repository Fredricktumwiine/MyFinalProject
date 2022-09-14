package com.firstapp.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView name, age, maths, science, english, sSt, comment, house;
    private ImageView studentImage;
    private DatabaseReference databaseReference;
    private StorageReference mStorageReference;
    private ArrayList<StudentRVModal> studentRVModalArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        studentImage = findViewById(R.id.idStudentsImage);
        name = findViewById(R.id.idTVName);
        age = findViewById(R.id.idTVAge);
        maths = findViewById(R.id.idTVMaths);
        science = findViewById(R.id.idTVScience);
        english = findViewById(R.id.idTVEnglish);
        sSt = findViewById(R.id.idTVSSt);
        comment = findViewById(R.id.idTVComment);
        house = findViewById(R.id.idTVHouse);

        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");

        StudentRVModal studentRVModal = getIntent().getParcelableExtra("course");;
        Picasso.get().load(studentRVModal.getStudentImageUri()).into(studentImage);
        name.setText(studentRVModal.getStudentName());
        comment.setText(studentRVModal.getStudentComment());
        house.setText(studentRVModal.getStudentHouse());
        age.setText(studentRVModal.getStudentAge()+"yrs");
        maths.setText(studentRVModal.getMaths());
        science.setText(studentRVModal.getScience());
        english.setText(studentRVModal.getEnglish());
        sSt.setText(studentRVModal.getsSt());


    }
}