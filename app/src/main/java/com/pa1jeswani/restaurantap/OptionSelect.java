package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import info.hoang8f.widget.FButton;

public class OptionSelect extends AppCompatActivity {

    ActionProcessButton btnDiners,btnBusiness;
    TextView tvDescLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_select);
        btnBusiness=findViewById(R.id.btnBusiness);
        btnDiners=findViewById(R.id.btnDiners);
        tvDescLogo=findViewById(R.id.tvDescLogo);


        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/myfont.ttf");
        tvDescLogo.setTypeface(face);

        btnDiners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionSelect.this,LoginAct.class);
                i.putExtra("type","Diners");
                startActivity(i);
                finish();
            }
        });

        btnBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionSelect.this,LoginAct.class);
                i.putExtra("type","Business");
                startActivity(i);
                finish();
            }
        });
    }
}
