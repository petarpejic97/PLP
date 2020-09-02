package com.example.premierleaguepredictions.Fixtures;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premierleaguepredictions.Clubs.OnItemClickListener;
import com.example.premierleaguepredictions.R;

public class FixturesViewHolder extends RecyclerView.ViewHolder  {

    private ImageView HomeImage;
    private ImageView AwayImage;
    private TextView HomeNameClub;
    private TextView AwayNameClub;
    private TextView HomeChances;
    private TextView AwayChances;

    public FixturesViewHolder(@NonNull View itemView) {
        super(itemView);

        HomeNameClub = itemView.findViewById(R.id.firstClub);
        HomeChances = itemView.findViewById(R.id.firstChance);
        HomeImage = itemView.findViewById(R.id.ivHomeClub);

        AwayNameClub = itemView.findViewById(R.id.secondClub);
        AwayChances = itemView.findViewById(R.id.secondChance);
        AwayImage = itemView.findViewById(R.id.ivAwayClub);


    }


    public void setHomeNameClub(String homeNameClub) {
        this.HomeNameClub.setText(homeNameClub);
    }

    public void setAwayNameClub(String awayNameClub) {
        this.AwayNameClub.setText(awayNameClub);
    }

    public void setHomeChances(double homeChances) {
        this.HomeChances.setText(String.valueOf(homeChances));
    }

    public void setAwayChances(double awayChances) {
        this.AwayChances.setText(String.valueOf(awayChances));
    }

    public void setHomeImage(String homeClubName){
        switch(homeClubName) {
            case "Arsenal" :
                this.HomeImage.setImageResource(R.drawable.arsenal);
                break;
            case "Chelsea" :
                this.HomeImage.setImageResource(R.drawable.chelsea);
                break;
            case "Leicester" :
                this.HomeImage.setImageResource(R.drawable.leicester);
                break;
            case "Everton" :
                this.HomeImage.setImageResource(R.drawable.everton);
                break;
            case "Crystal Palace" :
                this.HomeImage.setImageResource(R.drawable.christalpalace);
                break;
            case "Brighton" :
                this.HomeImage.setImageResource(R.drawable.brighton);
                break;
            case "Burnley" :
                this.HomeImage.setImageResource(R.drawable.burnley);
                break;
            case "Bournemouth" :
                this.HomeImage.setImageResource(R.drawable.bournemounht);
                break;
            case "Aston Villa" :
                this.HomeImage.setImageResource(R.drawable.astonvilla);
                break;
            case "Sheffield United" :
                this.HomeImage.setImageResource(R.drawable.shefildunited);
                break;
            case "Norwich" :
                this.HomeImage.setImageResource(R.drawable.norwich);
                break;
            case "Newcastle United" :
                this.HomeImage.setImageResource(R.drawable.newcastle);
                break;
            case "Manchester United" :
                this.HomeImage.setImageResource(R.drawable.manutd);
                break;
            case "Manchester City" :
                this.HomeImage.setImageResource(R.drawable.mancity);
                break;
            case "Liverpool" :
                this.HomeImage.setImageResource(R.drawable.liverpoolfc);
                break;
            case "Wolverhampton" :
                this.HomeImage.setImageResource(R.drawable.wolves);
                break;
            case "West Ham United" :
                this.HomeImage.setImageResource(R.drawable.westham);
                break;
            case "Watford" :
                this.HomeImage.setImageResource(R.drawable.watford);
                break;
            case "Tottenham" :
                this.HomeImage.setImageResource(R.drawable.tottenham);
                break;
            case "Southampton" :
                this.HomeImage.setImageResource(R.drawable.southampton);
                break;
        }
    }

    public void setAwayImage(String awayClubName){
        switch(awayClubName) {
            case "Arsenal" :
                this.AwayImage.setImageResource(R.drawable.arsenal);
                break;
            case "Chelsea" :
                this.AwayImage.setImageResource(R.drawable.chelsea);
                break;
            case "Leicester" :
                this.AwayImage.setImageResource(R.drawable.leicester);
                break;
            case "Everton" :
                this.AwayImage.setImageResource(R.drawable.everton);
                break;
            case "Crystal Palace" :
                this.AwayImage.setImageResource(R.drawable.christalpalace);
                break;
            case "Brighton" :
                this.AwayImage.setImageResource(R.drawable.brighton);
                break;
            case "Burnley" :
                this.AwayImage.setImageResource(R.drawable.burnley);
                break;
            case "Bournemouth" :
                this.AwayImage.setImageResource(R.drawable.bournemounht);
                break;
            case "Aston Villa" :
                this.AwayImage.setImageResource(R.drawable.astonvilla);
                break;
            case "Sheffield United" :
                this.AwayImage.setImageResource(R.drawable.shefildunited);
                break;
            case "Norwich" :
                this.AwayImage.setImageResource(R.drawable.norwich);
                break;
            case "Newcastle United" :
                this.AwayImage.setImageResource(R.drawable.newcastle);
                break;
            case "Manchester United" :
                this.AwayImage.setImageResource(R.drawable.manutd);
                break;
            case "Manchester City" :
                this.AwayImage.setImageResource(R.drawable.mancity);
                break;
            case "Liverpool" :
                this.AwayImage.setImageResource(R.drawable.liverpoolfc);
                break;
            case "Wolverhampton" :
                this.AwayImage.setImageResource(R.drawable.wolves);
                break;
            case "West Ham United" :
                this.AwayImage.setImageResource(R.drawable.westham);
                break;
            case "Watford" :
                this.AwayImage.setImageResource(R.drawable.watford);
                break;
            case "Tottenham" :
                this.AwayImage.setImageResource(R.drawable.tottenham);
                break;
            case "Southampton" :
                this.AwayImage.setImageResource(R.drawable.southampton);
                break;
        }
    }


}
