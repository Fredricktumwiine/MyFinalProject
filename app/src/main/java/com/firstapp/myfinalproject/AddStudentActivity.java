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

public class AddStudentActivity extends AppCompatActivity {

    private TextInputEditText studentNameEdt, studentAgeEdt, studentHouseEdt, studentImgEdt,
            studentCommentEdt, MathsMarksEdt, ScienceMarksEdt, EnglishMarksEdt, SStMarksEdt;
    private Button addStudentBtn, selectImageBtn;
    private ImageView studentImage;
    private TextView imageUrl;
    private ProgressBar loadinPB;
    private StorageReference mStorageReference;
    private DatabaseReference databaseReference;
    private String courseID;
    private Uri studentImageUri;
    private StorageTask mUploadTask;
    ActivityResultLauncher<String> studentImageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

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

        addStudentBtn = findViewById(R.id.idBtnAddStudent);
        selectImageBtn = findViewById(R.id.idSelectImageBtn);
        loadinPB = findViewById(R.id.idPBLoadin);
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses");

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

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFile();


            }
        });
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
            String coursePrice = studentAgeEdt.getText().toString();
            String SuitedFor = studentHouseEdt.getText().toString();
            String maths = MathsMarksEdt.getText().toString();
            String science = ScienceMarksEdt.getText().toString();
            String english = EnglishMarksEdt.getText().toString();
            String sSt = SStMarksEdt.getText().toString();
            String courseDesc = studentCommentEdt.getText().toString();
            courseID = courseName;
            StorageReference taskSnapshot = null;

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
                                    String MyimageUrl = uri.toString();

                                    StudentRVModal studentRVModal = new StudentRVModal(courseName, courseDesc, coursePrice, SuitedFor,
                                            MyimageUrl, maths, science, english, sSt, courseID);

                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            loadinPB.setVisibility(View.GONE);
                                            databaseReference.child(courseID).setValue(studentRVModal);
                                            Toast.makeText(AddStudentActivity.this, "Student added..", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(AddStudentActivity.this, "Error is " + error.toString(), Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(AddStudentActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddStudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(AddStudentActivity.this, "No Image selected", Toast.LENGTH_SHORT).show();
        }

    }
}