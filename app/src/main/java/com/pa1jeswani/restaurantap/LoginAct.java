package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hanks.htextview.base.HTextView;

//import info.hoang8f.widget.FButton;

public class LoginAct extends AppCompatActivity {

    private KenBurnsView kv;
    private EditText etId,etPswd;
    private HTextView tvNewAcc;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etId = findViewById(R.id.etId);
        etPswd = findViewById(R.id.etPswd);
        tvNewAcc = findViewById(R.id.tvNewAcc);
        btnLogin= findViewById(R.id.btnULogin);
        mAuth = FirebaseAuth.getInstance();

        final String utype = getIntent().getStringExtra("type");
        Toast.makeText(this, "For"+utype, Toast.LENGTH_LONG).show();
        kv = findViewById(R.id.kvImg);
        kv.resume();
        tvNewAcc.animateText("New User?? Create new Account...");
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/ruach.ttf");
        tvNewAcc.setTypeface(face);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(utype.equals("Business"))
                {
                    Intent i = new Intent(LoginAct.this,ManagerMainAct.class);
                    startActivity(i);
                }
                else {
                    String um = etId.getText().toString();
                    String upsw = etPswd.getText().toString();
                    if (um.length() < 5) {
                        etId.setError("Invalid ID");
                        etId.requestFocus();
                        return;
                    } else if (upsw.length() < 8) {
                        etPswd.setError("Too SHort");
                        etPswd.requestFocus();
                        return;
                    } else {
                        loginwithunps(um, upsw);
                    }
                }
            }
        });

        tvNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(utype.equals("Business"))
                {
                Intent i = new Intent(LoginAct.this,CreateRest.class);
                startActivity(i);
                }
                else{
                Intent i = new Intent(LoginAct.this,CreateUser.class);
                startActivity(i);
                }
            }
        });

    }

    private void loginwithunps(String um, String upsw) {
        mAuth.signInWithEmailAndPassword(um, upsw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginAct.this,
                                    "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginAct.this, "createUserWithEmail:failure\n"+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    }

