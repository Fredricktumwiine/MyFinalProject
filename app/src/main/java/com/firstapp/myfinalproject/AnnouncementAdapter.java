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

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<AnnouncementRVModal> announcementRVModalArrayList;
    private Context context;

    public AnnouncementAdapter(ArrayList<AnnouncementRVModal> announcementRVModalArrayList, Context context) {
        this.announcementRVModalArrayList = announcementRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcements_rv_item,parent,false);
        return new AnnouncementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {

        AnnouncementRVModal announcementRVModal = announcementRVModalArrayList.get(position);
        holder.announcementTV.setText(announcementRVModal.getAnnouncement());

    }

    @Override
    public int getItemCount() {
        return announcementRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView announcementTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            announcementTV = itemView.findViewById(R.id.idTVAnnouncement);

        }
    }
}