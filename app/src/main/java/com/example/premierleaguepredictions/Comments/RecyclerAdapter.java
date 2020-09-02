package com.example.premierleaguepredictions.Comments;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<NameViewHolder> {

    private List<Comment> commentList = new ArrayList<>();

    public RecyclerAdapter(){}
    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);

        return new NameViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        holder.setIvUserImage(commentList.get(position).getIvUserImage());
        holder.setTvNickname(commentList.get(position).getNickname());
        holder.setTvTime(commentList.get(position).getTime());
        holder.setTvComment(commentList.get(position).getText());
    }

    public void addComments(List<Comment> allComments) {
        this.commentList.clear();
        this.commentList.addAll(allComments);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
