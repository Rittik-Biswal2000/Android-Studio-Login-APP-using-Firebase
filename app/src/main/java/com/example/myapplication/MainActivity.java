package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText usermail, userpassword;
    Button lgin, sgup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Log In");

        mFirebaseAuth= FirebaseAuth.getInstance();
        usermail= findViewById(R.id.umail);
        userpassword= findViewById(R.id.upwd);
        lgin= findViewById(R.id.login);
        sgup=findViewById(R.id.signup);


        mAuthStateListner=new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser!= null){
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,Home.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Login!!", Toast.LENGTH_SHORT).show();;
                }
            }
        };
        sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intologin = new Intent(MainActivity.this, signup.class);
                startActivity(intologin);
            }
        });
        lgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid = usermail.getText().toString();
                String pwd = userpassword.getText().toString();

                if (mailid.isEmpty()){
                    usermail.setError("Please enter the email");
                    usermail.requestFocus();
                }
                else if (pwd.isEmpty()){
                    userpassword.setError("Please Enter user Password");
                    userpassword.requestFocus();
                }
                else if (mailid.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are Empty!!",Toast.LENGTH_SHORT).show();
                }
                else if (!(mailid.isEmpty() && pwd.isEmpty())){
                   mFirebaseAuth.signInWithEmailAndPassword(mailid,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (!task.isSuccessful()){
                               Toast.makeText(MainActivity.this, "Login Error, Please Login Again!!!",Toast.LENGTH_SHORT).show();
                           }
                           else{
                               Intent intoHome = new Intent(MainActivity.this, Home.class);
                               startActivity(intoHome);
                           }
                       }
                   });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occurred!!", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }
}
