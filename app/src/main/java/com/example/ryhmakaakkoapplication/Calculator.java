package com.example.ryhmakaakkoapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator {

    private int stepGoal;
    private float minSugar;
    private float maxSugar;
    private int stepcount;

    public Calculator() {
        this.stepGoal = stepGoal;
        this.minSugar = minSugar;
        this.maxSugar = maxSugar;
        this.stepcount = stepcount;
    }

    public void colorCalc(int stepGoal, int stepcount, float minSugar, float maxSugar, double roundedDouble, TextView stepsView, TextView bloodsugar)  {       //säätää ympyröiden väriä tavoitteen mukaan

        StateListDrawable stepsViewBackground = (StateListDrawable) stepsView.getBackground();
        StateListDrawable sugarViewBackground = (StateListDrawable) bloodsugar.getBackground();

        if(stepcount < (stepGoal/2))   {
            stepsViewBackground.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        } else if (stepcount < stepGoal) {
            stepsViewBackground.setColorFilter(Color.parseColor("#FFFF00"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            stepsViewBackground.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_ATOP);
        }

        if((roundedDouble <= maxSugar) && (roundedDouble >= minSugar))    {
            sugarViewBackground.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            sugarViewBackground.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
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

    public String percentageCalc(int stepcount, int stepGoal)  {
        int percentage = (stepcount/stepGoal)*100;
        String strPercentage = percentage + "%";
        return strPercentage;
    }



}
