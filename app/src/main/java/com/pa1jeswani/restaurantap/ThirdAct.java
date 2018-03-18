package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class ThirdAct extends AppCompatActivity {
    CheckedTextView ctv1 , ctv2 , ctv3 ;
    Button btnSaveCont , btnPict , btnSelPict1 , btnSelPict2;
    EditText etPan , etGst , etGstPer , etFee , etCharge , etSerFee ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ctv1 = (CheckedTextView) findViewById(R.id.ctv1);
        ctv2 = (CheckedTextView) findViewById(R.id.ctv2);
        ctv3 = (CheckedTextView) findViewById(R.id.ctv3);
        btnPict = findViewById(R.id.btnPict);
        btnSelPict1 = findViewById(R.id.btnSelPict1);
        btnSelPict2 = findViewById(R.id.btnSelPict2);
        etPan = (EditText) findViewById(R.id.etPan);
        etGst = (EditText) findViewById(R.id.etGst);
        etGstPer = (EditText) findViewById(R.id.etGstPer);
        etFee = (EditText) findViewById(R.id.etFee);
        etCharge = (EditText) findViewById(R.id.etCharge);
        etSerFee = (EditText) findViewById(R.id.etSerFee);

        btnSaveCont = (Button) findViewById(R.id.btnSaveCont);

        if (ctv1.isChecked())
            ctv1.setChecked(false);
        else
            ctv1.setChecked(true);

        if (ctv2.isChecked())
            ctv2.setChecked(false);
        else
            ctv2.setChecked(true);

        btnPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);
                File picture= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data , "image/*");
                startActivityForResult(gallery , 29);
            }
        });

        btnSelPict1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                File picture= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data , "image/*");
                startActivityForResult(gallery , 30);
            }
        });

        btnSelPict2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                File picture= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data , "image/*");
                startActivityForResult(gallery , 32);
            }
        });


        btnSaveCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPan.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Invalid PAN/TAN Number", Toast.LENGTH_LONG).show();
                    etPan.requestFocus();
                    return;
                }//end of if
                if(etGst.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Invalid GST Number", Toast.LENGTH_LONG).show();
                    etGst.requestFocus();
                    return;
                }//end of if
                if(etGstPer.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Invalid GST Percentage", Toast.LENGTH_LONG).show();
                    etGstPer.requestFocus();
                    return;
                }//end of if
                if(etFee.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please Enter some amount", Toast.LENGTH_LONG).show();
                    etFee.requestFocus();
                    return;
                }//end of if
                if(etCharge.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter packaging charge", Toast.LENGTH_LONG).show();
                    etCharge.requestFocus();
                    return;
                }//end of if
                if(etSerFee.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter service Fee", Toast.LENGTH_LONG).show();
                    etSerFee.requestFocus();
                    return;
                }//end of if
                Intent i1 = new Intent(ThirdAct.this , FourAct.class);
                startActivity(i1);
            }//end of onclick
        });//end of btn

    }//end of onCreate
}///end of main
