package com.example.proyecto_pm1_g_5_resp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up_Activity extends AppCompatActivity {

    private EditText txtSignName, txtSignEmail, txtSignPass;
    private Button btnSignEnter;

    private FirebaseAuth uAuth; //Nos ayuda con la autenticacion de usuario
    private FirebaseAuth.AuthStateListener authListener; //escucha para uAuth
    private FirebaseFirestore mFiresstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getObj();

        uAuth = FirebaseAuth.getInstance(); //se obtiene una instancia de autenticacion de FireBase
        mFiresstore = FirebaseFirestore.getInstance();


        btnSignEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp();
            }
        });

        /*authListener = new FirebaseAuth.AuthStateListener() { //Nos ayuda a saber si el usuario ya esta
            @Override //autenticado y verificar si ya hizo login
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {}
        };*/
    }

    /*protected void onStart(){ //empieza a escuchar los eventos de uAuth
        super.onStart();
        uAuth.addAuthStateListener(authListener);
    }

    protected void onStop(){ //deja de escuchar los eventos de uAuth
        super.onStop();
        if(authListener != null) uAuth.removeAuthStateListener(authListener);
    }*/

    public void singUp(){
        String usrNm   = txtSignName.getText().toString().trim();
        String usrmail = txtSignEmail.getText().toString().trim();
        String usrPass = txtSignPass.getText().toString().trim();

        uAuth.createUserWithEmailAndPassword(usrmail, usrPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = uAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id",    id);
                    map.put("name",  usrNm);
                    map.put("email", usrmail);
                    map.put("pass",  usrPass);

                    Toast.makeText(Sign_Up_Activity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                    FirebaseUser fUsr = uAuth.getCurrentUser();
                    fUsr.sendEmailVerification();

                    mFiresstore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            Toast.makeText(Sign_Up_Activity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Sign_Up_Activity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString() + "error al guardar");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sign_Up_Activity.this, "Hubo un error al crear usuario", Toast.LENGTH_SHORT).show();
                System.out.println(e.toString() + "error al crear");
            }
        });
    }

    private void getObj(){
        txtSignName  = (EditText) findViewById(R.id.txtSignName);
        txtSignEmail = (EditText) findViewById(R.id.txtSignMail);
        txtSignPass  = (EditText) findViewById(R.id.txtSignPass);
        btnSignEnter = (Button)   findViewById(R.id.btnSignEnter);
    }
}