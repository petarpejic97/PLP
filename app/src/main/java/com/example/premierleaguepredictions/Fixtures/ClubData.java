package com.example.premierleaguepredictions.Fixtures;

import android.util.Log;

public class ClubData {
    private String name;
    private double bettingCoefficient;

    public ClubData(String name, double bettingCoefficient) {
        this.name = name;
        this.bettingCoefficient = bettingCoefficient;
    }
    public ClubData(String name,String bettingCoefficient){
        Log.w("ERROR","Usao u pravi konstuktor u ClubData");
        this.name = name;
        this.bettingCoefficient = Double.parseDouble(bettingCoefficient);

    }
    public ClubData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBettingCoefficient() {
        return bettingCoefficient;
    }

    public void setBettingCoefficient(double bettingCoefficient) {
        this.bettingCoefficient = bettingCoefficient;
    }
}
