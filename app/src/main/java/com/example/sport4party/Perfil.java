package com.example.sport4party;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.IntentService;
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
import android.util.Log;
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
import android.widget.Toast;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Ubicacion;
import com.example.sport4party.Utils.Almacenamiento;
import com.example.sport4party.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Perfil extends AppCompatActivity {
    //modelo
    private String perfilId;
    private String usuarioId;
    private Jugador perfil;
    private ArrayList<String> idAmigos;
    private ArrayList<String> idEventosCreados;
    private int tipo;
    //UI
    private ImageView usrImage;
    private TextView nombreUsuario;
    private TextView amigos;
    private TextView editEvent;
    private Button buttonEventos;
    private Button toAdd;
    private ListView listEventos;
    //Popup de la imagen de perfil
    private Dialog imgPopup;
    private ImageView imgPopProf;
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
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent.getStringExtra("jugador") != null) {
            perfilId = intent.getStringExtra("jugador");
        }
        tipo = Integer.parseInt(intent.getStringExtra("tipo"));

        perfil = new Jugador();
        perfil.setEventosCreados(new ArrayList<Evento>());
        idAmigos = new ArrayList<>();
        idEventosCreados = new ArrayList<>();

        if (tipo > 0) {
            usuarioId = mAuth.getCurrentUser().getUid();
        } else {
            perfilId = mAuth.getCurrentUser().getUid();
        }

        Almacenamiento almacenamiento = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if (datos != null) {
                    if (datos.containsKey("nombreUsuario")) {
                        perfil.setNombreUsuario(datos.get("nombreUsuario").toString());
                    }
                    if (datos.containsKey("amigos")) {
                        DataSnapshot amigos = singleSnapShot.child("amigos/");
                        idAmigos.clear();
                        for (DataSnapshot i : amigos.getChildren()) {
                            idAmigos.add(i.getValue().toString());
                        }
                    }
                    if (datos.containsKey("eventosCreados")) {
                        DataSnapshot eventosCreados = singleSnapShot.child("eventosCreados/");
                        idEventosCreados.clear();
                        perfil.setEventosCreados(new ArrayList<Evento>());
                        for (DataSnapshot i : eventosCreados.getChildren()) {
                            idEventosCreados.add(i.getValue().toString());
                        }
                    }
                    actualizar();
                }else
                    finish();
            }
        };
        almacenamiento.obtenerPorID("Jugador/", perfilId);

        Almacenamiento almacenamiento2 = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if (singleSnapShot == null)
                    return;
                String eventoCreadoId = singleSnapShot.getKey();
                if (idEventosCreados.contains(eventoCreadoId)) {
                    perfil.addEventoCreado(obtenerEvento(singleSnapShot));
                    idEventosCreados.remove(eventoCreadoId);
                }
                actualizarEventosUsuario();
            }
        };
        almacenamiento2.loadSubscription("Evento/");
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void crearEvento(View view) {
        Intent nuevoEvento = new Intent(view.getContext(), CrearEvento.class);
        nuevoEvento.putExtra("pantalla", 1);
        startActivity(nuevoEvento);
    }

    private Evento obtenerEvento(DataSnapshot singleSnapShot) {
        HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
        Evento evento = new Evento();
        evento.setId(singleSnapShot.getKey());
        evento.setID(Integer.parseInt(datos.get("ID").toString()));
        evento.setNombre(datos.get("nombre").toString());
        evento.setPrivado((boolean) datos.get("privado"));
        return evento;
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

        //if (perfil.getImagenPerfil() != null)
        //usrImage.setImageBitmap(perfil.getImagenPerfil());

        nombreUsuario.setText(perfil.getNombreUsuario());
        amigos.setText(Integer.toString(idAmigos.size()));
        switch (tipo) {
            case 0:
                miPerfilVista();
                break;
            case 1:
                amigoVista();
                break;
            case 2:
                otherVista();
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

    public void agregarEliminar(View view) {
        Almacenamiento almacenamiento = new Almacenamiento();
        switch (tipo) {
            case 1: {
                almacenamiento.erase("Jugador/" + usuarioId + "/amigos/" + perfilId);
                almacenamiento.erase("Jugador/" + perfilId + "/amigos/" + usuarioId);
            }
            break;
            case 2: {
                almacenamiento.push(perfilId, "Jugador/" + usuarioId + "/amigos/", perfilId);
                almacenamiento.push(perfilId, "Jugador/" + perfilId + "/amigos/", usuarioId);
            }
        }
    }

    private void actualizarEventosUsuario() {
        if (perfil.getEventosCreados() != null) {
            EventosAdapter eventosAdapter = new EventosAdapter(this, perfil.getEventosCreados(), false, true);
            listEventos.setAdapter(eventosAdapter);
        }
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
                actualizarNombre(input.getText().toString());
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

    private void actualizarNombre(String nombre) {
        Almacenamiento almacenamiento = new Almacenamiento();
        almacenamiento.addValueToReference("Jugador/" + perfilId + "/nombreUsuario", nombre);
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
    }

    public void popupImagen(View view) {
        if (tipo == 0) {
            crearPopUp();
            imgPopup.show();
        }
    }

    public void cerrarPopupImgPerfil(View view) {
        imgPopup.dismiss();
    }

    public void aceptarImagenPerfil(View view) {
        //perfil.setImagenPerfil(getBitmap(imgPopProf));
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

    public void regresar(View view) {
        finish();
    }
}
