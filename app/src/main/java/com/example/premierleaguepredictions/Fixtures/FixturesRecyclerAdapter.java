package com.example.premierleaguepredictions.Fixtures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.R;

import java.util.ArrayList;
import java.util.List;

public class FixturesRecyclerAdapter extends RecyclerView.Adapter<FixturesViewHolder>{

    private List<Fixture> fixtureList = new ArrayList<>();

    @NonNull
    @Override
    public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixtures_item,parent,false);

        return new FixturesViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesViewHolder holder, int position) {
        holder.setHomeNameClub(fixtureList.get(position).getHomeClub().getName());
        holder.setHomeChances(fixtureList.get(position).calcHomeChanses());
        holder.setHomeImage(fixtureList.get(position).getHomeClub().getName());
        holder.setAwayNameClub(fixtureList.get(position).getAwayClub().getName());
        holder.setAwayChances(fixtureList.get(position).calcAwayChanses());
        holder.setAwayImage(fixtureList.get(position).getAwayClub().getName());
    }

    public void addFixture(List<Fixture> allFixtures) {
        this.fixtureList.clear();
        this.fixtureList.addAll(allFixtures);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return fixtureList.size();
    }
}
