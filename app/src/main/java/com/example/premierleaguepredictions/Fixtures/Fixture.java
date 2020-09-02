package com.example.premierleaguepredictions.Fixtures;

import java.text.DecimalFormat;


public class Fixture {
    private ClubData HomeClub;
    private ClubData AwayClub;

    public Fixture(ClubData homeClub, ClubData awayClub) {
        this.HomeClub = homeClub;
        this.AwayClub = awayClub;
    }

    public Fixture() {
        this.HomeClub = new ClubData("/",1);
        this.AwayClub = new ClubData("/",1);
    }
    public Fixture(double num) {
        this.HomeClub = new ClubData("/",1);
        this.AwayClub = new ClubData("/",1);
    }

    public ClubData getHomeClub() {
        return HomeClub;
    }

    public ClubData getAwayClub() {
        return AwayClub;
    }

    public double calcHomeChanses(){
        double oddPercentage=1/getHomeClub().getBettingCoefficient()*100;
        DecimalFormat df = new DecimalFormat("#.##");
        oddPercentage=Double.valueOf(df.format(oddPercentage));
        return oddPercentage;
    }

    public double calcAwayChanses(){
        double oddPercentage=1/getAwayClub().getBettingCoefficient()*100;
        DecimalFormat df = new DecimalFormat("#.##");
        oddPercentage=Double.valueOf(df.format(oddPercentage));
        return oddPercentage;
    }

    public void setHomeClub(ClubData homeClub) {
        HomeClub = homeClub;
    }

    public void setAwayClub(ClubData awayClub) {
        AwayClub = awayClub;
    }
}
