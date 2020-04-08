package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deportista;

public class Perfil extends AppCompatActivity {
    private Deportista perfil;
    private int tipo;
    private TextView nombreUsuario;
    private TextView nivel;
    private TextView amigos;
    private TextView editEvent;
    private Button buttonEventos;
    private Button toAdd;
    private ImageButton buttonPreferencias;

    private EventosAdapter eventosAdapter;
    private ListView listEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreUsuario = (TextView) findViewById(R.id.profileName);
        nivel = (TextView) findViewById(R.id.resultPromedioCalif);
        amigos = (TextView) findViewById(R.id.resultCantAmigos);
        editEvent = (TextView) findViewById(R.id.textoEditorEventos);
        buttonEventos = (Button) findViewById(R.id.botonNuevoEvento);
        toAdd = (Button) findViewById(R.id.toAddOrDelete);
        buttonPreferencias = (ImageButton) findViewById(R.id.imageButtonPreferencias);


        buttonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoEvento = new Intent(v.getContext(), CrearEvento.class);
                nuevoEvento.putExtra("pantalla",1);
                startActivity(nuevoEvento);
            }
        });

        buttonPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Su nuevo nombre:");

                final EditText input = new EditText(v.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(perfil.getNombre());
                builder.setView(input);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        perfil.setNombre(input.getText().toString());
                        actualizar();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        Intent intent = getIntent();
        perfil = (Deportista) intent.getSerializableExtra("deportista");
        tipo = Integer.parseInt(intent.getStringExtra("tipo"));
        listEventos = (ListView)findViewById(R.id.listViewEventos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private void actualizar() {
        nombreUsuario.setText(perfil.getNombre());
        nivel.setText(Double.toString(4.5));
        amigos.setText(Integer.toString(perfil.sizeAmigos()));
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
        buttonPreferencias.setVisibility(View.VISIBLE);
        buttonPreferencias.setClickable(true);
    }

    private void amigoVista() {
        editEvent.setText("Eventos de "+perfil.getNombre());
        buttonEventos.setVisibility(View.GONE);
        toAdd.setText("Eliminar de mis amigos");
        toAdd.setBackgroundResource(R.drawable.boton_general2);
        buttonPreferencias.setVisibility(View.INVISIBLE);
        buttonPreferencias.setClickable(false);
    }

    private  void otherVista(){
        editEvent.setText("Eventos de "+perfil.getNombre());
        buttonEventos.setVisibility(View.GONE);
        toAdd.setText("Agregar a mis amigos");
        toAdd.setBackgroundResource(R.drawable.boton_general3);
        buttonPreferencias.setVisibility(View.INVISIBLE);
        buttonPreferencias.setClickable(false);
    }
    private void actualizarEventosUsuario(){
        eventosAdapter = new EventosAdapter(this, perfil.getEventos());
        listEventos.setAdapter(eventosAdapter);
    }
}
