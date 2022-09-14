package com.firstapp.myfinalproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<HomeworkRVModal> homeworkRVModalArrayList;
    private Context context;

    public HomeworkAdapter(ArrayList<HomeworkRVModal> homeworkRVModalArrayList, Context context) {
        this.homeworkRVModalArrayList = homeworkRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homework_rv_item,parent,false);
        return new HomeworkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkAdapter.ViewHolder holder, int position) {

        HomeworkRVModal homeworkRVModal = homeworkRVModalArrayList.get(position);
        holder.homeworkTV.setText(homeworkRVModal.getHomework());

    }

    @Override
    public int getItemCount() {
        return homeworkRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView homeworkTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeworkTV = itemView.findViewById(R.id.idTVHomework);

        }
    }
}