package com.firstapp.myfinalproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class EditStudentActivity extends AppCompatActivity {

    private TextInputEditText studentNameEdt, studentAgeEdt, SStMarksEdt, EnglishMarksEdt, studentHouseEdt,
            studentCommentEdt, MathsMarksEdt, ScienceMarksEdt;
    private Button updateCourseBtn,deleteCourseBtn, selectImageBtn;
    private ProgressBar loadinPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference mStorageReference;
    private String courseID;
    private TextView imageUrl, courseImgEdt;
    private ImageView studentImage;
    private StudentRVModal studentRVModal;
    private StorageTask mUploadTask;
    Uri studentImageUri;
    ActivityResultLauncher<String> studentImageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        firebaseDatabase = FirebaseDatabase.getInstance();
        studentNameEdt = findViewById(R.id.idEdtStudentName);
        studentAgeEdt = findViewById(R.id.idEdtStudentAge);
        studentHouseEdt = findViewById(R.id.idEdtStudentHouse);
        imageUrl = findViewById(R.id.idTVImageurl);
        studentImage = findViewById(R.id.idStudentImage);
        studentCommentEdt = findViewById(R.id.idEdtStudentComment);
        MathsMarksEdt = findViewById(R.id.idEdtMathsMarks);
        ScienceMarksEdt = findViewById(R.id.idEdtScienceMarks);
        SStMarksEdt = findViewById(R.id.idEdtSSTMarks);
        EnglishMarksEdt = findViewById(R.id.idEdtEnglishMarks);
        selectImageBtn = findViewById(R.id.idImage);

        updateCourseBtn = findViewById(R.id.idBtnUpdateStudent);
        deleteCourseBtn = findViewById(R.id.idBtnDeleteStudent);
        loadinPB = findViewById(R.id.idPBLoadin);

        studentImageSelector = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        studentImage.setImageURI(result);
                        studentImageUri = result;
                    }
                }

        );
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentImageSelector.launch("image/*");
            }
        });
        studentRVModal = getIntent().getParcelableExtra("course");
        if(studentRVModal !=null){
            studentNameEdt.setText(studentRVModal.getStudentName());
            studentAgeEdt.setText(studentRVModal.getStudentAge());
            studentHouseEdt.setText(studentRVModal.getStudentHouse());
            studentCommentEdt.setText(studentRVModal.getStudentComment());
            MathsMarksEdt.setText(studentRVModal.getMaths());
            ScienceMarksEdt.setText(studentRVModal.getScience());
            SStMarksEdt.setText(studentRVModal.getsSt());
            EnglishMarksEdt.setText(studentRVModal.getEnglish());
            courseID = studentRVModal.getStudentID();
        }

        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");

        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFile();
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });

    }
    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditStudentActivity.this, MainActivity.class));
        finish();

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){

        if (studentImageUri != null) {

            loadinPB.setVisibility(View.VISIBLE);
            String courseName = studentNameEdt.getText().toString();
            String studentAge = studentAgeEdt.getText().toString();
            String studentHouse = studentHouseEdt.getText().toString();
            String studentComment = studentCommentEdt.getText().toString();
            String maths = MathsMarksEdt.getText().toString();
            String science = ScienceMarksEdt.getText().toString();
            String english = EnglishMarksEdt.getText().toString();
            String sst = SStMarksEdt.getText().toString();

            courseID = courseName;

            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(studentImageUri));

            mUploadTask = fileReference.putFile(studentImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String MyImageUrl = uri.toString();

                                            Map<String, Object> map = new HashMap<>();
                                            map.put("studentName", courseName);
                                            map.put("studentAge", studentAge);
                                            map.put("maths", maths);
                                            map.put("science", science);
                                            map.put("sSt", sst);
                                            map.put("english", english);
                                            map.put("studentComment", studentComment);
                                            map.put("studentHouse", studentHouse);
                                            map.put("studentImageUri", MyImageUrl);
                                            map.put("courseID", courseID);

                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    loadinPB.setVisibility(View.GONE);
                                                    databaseReference.updateChildren(map);
                                                    Toast.makeText(EditStudentActivity.this, "Student updated..", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(EditStudentActivity.this, MainActivity.class));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(EditStudentActivity.this, "Failed to update student..", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                        }
                                    });
                                }
                            }

                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, 500);


                            Toast.makeText(EditStudentActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditStudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        } else {
            Toast.makeText(EditStudentActivity.this, "No Image selected", Toast.LENGTH_SHORT).show();
        }

    }

}