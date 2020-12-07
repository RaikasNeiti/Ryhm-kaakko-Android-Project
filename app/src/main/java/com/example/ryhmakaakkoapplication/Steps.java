package com.example.ryhmakaakkoapplication;

public class Steps {
    private int numSteps;
    /**
     * tämä class on vastuussa askelmittauksien nostamisesta ja arvon tallentamisesta.
     *
     */



    public Steps (){
        this.numSteps = 0;

    }

    public void add(){
        /**
         * Askelmäärään lisätään 1.
         */

        this.numSteps++;

    }
    public int value(){

        /**
         * palauttaa askelmäärään.
         */

        return this.numSteps;

    }
    public void reset(){
        /**
         * resetoi askelmäärään.
         */

        this.numSteps = 0;
    }


}
