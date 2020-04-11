package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    ImageButton imagenPerfil;
    EditText nombreUsuario;
    EditText contraseña;
    EditText contraseñaReply;
    EditText correo;
    Spinner sexo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Tomo los datos de la vista
        imagenPerfil = findViewById(R.id.userIMG);
        nombreUsuario = findViewById(R.id.nombreUsuarioRegistro);
        contraseña = findViewById(R.id.contraseñaRegistro);
        contraseñaReply = findViewById(R.id.repetirContraseña);
        correo = findViewById(R.id.correoElectronico);
        sexo = findViewById(R.id.sexoRegistro);
        //Actualizo las credenciales de autenticacion
        mAuth = FirebaseAuth.getInstance();
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent change = new Intent(this, InicioDeSesión.class);
            change.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            change.putExtra("user", currentUser.getEmail());
            startActivity(change);
            finish();
        } else {
            //email.setText("");
            //password.setText("");
        }
    }

    public void registro(View v){

        if(validateForm()) {
            String email = correo.getText().toString().trim();
            String contrase = contraseña.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(email, contrase).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registro.this, "Registro realizado con exito", Toast.LENGTH_LONG).show();
                        updateUI(mAuth.getCurrentUser());
                    }
                }
            });
        }
    }

    public boolean validateForm(){
        boolean valid = true;
        String email = correo.getText().toString();
        if (TextUtils.isEmpty(email)) {
            correo.setError("Informacion Requerida");
            valid = false;
        }
        else{
            Pattern direccionCorrecta = Pattern.compile("^*@*.*");
            Matcher analisis = direccionCorrecta.matcher(email);
            if(!analisis.find()){
                correo.setError("Direccion no valida");
                valid = false;
            }
        }
        String password = contraseña.getText().toString();
        if (TextUtils.isEmpty(password)) {
            contraseña.setError("Informacion Requerida");
            valid = false;
        }
        else{
            if(password.length() < 6){
                contraseña.setError("Requiere mas de 6 caracteres");
                valid = false;
            }
        }
        String passwordReply = contraseñaReply.getText().toString();
        if(TextUtils.isEmpty(passwordReply)){
            contraseñaReply.setError("Informacion Requerida");
            valid = false;
        }
        else{
            if(!passwordReply.equals(password)){
                contraseñaReply.setError("No coincide con la contraseña");
                valid = false;
            }
        }
        return valid;
    }
}
