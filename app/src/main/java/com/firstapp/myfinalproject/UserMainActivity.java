package com.firstapp.myfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity implements StudentRVAdapter.StudentClickInterface {

    private RecyclerView courseRV;
    private FloatingActionButton addFAB;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<StudentRVModal> studentRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private StudentRVAdapter studentRVAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");
        studentRVModalArrayList = new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBsheet);
        mAuth = FirebaseAuth.getInstance();

        studentRVAdapter = new StudentRVAdapter(studentRVModalArrayList, this, this);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        courseRV.setAdapter(studentRVAdapter);

        getAllCourses();
    }

    private void getAllCourses(){
        studentRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                studentRVModalArrayList.add(snapshot.getValue(StudentRVModal.class));
                studentRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                studentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                studentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                studentRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(studentRVModalArrayList.get(position));

    }

    private void displayBottomSheet(StudentRVModal studentRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.user_bottom_sheet_dialog, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView courseNameTV = layout.findViewById(R.id.idTVStudentName);
        TextView courseDescTV = layout.findViewById(R.id.idTVStudentComment);
        TextView courseSuitedForTV = layout.findViewById(R.id.idTVStudentHouse);
        TextView coursePriceTV = layout.findViewById(R.id.idTVStudentAge);
        ImageView courseIV = layout.findViewById(R.id.idIVStudent);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);


        courseNameTV.setText(studentRVModal.getStudentName());
        courseDescTV.setText(studentRVModal.getStudentComment());
        courseSuitedForTV.setText(studentRVModal.getStudentHouse());
        coursePriceTV.setText(studentRVModal.getStudentAge()+" yrs");
        Picasso.get().load(studentRVModal.getStudentImageUri()).into(courseIV);

        /*editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserMainActivity.this, EditStudentActivity.class);
                i.putExtra("course", studentRVModal);
                startActivity(i);

            }
        });*/

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserMainActivity.this, StudentDetailsActivity.class);
                i.putExtra("course", studentRVModal);
                startActivity(i);
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(studentRVModal.getCourseLink()));
                startActivity(i);*/
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogout:
                Toast.makeText(this, "User Logged out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(UserMainActivity.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
