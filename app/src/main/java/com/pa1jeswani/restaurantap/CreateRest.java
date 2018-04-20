package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class CreateRest extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    EditText etOwnEmail, etPass1, etPass2, etRestName, etReg, etResAddress, etPostal, etOwnerName, etContact, etLocation, etCity, etState;
    Button btnSave, btnSelPic1, btnSelPic2, btnSelPic3;
    RadioButton rbYes, rbNo;
    private FirebaseAuth mAuth;
    private GoogleApiClient mLocationClient;
    CheckedTextView ctv1, ctv2, ctv3;
    private Location mLastLoc;
    DatabaseReference dbRef;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (mLocationClient != null) {
            mLocationClient.connect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rest);

        etOwnEmail = (EditText) findViewById(R.id.etOwnEmail);
        etPass1 = (EditText) findViewById(R.id.etPass1);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        etRestName = (EditText) findViewById(R.id.etRestName);
        etReg = (EditText) findViewById(R.id.etReg);
        etResAddress = (EditText) findViewById(R.id.etResAddress);
        etPostal = (EditText) findViewById(R.id.etPostal);
        etOwnerName = (EditText) findViewById(R.id.etOwnerName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etContact = (EditText) findViewById(R.id.etContact);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSelPic1 = (Button) findViewById(R.id.btnSelPic1);
        btnSelPic2 = (Button) findViewById(R.id.btnSelPic2);
        btnSelPic3 = (Button) findViewById(R.id.btnSelPic3);
        ctv1 = (CheckedTextView) findViewById(R.id.ctv1);
        ctv2 = (CheckedTextView) findViewById(R.id.ctv2);
        ctv3 = (CheckedTextView) findViewById(R.id.ctv3);

        btnSelPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data, "image/*");
                startActivityForResult(gallery, 20);
            }
        });//end of 1

        btnSelPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data, "image/*");
                startActivityForResult(gallery, 22);
            }
        }); // end of 2

        btnSelPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String photo = picture.getPath();
                Uri data = Uri.parse(photo);
                gallery.setDataAndType(data, "image/*");
                startActivityForResult(gallery, 25);
            }
        });//end of 3


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OwnerEmail = etOwnEmail.getText().toString();
                String pass1 = etPass1.getText().toString();
                String pass2 = etPass2.getText().toString();
                String RestName = etRestName.getText().toString();
                String ResAddress = etResAddress.getText().toString();
                String postal = etPostal.getText().toString();

                String OwnerName = etOwnerName.getText().toString();
                String contact = etContact.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                String location = etLocation.getText().toString();

                if (ctv1.isChecked())
                    ctv1.setChecked(false);
                else
                    ctv1.setChecked(true);

                if (OwnerName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Name", Toast.LENGTH_LONG).show();
                    etOwnerName.requestFocus();
                    return;
                }//end of if

                if (OwnerEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    etOwnEmail.requestFocus();
                    return;
                }//end of if
                if (pass1.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                    etPass1.requestFocus();
                    return;
                }//end of if
                if (pass2.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                    etPass2.requestFocus();
                    return;
                }//end of if
                if (!(pass1.equals(pass2))) {
                    Toast.makeText(getApplicationContext(), "Password Not Matching", Toast.LENGTH_LONG).show();
                    etPass2.requestFocus();
                    return;
                }
                if (RestName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Restaurant Name", Toast.LENGTH_LONG).show();
                    etRestName.requestFocus();
                    return;
                }//end of if
                if (ResAddress.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Restaurant Address", Toast.LENGTH_LONG).show();
                    etResAddress.requestFocus();
                    return;
                }//end of if
                if (postal.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Address", Toast.LENGTH_LONG).show();
                    etPostal.requestFocus();
                    return;
                }//end of if
                if (contact.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    etContact.requestFocus();
                    return;
                }//end of if
                if (city.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter City", Toast.LENGTH_LONG).show();
                    etCity.requestFocus();
                    return;
                }//end of if
                if (state.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter State", Toast.LENGTH_LONG).show();
                    etState.requestFocus();
                    return;
                }
                if (location.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Location", Toast.LENGTH_LONG).show();
                    etState.requestFocus();
                    return;
                }
                Intent i = new Intent(getApplicationContext(), SecondAct.class);
                startActivity(i);
                finish();
            }//end of onclick
        });//end of button


    }//end of oncreate

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLoc = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if(mLastLoc!=null)
        {
            double lat=mLastLoc.getLatitude();
            double longt= mLastLoc.getLongitude();

            Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.ENGLISH);
            try {
                List<Address> adresss=geocoder.getFromLocation(lat,longt,2);
                if(adresss!=null)
                {
                    Address fa = adresss.get(0);
                    etLocation.setText(fa.getAddressLine(0));
                }
                else
                {
                    etLocation.setText("UMMMM Not foound");
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}//end of main
