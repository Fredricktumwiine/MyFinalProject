package com.firstapp.myfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentRVAdapter extends RecyclerView.Adapter<StudentRVAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<StudentRVModal> studentRVModalArrayList;
    private Context context;
    private StudentClickInterface studentClickInterface;

    public StudentRVAdapter(ArrayList<StudentRVModal> studentRVModalArrayList, Context context, StudentClickInterface studentClickInterface) {
        this.studentRVModalArrayList = studentRVModalArrayList;
        this.context = context;
        this.studentClickInterface = studentClickInterface;
    }

    @NonNull
    @Override
    public StudentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentRVAdapter.ViewHolder holder, int position) {
        StudentRVModal studentRVModal = studentRVModalArrayList.get(position);
        holder.studentNameTV.setText(studentRVModal.getStudentName());
        holder.studentAgeTV.setText(studentRVModal.getStudentAge()+" yrs");
        Picasso.get().load(studentRVModal.getStudentImageUri()).into(holder.studentIV);
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentClickInterface.onCourseClick(position);
            }
        });
    }
    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {

        return studentRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView studentNameTV, studentAgeTV;
        public ImageView studentIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTV = itemView.findViewById(R.id.idTVStudentName);
            studentAgeTV = itemView.findViewById(R.id.idTVStudentAge);
            studentIV = itemView.findViewById(R.id.idIVStudent);
        }
    }

    public interface StudentClickInterface {
        void onCourseClick(int position);
    }
}
