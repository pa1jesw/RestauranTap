package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class MainActivity extends TutorialActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new Step.Builder().setTitle("This is So Old School")
                .setContent("The Long Waiting time ")
                .setBackgroundColor(Color.parseColor("#1E88E5")) // int background color
                .setDrawable(R.drawable.oldera_icon) // int top drawable
                .setSummary("This is summary")
                .build());
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#3949AB")) // int background color
                .setDrawable(R.drawable.menu_icon) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#4CAF50")) // int background color
                .setDrawable(R.drawable.mobile_order_icon) // int top drawable
                .setSummary("This is summary")
                .build());
        addFragment(new Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#795548")) // int background color
                .setDrawable(R.drawable.food) // int top drawable
                .setSummary("This is summary")
                .build());
    }

    @Override
    public void finishTutorial() {
        // Your implementation
        Intent i = new Intent(MainActivity.this,OptionSelect.class);
        startActivity(i);
        finish();

    }
}


