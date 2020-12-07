package com.example.ryhmakaakkoapplication;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

    /**
     * Calculator-luokka on elementtien värien määrittelyä ja erilaisia matemaattisia operaatioita varten.
     * Sitä käytetään MainActivityssa ja activity_entry_display -luokissa käyttäjän merkintöjen visuaalisen
     * kuvaamisen apuna.
     */

public class Calculator {

    /**
     * Funktio, joka määrittää elementin värin toteutuneiden askelten mukaan. Funktio ottaa parametreiksi askeltavoitteen,
     * askelmäärän ja View-elementin, jonka väriä vaihdetaan. Funktio ei palauta mitään, vaan muuttaa
     *  suoraan elementtien väriä.
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
     * Funktio, joka määrittää ProgressBar -elementin värin toteutuneiden askelten mukaan. Funktio ottaa parametreiksi
     * askelmäärän, askeltavoitteen ja View-elementin, jonka väriä vaihdetaan. Funktio ei palauta mitään, vaan muuttaa
     * suoraan elementtien väriä.
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
     * Funktio, joka määrittää  verensokeri-elementin värin toteutuneiden mittausten mukaan. Funktio ottaa
     * parametreiksi verensokeriarvoalueen ja mitatun verensokeriarvon. Jos arvo on arvoalueen ulkupuolella,
     * elementti värjätään punaiseksi, muuten vihreäksi.
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
     * Keskiarvon laskenta. Funktio ottaa parametriksi kaikki halutun päivän verensokerimerkinnät ja palauttaa niiden
     * keskiarvon. Funktiota käytetään sen määrittelyyn, onko päivän verensokerikeskiarvo verensokeriarvoalueen sisällä.
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
     * Prosentin laskenta. Funktio ottaa parametreiksi askelmäärän ja askeltavoitteen. Sitä käytetään sen laskemiseksi,
     * lähellä käyttäjä on askeltavoiteensa täyttymistä.
     */
    public int percentCalc(int stepcount, int stepGoal)  {
        return Math.round(((float) stepcount/stepGoal)*100);
    }
    /**
     * Erotuksen laskenta. Funktio ottaa parametriksi tietokannasta listan, jossa on haetut kaksi
     * viimeisintä verensokerimerkintää ja palauttaa niiden erotuksen.
     */
    public String diffCalc(ArrayList<String> latest)    {
       return "+/- " + Math.abs(Float.parseFloat(latest.get(1)) - Float.parseFloat(latest.get(2)));
    }


}
