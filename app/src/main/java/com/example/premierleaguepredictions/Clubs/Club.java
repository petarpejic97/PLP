package com.example.premierleaguepredictions.Clubs;

public class Club {
    private String name;
    private int scoredGoals;
    private int recivedGoals;
    private int wonGMatches;
    private int drawMatches;
    private int lostMatches;

    public Club() {
        this.name = "";
        this.scoredGoals = 0;
        this.recivedGoals = 0;
        this.wonGMatches = 0;
        this.drawMatches = 0;
        this.lostMatches = 0;
    }

    public Club(String name, int scoredGoals, int recivedGoals, int wonGMatches, int drawMatches, int lostMatches) {
        this.name = name;
        this.scoredGoals = scoredGoals;
        this.recivedGoals = recivedGoals;
        this.wonGMatches = wonGMatches;
        this.drawMatches = drawMatches;
        this.lostMatches = lostMatches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return getWonGMatches()*3+getDrawMatches();
    }

    public int getPlayedMatches() {
        return getDrawMatches()+getWonGMatches()+getLostMatches();
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public int getRecivedGoals() {
        return recivedGoals;
    }

    public int getWonGMatches() {
        return wonGMatches;
    }

    public int getDrawMatches() {
        return drawMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }
}
