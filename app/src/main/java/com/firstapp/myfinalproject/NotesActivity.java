package com.firstapp.myfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    List<Product> productList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesactivity_main);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_about_card_show);
        RelativeLayout relativeLayout = findViewById(R.id.rl);
        relativeLayout.startAnimation(animation);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        productList.add(
                new Product(
                        1,
                        "Mathematics p7\n",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2Fp7%20MATHEMATICS%20NOTES%20-%20original%20TERM%201%20and%202.pdf?alt=media&token=fbc0d643-4470-4668-bb3c-ab2aa1c51749"

                ));

        productList.add(
                new Product(
                        1,
                        " Science P7 \n",

                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FP7SCIENCE.pdf?alt=media&token=3e84c3e0-e25e-4102-b963-6ac5155498f9"
                ));

        productList.add(
                new Product(
                        1,
                        "SST P7 ",


                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2Fp7-sst.pdf?alt=media&token=1ede8239-cf6c-4afa-a84d-a981dd513c03"
                ));

        productList.add(
                new Product(
                        1,
                        "English P7",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FP_7%20ENGLISH%20LESSON%20NOTES%20.pdf?alt=media&token=0e61650f-7cda-4ae0-abd5-082fa26d9628    "
                ));
        productList.add(
                new Product(
                        1,
                        "Mathematics P6",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FP6%20mathematics%20%20%20LESSON%20NOTES%20TERM%201.pdf?alt=media&token=69b48fd6-6dee-4dd3-abef-6be4a6edc205    "
                ));
        productList.add(
                new Product(
                        1,
                        " Science P6",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2Fp6%20%20SCIENCE%20NOTES%20FOR%20TERM%20TWO.pdf?alt=media&token=baf683c2-2919-4d3b-9477-b89f3051032e   "
                ));
        productList.add(
                new Product(
                        1,
                        "SST P6",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FSST%20P_6%20LESSON%20NOTES%202018.pdf?alt=media&token=60033860-d492-4bd8-956e-2256f103d823"
                ));
        productList.add(
                new Product(
                        1,
                        "English P6",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FP6%20ENGLISH%20GRAMER%20NOTES.pdf?alt=media&token=e94740ad-da2d-4946-840f-7b9184858177    "
                ));
        productList.add(
                new Product(
                        1,
                        "Religious Education",
                        60000,
                        R.drawable.pdficon,
                        "https://firebasestorage.googleapis.com/v0/b/myfinalproject-ed90d.appspot.com/o/notes%2FP6%20%20REligious%20education%20NOTES%20TERM%20II.pdf?alt=media&token=921bb02f-d43f-474d-84c6-e23f196a15d1    "
                ));


        ProductAdapter adapter = new ProductAdapter(this, productList);

        recyclerView.setAdapter(adapter);
    }

}