package com.example.ryhmakaakkoapplication;
/**
 * Steps luokka on vastuussa askelmittauksien kirjaamisesta ja antaa askeleiden mäåärän kutsuttua steps.value().
 * @author Joni Tahvanainen (RaikasNeiti)
 * @version 6.9 12/2020
 */
public class Steps {
    /*
    Askelmäärä.
     */
    private int numSteps;


    /**
     * Määrätään alussa, että mikä on askeleiden määrä, tässä tapauksessa se on aina alussa 0.
     */

    public Steps (){
        this.numSteps = 0;


    }

    /**
     * Tällä methodilla voimme korottaa kokonais askelmäärää yhdellä.
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
     * Tällä methodilla kokonaisaskelmäärä nollataan.
     */
    public void reset(){


        this.numSteps = 0;
    }


}
