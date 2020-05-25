package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private ImageButton imagenPerfil;
    private EditText nombreUsuario;
    private EditText contraseña;
    private EditText contraseñaReply;
    private EditText correo;
    private Spinner sexo;
    private Bitmap imagenBitMap;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;
    private static final int IMAGE_PICKER_REQUEST = 6;
    private static final int REQUEST_IMAGE_CAPTURE = 7;

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
        imagenBitMap = null;
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){

            Almacenamiento almacenamientoBase = new Almacenamiento();
            String nombreusuario = this.nombreUsuario.getText().toString().trim();
            String correoElectronico = this.correo.getText().toString().trim();
            String sexoUsuario = this.sexo.getSelectedItem().toString().trim();

            //Jugador jugador = new Jugador(correoElectronico, nombreusuario, sexoUsuario, "0");

            //almacenamientoBase.push(jugador, "Usuarios/"+mAuth.getUid());
            //almacenamientoBase.addValueToReference("Jugadores/" + mAuth.getUid() + "/mundo", "hola");

            HashMap<String, Object> datos = new HashMap<String, Object>();
            datos.put("correo", correoElectronico);
            datos.put("nombreUsuario", nombreusuario);
            datos.put("sexo", sexoUsuario);
            datos.put("tipo", "0");
            almacenamientoBase.push(datos, "Jugador/"+mAuth.getUid());
            //jugador.pushFireBaseBD();


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
            Pattern direccionCorrecta = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
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

    public void loadImage(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_image_customer,null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    public void requestPermission(Activity context, String permission, String just, int id) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

            }
            ActivityCompat.requestPermissions(context, new String[]{permission}, id);
        }
    }

    public void eliminarImagen(View v){
        imagenBitMap = null;
        imagenPerfil.setImageResource(R.drawable.user2);
        if(dialog != null){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

    public void buscarImagen(View v){
        //requestPermission(this, Manifest.permission.CAMERA, "Es necesario para usar la camamara", REQUEST_IMAGE_CAPTURE );


        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
        if(dialog != null){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

    public void tomarImagen(View v){
        requestPermission(this, Manifest.permission.CAMERA, "Es necesario para usar la camamara", REQUEST_IMAGE_CAPTURE );

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

            if(dialog != null){
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    if(resultCode == RESULT_OK){
                        Bundle extras = data.getExtras();
                        imagenBitMap = (Bitmap) extras.get("data");
                        if(imagenBitMap != null){
                            imagenPerfil.setImageBitmap(imagenBitMap);
                        }
                        else{
                            imagenPerfil.setImageResource(R.drawable.user2);
                        }
                    }
                }
                break;

            case IMAGE_PICKER_REQUEST:
                if(resultCode == RESULT_OK){
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        imagenBitMap = BitmapFactory.decodeStream(imageStream);
                        if(imagenBitMap != null){
                            imagenPerfil.setImageBitmap(imagenBitMap);
                        }
                        else{
                            imagenPerfil.setImageResource(R.drawable.user2);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
