package com.example.premierleaguepredictions.Comments;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.R;
import com.squareup.picasso.Picasso;


import java.io.File;

public class NameViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivUserImage;
    private TextView tvNickname;
    private TextView tvTime;
    private TextView tvComment;

    public NameViewHolder(@NonNull View itemView) {
        super(itemView);

        ivUserImage = itemView.findViewById(R.id.ivUserImage);
        tvNickname = itemView.findViewById(R.id.tvNickname);
        tvTime = itemView.findViewById(R.id.tvTime);
        tvComment = itemView.findViewById(R.id.tvComment);
    }

    public void setIvUserImage(String filepath) {
        Picasso.get().load(filepath).fit().into(ivUserImage);
    }

    public void setTvNickname(String tvNickname) {
        this.tvNickname.setText(tvNickname);
    }

    public void setTvTime(String tvTime) {
        this.tvTime.setText(tvTime);
    }

    public void setTvComment(String tvComment) {
        this.tvComment.setText(tvComment);
    }
}
