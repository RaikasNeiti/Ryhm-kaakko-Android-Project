package com.example.ryhmakaakkoapplication;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

    /**
     * Calculator-luokka on elementtien värien määrittelyä ja laskentaa varten.
     * @author Olli Kolkki
     * @version 2.0 12/2020
     */

public class Calculator {

    /**
     * Vaihtaa annettujen TextView-elementin väriä sen mukaan, kuinka lähellä toteutunut arvo on
     * tavoitearvoa
     * @param stepGoal = asetettu askeltavoite
     * @param stepcount = toteutuneet askeleet
     * @param stepsView = elementti, jonka väriä muutetaan
     */
    public void stepsColor (int stepGoal, int stepcount, TextView stepsView)  {       //säätää ympyröiden väriä tavoitteen mukaan

        StateListDrawable stepsViewBackground = (StateListDrawable) stepsView.getBackground();

        if(stepcount < (stepGoal/2))   {
            stepsViewBackground.setColorFilter(Color.parseColor("#be1f1d"), PorterDuff.Mode.SRC_ATOP);
        } else if (stepcount < stepGoal) {
            stepsViewBackground.setColorFilter(Color.parseColor("#ffb400"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            stepsViewBackground.setColorFilter(Color.parseColor("#005800"), PorterDuff.Mode.SRC_ATOP);
        }

    }


    /**
     * Vaihtaa annettujen ProgressBar-elementin väriä sen mukaan, kuinka lähellä toteutunut arvo on
     * tavoitearvoa
     * @param stepGoal tavoitearvo
     * @param stepcount toteutunut arvo
     * @param progressBar elementti, jonka väriä muutetaan
     */
    public void progressColor(int stepGoal, int stepcount, ProgressBar progressBar) {
        if(stepcount < (stepGoal/2))   {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#be1f1d"), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (stepcount < stepGoal) {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#ffb400"), android.graphics.PorterDuff.Mode.SRC_IN);
        } else  {
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#005800"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }


        /**
         * Vaihtaa annetun TextView-elementin väriä sen mukaan, onko mitattu arvo alueen sisällä vai ei
         * @param minSugar minimiarvo
         * @param maxSugar maksimiarvo
         * @param roundedDouble mitattu arvo
         * @param bloodsugar View-elementti
         */
    public void sugarColor(float minSugar, float maxSugar, double roundedDouble, TextView bloodsugar)   {

        StateListDrawable sugarViewBackground = (StateListDrawable) bloodsugar.getBackground();

        if((roundedDouble <= maxSugar) && (roundedDouble >= minSugar))    {
            sugarViewBackground.setColorFilter(Color.parseColor("#005800"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            sugarViewBackground.setColorFilter(Color.parseColor("#be1f1d"), PorterDuff.Mode.SRC_ATOP);
        }
    }

        /**
         * Laskee listan lukujen keskiarvon
         * @param list lista, josta keskiarvo lasketaan
         * @return keskiarvo
         */
    public double avgCalc(ArrayList<Double> list)  {
        double roundedDouble;
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + list.get(i);
        }
        double avg = sum / list.size();
        roundedDouble = Math.round(avg * 100.0) / 100.0;
        return roundedDouble;
    }

        /**
         *Laskee montako prosenttia luku on toisesta
         * @param stepcount askelten määrä
         * @param stepGoal askeltavoite
         * @return montako prosenttia askeleet on askeltavoitteesta
         */
    public int percentCalc(int stepcount, int stepGoal)  {
        return Math.round(((float) stepcount/stepGoal)*100);
    }

        /**
         * Laskee kahden arvon erotuksen
         * @param latest lista, joka sisältää kaksi arvoa
         * @return arvojen erotus
         */
    public String diffCalc(ArrayList<String> latest)    {
       return "+/- " + Math.abs(Float.parseFloat(latest.get(1)) - Float.parseFloat(latest.get(2)));
    }


}
