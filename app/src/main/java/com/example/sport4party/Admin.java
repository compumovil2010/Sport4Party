package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
    }

    public void cerrarSesion(View v){
        mAuth.signOut();
        Intent change = new Intent(this, InicioDeSesión.class);
        change.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_LONG).show();
        startActivity(change);
        finish();
    }
}
