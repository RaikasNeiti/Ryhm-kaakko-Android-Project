package com.example.ryhmakaakkoapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator {


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

    public void sugarColor(float minSugar, float maxSugar, double roundedDouble, TextView bloodsugar)   {

        StateListDrawable sugarViewBackground = (StateListDrawable) bloodsugar.getBackground();

        if((roundedDouble <= maxSugar) && (roundedDouble >= minSugar))    {
            sugarViewBackground.setColorFilter(Color.parseColor("#005800"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            sugarViewBackground.setColorFilter(Color.parseColor("#be1f1d"), PorterDuff.Mode.SRC_ATOP);
        }
    }


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

    public int percentCalc(int stepcount, int stepGoal)  {
        return Math.round(((float) stepcount/stepGoal)*100);
    }

    public String diffCalc(ArrayList<String> latest)    {
       return "+/- " + Math.abs(Float.parseFloat(latest.get(1)) - Float.parseFloat(latest.get(2)));
    }


}
