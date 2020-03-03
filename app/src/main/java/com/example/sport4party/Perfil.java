package com.example.sport4party;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Deportista;
import com.example.sport4party.Modelo.Evento;

public class Perfil extends AppCompatActivity {
    private Deportista perfil;
    private int tipo;
    private TextView nombreUsuario;
    private TextView nivel;
    private TextView amigos;
    private TextView eventos;
    private Button buttonEventos;
    private ImageButton buttonPreferencias;

    private EventosAdapter eventosAdapter;
    private ListView listEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreUsuario = (TextView) findViewById(R.id.textViewNombreUsuario);
        nivel = (TextView) findViewById(R.id.textViewNivel);
        amigos = (TextView) findViewById(R.id.textViewAmigos);
        eventos = (TextView) findViewById(R.id.textViewEventos);
        buttonEventos = (Button) findViewById(R.id.buttonNuevoEvento);
        buttonPreferencias = (ImageButton) findViewById(R.id.imageButtonPreferencias);

        buttonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Nuevo evento creado", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        perfil = (Deportista) intent.getSerializableExtra("deportista");
        tipo = Integer.parseInt(intent.getStringExtra("tipo"));

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

        listEventos = (ListView)findViewById(R.id.listViewEventos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizar();
    }

    private void actualizar() {
        nombreUsuario.setText(perfil.getNombre());
        nivel.setText(perfil.getNivelHabilidad());
        amigos.setText(Integer.toString(perfil.sizeAmigos()));
        eventos.append(" " + perfil.getNombre());

        switch (tipo) {
            case 0: {
                miPerfilVista();
                actualizarEventosUsuario();
            }
            break;
            case 1: {
                amigoVista();
            }
        }
    }

    private void miPerfilVista() {
        buttonEventos.setVisibility(View.VISIBLE);
        buttonPreferencias.setVisibility(View.VISIBLE);
    }

    private void amigoVista() {
        buttonEventos.setVisibility(View.GONE);
        buttonPreferencias.setVisibility(View.GONE);
    }

    private void actualizarEventosUsuario(){
        eventosAdapter = new EventosAdapter(this, perfil.getEventos());
        listEventos.setAdapter(eventosAdapter);
    }
}
