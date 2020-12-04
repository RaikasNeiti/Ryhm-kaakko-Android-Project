package com.example.ryhmakaakkoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class activity_goal_entry extends AppCompatActivity {

    private RangeSlider sugarslider;
    private Slider stepslider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_entry);
        sugarslider = findViewById(R.id.sugarslider);
        stepslider = findViewById(R.id.stepslider);
        sugarslider.setLabelFormatter(value -> value + " mmol/L");
        stepslider.setLabelFormatter(value -> value + " askelta");

        stepslider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                SharedPreferences sp =
                        getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
                SharedPreferences.Editor spe = sp.edit();
                spe.putInt("stepGoal", Math.round(value));
                spe.apply();
            }
        });

        sugarslider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = slider.getValues();
                float min = Collections.min(values);
                float max = Collections.max(values);
                SharedPreferences sp =
                        getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
                SharedPreferences.Editor spe = sp.edit();
                spe.putFloat("minSugar", min);
                spe.putFloat("maxSugar", max);
                spe.apply();
            }
        });



    }
}