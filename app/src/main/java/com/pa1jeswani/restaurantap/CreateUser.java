package com.pa1jeswani.restaurantap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;
import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukeshsolanki.sociallogin.facebook.FacebookHelper;
import com.mukeshsolanki.sociallogin.facebook.FacebookListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateUser extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, FacebookListener {

    //Declare
    private DatePickerDialog.OnDateSetListener mDateListener;
    private FirebaseAuth mAuth;
    private MaterialEditText etUName, etUEmaiId, etUPhNo, etUPswd, etUCPswd, etUDob, etUFoodPref, etUCurLoc;
    private ActionProcessButton btnUSignUp;
    private ImageView googleSignUp, fbSignUp, ivProfile;
    private GoogleApiClient mLocationClient;
    private GoogleSignInClient mGoogleSignInClient;
    private FacebookHelper mFB;
    private Location mLastLoc;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //Binding
        mAuth = FirebaseAuth.getInstance();
        etUName = findViewById(R.id.etUName);
        etUEmaiId = findViewById(R.id.etUEmailID);
        etUPhNo = findViewById(R.id.etUPhNo);
        etUCPswd = findViewById(R.id.etUCPswd);
        etUPswd = findViewById(R.id.etUPswd);
        etUDob = findViewById(R.id.etUDob);
        etUCurLoc = findViewById(R.id.etUCurLoc);
        etUFoodPref = findViewById(R.id.etUFoodPref);
        btnUSignUp = findViewById(R.id.btnUSignUp);
        ivProfile = findViewById(R.id.ivUserProfile);
        googleSignUp = findViewById(R.id.googleSignUp);
        fbSignUp = findViewById(R.id.fbSignUp);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ruach.ttf");
        etUName.setTypeface(face);
        etUEmaiId.setTypeface(face);
        etUPhNo.setTypeface(face);
        etUDob.setTypeface(face);
        etUFoodPref.setTypeface(face);
        etUPswd.setTypeface(face);
        etUCPswd.setTypeface(face);
        etUCurLoc.setTypeface(face);

        //intit DB REF

        //init fb
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(this);
        mFB = new FacebookHelper(this);

        //location client
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this).addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mLocationClient = builder.build();

        //Google sign in  client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Getting DOB
        etUDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(CreateUser.this,
                        android.R.style.Theme_Black,
                        mDateListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                etUDob.setText(date);
            }
        };

        //getting Food Pref
        etUFoodPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodDialog();
            }
        });

        //Google SignUp
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding Password
                etUCPswd.setHeight(0);
                etUPswd.setHeight(0);
                Toast.makeText(CreateUser.this,
                        "Please Fill Other Fields", Toast.LENGTH_SHORT).show();

                //Actual Google Authentication
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 500);
                Toast.makeText(CreateUser.this,
                        "Gone to Google", Toast.LENGTH_SHORT).show();
                //Normal validation
                String uname = etUName.getText().toString();
                String uemail = etUEmaiId.getText().toString();
                String uphno = etUPhNo.getText().toString();
                String upswd = etUPswd.getText().toString();
                String ucpswd = etUCPswd.getText().toString();
                String ufpr = etUFoodPref.getText().toString();
                String udob = etUDob.getText().toString();
                String uloc = etUCurLoc.getText().toString();
                //All user Data is authentic
                if (validateUserData(uname, uemail, upswd, ucpswd, uphno, ufpr, udob, uloc)) {
                    addNewUser(uname, uemail, upswd, ucpswd, uphno, ufpr, udob, uloc);
                 Intent i = new Intent(CreateUser.this,UserMainActivity.class);
                 startActivity(i);
                 finish();
                }
                }
        });

        //Fb SignUP
        fbSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateUser.this,UserMainActivity.class);
                startActivity(i);
                //Normal validation

            }
        });

        //Normal btnSignUp Function
        btnUSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = etUName.getText().toString();
                String uemail = etUEmaiId.getText().toString();
                String uphno = etUPhNo.getText().toString();
                String upswd = etUPswd.getText().toString();
                String ucpswd = etUCPswd.getText().toString();
                String ufpr = etUFoodPref.getText().toString();
                String udob = etUDob.getText().toString();
                String uloc = etUCurLoc.getText().toString();
                //All user Data is authentic
                if (validateUserData(uname, uemail, upswd, ucpswd, uphno, ufpr, udob, uloc)) {
                    btnUSignUp.setMode(ActionProcessButton.Mode.PROGRESS);
                createNewUser(uname,uemail,uphno,ucpswd,ufpr,udob,uloc);
                    btnUSignUp.setProgress(100);
                    Intent i = new Intent(CreateUser.this,UserMainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

        });
    }

    private void addNewUser(String uname, String uemail, String upswd, String ucpswd, String uphno, String ufpr, String udob, String uloc) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        String uid = dbRef.push().getKey();
        User newUser  = new User(uid,uname,uemail,uphno,ucpswd,udob,ufpr,uloc);
        dbRef.child(uid).setValue(newUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFB.onActivityResult(requestCode,resultCode,data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 500) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,
                        "Google sign in failed"+ e, Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Toast.makeText(this,
                "firebaseAuthWithGoogle:" + account.getId(), Toast.LENGTH_LONG).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(CreateUser.this,
                                    "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email =  user.getEmail();
                            etUEmaiId.setText(email);
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateUser.this,
                                    "signInWithCredential:failure"+task.getException(), Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    //creating New User
    private void createNewUser(final String uname, final String uemail, final String uphno, final String ucpswd, final String ufpr, final String udob, final String uloc) {
        mAuth.createUserWithEmailAndPassword(uemail, ucpswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = mAuth.getCurrentUser();
                addNewUser(uname,user.getEmail(),ucpswd,ucpswd,uphno,ufpr,udob,uloc);
            } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(CreateUser.this,
            "createUserWithEmail:failure"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                // ...
                    }
                });
    }


    private void openFoodDialog() {
        String[] items = getResources().getStringArray(R.array.food);
        new LovelyChoiceDialog(this, R.style.CheckBoxTintTheme)
                .setTopColorRes(R.color.DarkRed)
                .setTitle("Choose Your Choices")
                .setIcon(R.drawable.ic_cake_black_24dp)
                .setItemsMultiChoice(items, new LovelyChoiceDialog.OnItemsSelectedListener<String>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<String> items) {
                        Toast.makeText(CreateUser.this,
                                "You selected" + items,
                                Toast.LENGTH_SHORT)
                                .show();
                        etUFoodPref.setText("" + items.toString());
                    }
                })
                .setConfirmButtonText("Confirm")
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (mLocationClient != null) {
            mLocationClient.connect();
        }
    }

    //Validation Function
    public boolean validateUserData(String unm, String uemail, String upswd,
                                    String ucpswd, String uphno, String ucFoodPr,
                                    String uDob, String uLoc) {
        if (unm.length() < 3 || unm.length() > 20) {
            etUName.setError("Invalid Name");
            etUName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(uemail) || !Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            etUEmaiId.setError("Invalid Email");
            etUEmaiId.requestFocus();
            return false;
        } else if (uphno.length() != 10) {
            etUPhNo.setError("Invalid Number");
            etUPhNo.requestFocus();
            return false;
        } else if (upswd.length() < 8) {
            etUPswd.setError("Too Short Password");
            etUPswd.requestFocus();
            return false;
        } else if (!(upswd.equals(ucpswd))) {
            etUCPswd.setError("Not Matching");
            etUCPswd.requestFocus();
            return false;
        } else if (uDob.length() < 9) {
            etUDob.setError("Please Select DOB");
            etUDob.requestFocus();
            return false;
        } else if (ucFoodPr.length() < 4) {
            etUFoodPref.setError("please Select Food Pref");
            etUFoodPref.requestFocus();
            return false;
        } else if (uLoc.length() < 5) {
            etUCurLoc.setError("Turn On GPS");
            etUCurLoc.requestFocus();
            return false;
        } else
            return true;

    }

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
                    etUCurLoc.setText(fa.getAddressLine(0));
                }
                else
                {
                    etUCurLoc.setText("UMMMM Not foound");
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
            }
            }
            }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "ConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "ConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInFail(String s) {

        Toast.makeText(this,
                "Error"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInSuccess(String authToken, String userId) {
        Toast.makeText(this,
                "Userid"+userId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFBSignOut() {

    }
}
