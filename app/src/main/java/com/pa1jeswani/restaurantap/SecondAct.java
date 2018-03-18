package com.pa1jeswani.restaurantap;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SecondAct extends AppCompatActivity implements View.OnClickListener{

    CheckedTextView ctv1 , ctv2 , ctv3 ;
    Button btnSelect , btnOp , btnSave1 , btnPic;
    Spinner spnFeet , spnInch ,spnFeet1 , spnInch1 ,spnFeet12 , spnInch12 , spnFeet123 , spnInch123 ;
    EditText etOrder , etDist , etTime , etStaffName , etPhone , etEmail , etManager , etManPhone , etEmailManager;


    CharSequence[] colours = {"Vegetarian", "Italian", "Thai", "Asian", "South-Indian", "Take Out"};
    ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();

    CharSequence[] colours1 = {"Day 1 - 3 " , "day 1 - 5" , "day 1 -7" , "day 1-10" , "day 1-15"};
    ArrayList<CharSequence> selectedColours1 = new ArrayList<CharSequence>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etOrder = (EditText) findViewById(R.id.etOrder);
        etDist = (EditText) findViewById(R.id.etDist);
        etTime = (EditText) findViewById(R.id.etTime);
        etStaffName = (EditText) findViewById(R.id.etStaffName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etManager = (EditText) findViewById(R.id.etManager);
        etManPhone = (EditText) findViewById(R.id.etManPhone);
        etEmailManager = (EditText) findViewById(R.id.etEmailManager);

        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnOp = (Button) findViewById(R.id.btnOp);
        btnSave1 = (Button) findViewById(R.id.btnSave1);
        btnPic = (Button) findViewById(R.id.btnPic);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);//for day start
        spnFeet1 = (Spinner) findViewById(R.id.spnFeet1);
        spnInch1 = (Spinner) findViewById(R.id.spnInch1);//for day end
        spnFeet12 = (Spinner) findViewById(R.id.spnFeet12);
        spnInch12 = (Spinner) findViewById(R.id.spnInch12); // for night start
        spnFeet123 = (Spinner) findViewById(R.id.spnFeet123);
        spnInch123 = (Spinner) findViewById(R.id.spnInch123);//for night end

        btnSelect.setOnClickListener(this);
        btnOp.setOnClickListener(this);
        btnSave1.setOnClickListener(this);
        btnPic.setOnClickListener(this);
        ctv1 = (CheckedTextView) findViewById(R.id.ctv1);
        ctv2 = (CheckedTextView) findViewById(R.id.ctv2);
        ctv3 = (CheckedTextView) findViewById(R.id.ctv3);

        String[] feet = {"1", "2", "3", "4", "5", "6", "7", "8" , "9" ,"10" , "11" ,"12"}; //what is inside
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet);//to get view
        spnFeet.setAdapter(ad1); // to set

        String[] inch = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12", "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inch);//inch ko iasa view dikhaneka.
        spnInch.setAdapter(ad2);

        String[] feet1 = {"1", "2", "3", "4", "5", "6", "7", "8" , "9" ,"10" , "11" ,"12"}; //what is inside
        ArrayAdapter<String> ad3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet1);//to get view
        spnFeet1.setAdapter(ad3); // to set

        String[] inch1 =  {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12", "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
        ArrayAdapter<String> ad4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inch1);//inch ko iasa view dikhaneka.
        spnInch1.setAdapter(ad4);

        String[] feet2 = {"1", "2", "3", "4", "5", "6", "7", "8" , "9" ,"10" , "11" ,"12"}; //what is inside
        ArrayAdapter<String> ad5 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet2);//to get view
        spnFeet12.setAdapter(ad5); // to set

        String[] inch2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12", "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44", "45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
        ArrayAdapter<String> ad6 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inch2);//inch ko iasa view dikhaneka.
        spnInch12.setAdapter(ad6);

        String[] feet3 = {"1", "2", "3", "4", "5", "6", "7", "8" , "9" ,"10" , "11" ,"12"}; //what is inside
        ArrayAdapter<String> ad7 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feet3);//to get view
        spnFeet123.setAdapter(ad7); // to set

        String[] inch3 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" , "12", "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44", "45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
        ArrayAdapter<String> ad8 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inch3);//inch ko iasa view dikhaneka.
        spnInch123.setAdapter(ad8);

        if (ctv1.isChecked())
            ctv1.setChecked(false);
        else
            ctv1.setChecked(true);

    }//end of oncreate

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSelect:
                showSelectColoursDialog();
                break;

            case R.id.btnOp:
                showSelectColoursDialog1();
                break;

            case R.id.btnPic:
                Intent gallery = new Intent(Intent.ACTION_PICK);
                File picture= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data , "image/*");
                startActivityForResult(gallery , 27);
                break;

            case R.id.btnSave1:
                if(etOrder.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter minimum order value", Toast.LENGTH_LONG).show();
                    etOrder.requestFocus();
                    return;
                }//end of if
                if(etDist.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Delivery Distance", Toast.LENGTH_LONG).show();
                    etDist.requestFocus();
                    return;
                }//end of if
                if(etTime.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Delivery Time", Toast.LENGTH_LONG).show();
                    etTime.requestFocus();
                    return;
                }//end of if
                if(etStaffName.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Name of staff taking order", Toast.LENGTH_LONG).show();
                    etStaffName.requestFocus();
                    return;
                }//end of if
                if(etPhone.length() != 10)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    etPhone.requestFocus();
                    return;
                }//end of if
                if(etEmail.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter valid email ID", Toast.LENGTH_LONG).show();
                    etEmail.requestFocus();
                    return;
                }//end of if
                if(etManager.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Manager Name", Toast.LENGTH_LONG).show();
                    etManager.requestFocus();
                    return;
                }//end of if
                if(etManPhone.length() != 10)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    etManPhone.requestFocus();
                    return;
                }//end of if
                if(etEmailManager.length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Email ID to receive orders", Toast.LENGTH_LONG).show();
                    etEmailManager.requestFocus();
                    return;
                }//end of if

                Intent i1 = new Intent(SecondAct.this , ThirdAct.class);
                startActivity(i1);
                break;
            default:
                break;
        }

        //finish();
    }//end of onclick

    void onChangeSelectedColours1() {
        StringBuilder stringBuilder = new StringBuilder();
        for(CharSequence colour1 : selectedColours1)
            stringBuilder.append(colour1 + " ,");
        btnOp.setText(stringBuilder.toString());
    }//end of function

    void showSelectColoursDialog1() {
        boolean[] checkedColours1 = new boolean[colours1.length];
        int count = colours1.length;

        for (int i = 0; i < count; i++)
            checkedColours1[i] = selectedColours1.contains(colours1[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener1 = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedColours1.add(colours1[which]);
                else
                    selectedColours1.remove(colours1[which]);

                onChangeSelectedColours1();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Operating Days");
        builder.setMultiChoiceItems(colours1, checkedColours1, coloursDialogListener1);

        AlertDialog dialog = builder.create();
        dialog.show();

    } //end of fun


    void onChangeSelectedColours() {
        StringBuilder stringBuilder = new StringBuilder();
        for(CharSequence colour : selectedColours)
            stringBuilder.append(colour + " ,");

        btnSelect.setText(stringBuilder.toString());
    }//end of function

    void showSelectColoursDialog() {
        boolean[] checkedColours = new boolean[colours.length];
        int count = colours.length;

        for (int i = 0; i < count; i++)
            checkedColours[i] = selectedColours.contains(colours[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedColours.add(colours[which]);
                else
                    selectedColours.remove(colours[which]);

                onChangeSelectedColours();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Colours");
        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);

        AlertDialog dialog = builder.create();
        dialog.show();

    }//showfun


}///end of main
