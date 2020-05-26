package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InicioDeSesión extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private EditText mUser;
    private EditText mPassword;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        mUser = findViewById(R.id.nombreUsuario);
        mPassword = findViewById(R.id.contraseña);

        mAuth = FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance();
        //progressDialog = new ProgressDialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(final FirebaseUser currentUser){
        if(currentUser!=null){

            //----------------- PRUEBAS FIREBASE


            Almacenamiento almacenamientoBase = new Almacenamiento(){
                @Override
                public void leerDatos(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                    for(String i : datos.keySet()){
                        Log.i("DATOS", "KEY: " + i + " VALUE: " + datos.get(i));
                    }
                    if(datos.containsKey("amigos")){
                        HashMap<String,String> amigitos= (HashMap<String,String>)datos.get("amigos");
                        for(String i : amigitos.keySet()){
                            Log.i("AMIGUITOS", "KEY: " + i + " VALUE: " + datos.get(i));
                        }
                    }
                }
            };


            almacenamientoBase.loadOnce("Jugadores/");

            //HashMap<String, String> datos
//            almacenamientoBase.leerDatos();

            //----------------------------------

            myRef=database.getReference("Jugador");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapShot : dataSnapshot.getChildren()) {
                        if(singleSnapShot.getKey().equals(mAuth.getUid())){
                            HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
                            if(datos.containsKey("tipo")){
                                if(datos.get("tipo").equals("1")){
                                    Intent change = new Intent(InicioDeSesión.this, Admin.class);
                                    change.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(change);
                                    finish();
                                }
                                else{
                                    Intent change = new Intent(InicioDeSesión.this, Mapa.class);
                                    change.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    change.putExtra("user", currentUser.getEmail());
                                    startActivity(change);
                                    finish();
                                }
                            }
                        }
                    }
                }@Override public void onCancelled(@NonNull DatabaseError databaseError) { } });
        } else {
            //email.setText("");
            //password.setText("");
        }
    }

    public void toRegistro(View v){
        Intent change = new Intent(this, Registro.class);
        startActivity(change);
    }

    public void toMapa(View v) {

        if(validateForm()){
            String email = mUser.getText().toString().trim();
            String contraseña = mPassword.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Toast.makeText(InicioDeSesión.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }
                    else{
                        Toast.makeText(InicioDeSesión.this, "Credenciales no validas", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mUser.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUser.setError("Informacion Requerida");
            valid = false;
        }
        else{
            Pattern direccionCorrecta = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher analisis = direccionCorrecta.matcher(email);
            if(!analisis.find()){
                mUser.setError("Direccion no valida");
                valid = false;
            }
        }
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Informacion Requerida");
            valid = false;
        }
        else{
            if(password.length() < 6){
                mPassword.setError("Requiere mas de 6 caracteres");
                valid = false;
            }
        }
        return valid;
    }

}
