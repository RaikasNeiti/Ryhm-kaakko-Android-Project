package com.example.ryhmakaakkoapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator {


    public void stepsColor (int stepGoal, int stepcount, TextView stepsView)  {       //säätää ympyröiden väriä tavoitteen mukaan

        StateListDrawable stepsViewBackground = (StateListDrawable) stepsView.getBackground();

        if(stepcount < (stepGoal/2))   {
            stepsViewBackground.setColorFilter(Color.parseColor("#be1f1d"), PorterDuff.Mode.SRC_ATOP);
            Log.d("test1", "1");
            Log.d("test1", Integer.toString(stepcount));
            Log.d("test1", Integer.toString(stepGoal));

        } else if (stepcount < stepGoal) {
            stepsViewBackground.setColorFilter(Color.parseColor("#ffb400"), PorterDuff.Mode.SRC_ATOP);
            Log.d("test2", "2");
        } else  {
            stepsViewBackground.setColorFilter(Color.parseColor("#005800"), PorterDuff.Mode.SRC_ATOP);
            Log.d("test2", "3");
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

    public String percentageCalc(int stepcount, int stepGoal)  {
        int percentage = (stepcount/stepGoal)*100;
        String strPercentage = percentage + "%";
        Log.d("test2", strPercentage);
        Log.d("test2", Integer.toString(stepGoal));
        Log.d("dsad2", Integer.toString(stepcount));
        return strPercentage;
    }



}
