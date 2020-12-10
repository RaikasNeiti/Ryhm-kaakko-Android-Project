package com.example.ryhmakaakkoapplication;
/**
 * Steps-luokka on vastuussa askelmittauksien kirjaamisesta ja antaa askeleiden määrän funktiolla steps.value().
 * @author Teemu Olkkonen ja Joni Tahvanainen (Raikasneiti)
 * @version 6.9 12/2020
 */
public class Steps {
    /*
    Askelmäärä.
     */
    private int numSteps;


    /**
     * Määrätään alussa askelten määrä, tässä tapauksessa se on aina alussa 0.
     */

    public Steps (){
        this.numSteps = 0;


    }

    /**
     * Korottaa kokonaisaskelmäärää yhdellä.
     */

    public void add(){


        this.numSteps++;

    }

    /**
     * Palauttaa kokonaisaskelmäärän.
     * @return int numSteps
     */

    public int value(){




        return this.numSteps;

    }

    /**
     * Tällä metodilla kokonaisaskelmäärä nollataan.
     */
    public void reset(){


        this.numSteps = 0;
    }


}
