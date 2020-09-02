package com.example.premierleaguepredictions.Clubs;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.R;

public class ClubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView image;
    private TextView name;
    private TextView played;
    private TextView won;
    private TextView draw;
    private TextView lost;
    private TextView goalDifference;
    private TextView points;
    private OnItemClickListener clickListener;

    public ClubViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);

        this.clickListener=listener;
        image = itemView.findViewById(R.id.ivClubImage);
        name = itemView.findViewById(R.id.tvClubName);
        played = itemView.findViewById(R.id.playedMatches);
        won = itemView.findViewById(R.id.wonMatches);
        draw = itemView.findViewById(R.id.drawMatches);
        lost = itemView.findViewById(R.id.lostMatches);
        goalDifference = itemView.findViewById(R.id.goalDifference);
        points = itemView.findViewById(R.id.points);
        itemView.setOnClickListener(this);
    }

    public void setIMage(String ClubName) {
        switch(ClubName) {
            case "Arsenal" :
                this.image.setImageResource(R.drawable.arsenal);
                break;
            case "Chelsea" :
                this.image.setImageResource(R.drawable.chelsea);
                break;
            case "Leicester" :
                this.image.setImageResource(R.drawable.leicester);
                break;
            case "Everton" :
                this.image.setImageResource(R.drawable.everton);
                break;
            case "Crystal Palace" :
                this.image.setImageResource(R.drawable.christalpalace);
                break;
            case "Brighton" :
                this.image.setImageResource(R.drawable.brighton);
                break;
            case "Burnley" :
                this.image.setImageResource(R.drawable.burnley);
                break;
            case "Bournemouth" :
                this.image.setImageResource(R.drawable.bournemounht);
                break;
            case "Aston Villa" :
                this.image.setImageResource(R.drawable.astonvilla);
                break;
            case "Sheffield United" :
                this.image.setImageResource(R.drawable.shefildunited);
                break;
            case "Norwich" :
                this.image.setImageResource(R.drawable.norwich);
                break;
            case "Newcastle United" :
                this.image.setImageResource(R.drawable.newcastle);
                break;
            case "Manchester United" :
                this.image.setImageResource(R.drawable.manutd);
                break;
            case "Manchester City" :
                this.image.setImageResource(R.drawable.mancity);
                break;
            case "Liverpool" :
                this.image.setImageResource(R.drawable.liverpoolfc);
                break;
            case "Wolverhampton" :
                this.image.setImageResource(R.drawable.wolves);
                break;
            case "West Ham United" :
                this.image.setImageResource(R.drawable.westham);
                break;
            case "Watford" :
                this.image.setImageResource(R.drawable.watford);
                break;
            case "Tottenham" :
                this.image.setImageResource(R.drawable.tottenham);
                break;
            case "Southampton" :
                this.image.setImageResource(R.drawable.southampton);
                break;
        }
    }
    public void setName(String name) { this.name.setText(name); }
    public void setPlayed(int played){this.played.setText(String.valueOf(played));}
    public void setWon(int won){
        this.won.setText(String.valueOf(won));
    }
    public void setDraw(int draw){
        this.draw.setText(String.valueOf(draw));
    }
    public void setLost(int lost){
        this.lost.setText(String.valueOf(lost));
    }
    public void setGoalDifference(int difference){
        this.goalDifference.setText(String.valueOf(difference)); }
    public void setPoints(int points){
        this.points.setText(String.valueOf(points));
    }

    @Override
    public void onClick(View v) {
        clickListener.onItemClick(getAdapterPosition());
    }
}
