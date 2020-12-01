package com.example.ryhmakaakkoapplication;

public class Calorie {
    private int numCalorie;

    public Calorie() {
        this.numCalorie = 0;
    }
    public void addCalorie(int amount) {
        this.numCalorie += amount;
    }
    public int calorie() {
        return this.numCalorie;
    }
    public void reset() {
        this.numCalorie = 0;
    }
}
