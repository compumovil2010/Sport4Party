package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Perfil extends AppCompatActivity {
    //modelo
    private Jugador perfil;
    private int tipo;
    //UI
    private ImageView usrImage;
    private TextView nombreUsuario;
    private TextView amigos;
    private TextView editEvent;
    private Button buttonEventos;
    private Button toAdd;
    private EventosAdapter eventosAdapter;
    private ListView listEventos;
    //Popup de la imagen de perfil
    private Dialog imgPopup;
    private ImageView imgPopProf;
    private Button aceptarImg;
    private Button cancelarImg;
    private Button camera;
    private Button gallery;
    private ImageButton cerrarPopup;
    //Autenticación
    private FirebaseAuth mAuth;
    //Funcionalidad
    private final int IMAGE_PICKER_REQUEST = 0;
    private final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        iniciarVista();
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        perfil = (Jugador) intent.getSerializableExtra("jugador");
        tipo = Integer.parseInt(intent.getStringExtra("tipo"));

        buttonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoEvento = new Intent(v.getContext(), CrearEvento.class);
                nuevoEvento.putExtra("pantalla", 1);
                startActivity(nuevoEvento);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        actualizar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void iniciarVista() {
        usrImage = (ImageView) findViewById(R.id.userImg);
        nombreUsuario = (TextView) findViewById(R.id.profileName);
        amigos = (TextView) findViewById(R.id.resultCantAmigos);
        editEvent = (TextView) findViewById(R.id.textoEditorEventos);
        buttonEventos = (Button) findViewById(R.id.botonNuevoEvento);
        toAdd = (Button) findViewById(R.id.toAddOrDelete);
        listEventos = (ListView) findViewById(R.id.listViewEventos);
    }

    private void actualizar() {
        if (perfil.getImagenPerfil() != null)
            usrImage.setImageBitmap(perfil.getImagenPerfil());
        nombreUsuario.setText(perfil.getNombreUsuario());
        amigos.setText(Integer.toString(perfil.getAmigos().size()));
        switch (tipo) {
            case 0: {
                miPerfilVista();
                actualizarEventosUsuario();
            }
            break;
            case 1: {
                amigoVista();
            }
            break;
            case 2: {
                otherVista();
            }
        }
    }

    private void miPerfilVista() {
        editEvent.setText("Editor de eventos");
        buttonEventos.setVisibility(View.VISIBLE);
        toAdd.setVisibility(View.INVISIBLE);
        toAdd.setClickable(false);
    }

    private void amigoVista() {
        editEvent.setText("Eventos de " + perfil.getNombreUsuario());
        buttonEventos.setVisibility(View.GONE);
        toAdd.setText("Eliminar de mis amigos");
        toAdd.setBackgroundResource(R.drawable.boton_general2);
    }

    private void otherVista() {
        editEvent.setText("Eventos de " + perfil.getNombreUsuario());
        buttonEventos.setVisibility(View.GONE);
        toAdd.setText("Agregar a mis amigos");
        toAdd.setBackgroundResource(R.drawable.boton_general3);
    }

    private void actualizarEventosUsuario() {
        eventosAdapter = new EventosAdapter(this, perfil.getEventos(), false, true);
        listEventos.setAdapter(eventosAdapter);
    }

    public void cambiarNombre(View view) {
        if (tipo == 0)
            alertaNombre(view);
    }

    public void alertaNombre(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Actualizar nombre");
        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(perfil.getNombreUsuario());
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                perfil.setNombreUsuario(input.getText().toString());
                nombreUsuario.setText(perfil.getNombreUsuario());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    public Bitmap getBitmap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    public void crearPopUp() {
        imgPopup = new Dialog(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View ll = inflater.inflate(R.layout.popupimagenperfil, null);
        imgPopup.setContentView(ll);
        imgPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgPopProf = (ImageView) ll.findViewById(R.id.imageViewPopImgPerfil);
        imgPopProf.setImageBitmap(getBitmap(usrImage));
        cerrarPopup = (ImageButton) ll.findViewById(R.id.imageButtonCloseImg);
        aceptarImg = (Button) ll.findViewById(R.id.buttonImgOk);
        cancelarImg = (Button) ll.findViewById(R.id.buttonImgCancel);
        camera = (Button) ll.findViewById(R.id.buttonCamera);
        gallery = (Button) ll.findViewById(R.id.buttonGallery);
    }

    public void popupImagen(View view) {
        if(tipo == 0){
            crearPopUp();
            imgPopup.show();
        }
    }

    public void cerrarPopupImgPerfil(View view) {
        imgPopup.dismiss();
    }

    public void aceptarImagenPerfil(View view) {
        perfil.setImagenPerfil(getBitmap(imgPopProf));
        actualizar();
        imgPopup.dismiss();
    }

    public void cancelarImagenPerfil(View view) {
        imgPopup.dismiss();
    }

    public void tomarFoto(View view) {
        permissionCamera();
        takePicture();
    }

    public void buscarGaleria(View view) {
        permissionStorage();
        initViews();
    }

    private void permissionStorage() {
        Utils.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, "Necesario para cargar una imágen", IMAGE_PICKER_REQUEST);
    }

    private void permissionCamera() {
        Utils.requestPermission(this, Manifest.permission.CAMERA, "Necesario para tomar una foto", REQUEST_IMAGE_CAPTURE);
    }

    public void initViews() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKER_REQUEST: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imgPopProf.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case REQUEST_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imgPopProf.setImageBitmap(imageBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IMAGE_PICKER_REQUEST: {
                initViews();
                return;
            }
            case REQUEST_IMAGE_CAPTURE: {
                takePicture();
                return;
            }
        }
    }
}
