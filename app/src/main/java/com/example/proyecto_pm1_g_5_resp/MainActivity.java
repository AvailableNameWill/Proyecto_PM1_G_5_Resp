package com.example.proyecto_pm1_g_5_resp;

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

    private EditText txtLogEmail, txtLogPass;
    private Button btnLogEnter, btnLogReg;

    private FirebaseAuth uAuth; //Nos ayuda con la autenticacion de usuario
    private FirebaseAuth.AuthStateListener authListener; //escucha para uAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chargeObj();

        uAuth = FirebaseAuth.getInstance(); //se obtiene una instancia de autenticacion de FireBase

        authListener = new FirebaseAuth.AuthStateListener() { //Nos ayuda a saber si el usuario ya esta
            @Override //autenticado y verificar si ya hizo login
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usr = uAuth.getCurrentUser(); //se obtiene el usuario

                if(usr != null){
                    if(!usr.isEmailVerified()){
                        Toast.makeText(MainActivity.this, "E-Mail no verificado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Welcome!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        btnLogEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        btnLogReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp();
            }
        });
    }

    protected void onStart(){ //empieza a escuchar los eventos de uAuth
        super.onStart();
        uAuth.addAuthStateListener(authListener);
    }

    protected void onStop(){ //deja de escuchar los eventos de uAuth
        super.onStop();
        if(authListener != null) uAuth.removeAuthStateListener(authListener);
    }

    public void Login(){ //metodo para iniciar sesion
        String umail = txtLogEmail.getText().toString().trim();
        String upass = txtLogPass.getText().toString().trim();

        //metodo de Firebase para iniciar sesion con correo y contraseña
        uAuth.signInWithEmailAndPassword(umail, upass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Hubo un error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Inicio_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void singUp(){
        Intent intent = new Intent(getApplicationContext(), Sign_Up_Activity.class);
        startActivity(intent);
    }

    private void chargeObj(){
        txtLogEmail = (EditText) findViewById(R.id.txtLogEmail);
        txtLogPass  = (EditText) findViewById(R.id.txtLogPass);
        btnLogEnter = (Button)   findViewById(R.id.btnLogEnter);
        btnLogReg   = (Button)   findViewById(R.id.btnLogReg);
    }
}