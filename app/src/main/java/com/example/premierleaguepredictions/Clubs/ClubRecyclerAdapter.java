package com.example.premierleaguepredictions.Clubs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.R;

import java.util.ArrayList;
import java.util.List;

public class ClubRecyclerAdapter extends RecyclerView.Adapter<ClubViewHolder> {

    private List<Club> clubList = new ArrayList<>();
    private OnItemClickListener clickListener;

    public ClubRecyclerAdapter(OnItemClickListener clickListener){this.clickListener=clickListener;}
    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_item,parent,false);

        return new ClubViewHolder(cellView,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {

        holder.setIMage(clubList.get(position).getName());
        holder.setName(clubList.get(position).getName());
        holder.setPlayed(clubList.get(position).getPlayedMatches());
        holder.setWon(clubList.get(position).getWonGMatches());
        holder.setDraw(clubList.get(position).getDrawMatches());
        holder.setLost(clubList.get(position).getLostMatches());
        holder.setGoalDifference(clubList.get(position).getScoredGoals()- clubList.get(position).getRecivedGoals());
        holder.setPoints(clubList.get(position).getPoints());
    }
    public void addClub(List<Club> allClubs) {
        this.clubList.clear();
        this.clubList.addAll(allClubs);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return clubList.size();
    }
}
