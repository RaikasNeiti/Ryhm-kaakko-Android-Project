package com.example.ryhmakaakkoapplication;

public class Steps {
    private int numSteps;

    public Steps (){
        this.numSteps = 0;

    }

    public void add(){
        this.numSteps++;

    }
    public int value(){

        return this.numSteps;

    }
    public void reset(){
        this.numSteps = 0;
    }


}
