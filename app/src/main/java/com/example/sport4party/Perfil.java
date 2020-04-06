package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deportista;

public class Perfil extends AppCompatActivity {
    private Deportista perfil;
    private int tipo;
    //Spinner deportes;
    private TextView nombreUsuario;
    private TextView nivel;
    private TextView amigos;
    private Button buttonEventos;

    private EventosAdapter eventosAdapter;
    private ListView listEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreUsuario = (TextView) findViewById(R.id.profileName);
        nivel = (TextView) findViewById(R.id.resultPromedioCalif);
        amigos = (TextView) findViewById(R.id.resultCantAmigos);
        buttonEventos = (Button) findViewById(R.id.botonNuevoEvento);
        //deportes = (Spinner) findViewById(R.id.selectSport);

        buttonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoEvento = new Intent(v.getContext(), CrearEvento.class);
                nuevoEvento.putExtra("pantalla",1);
                startActivity(nuevoEvento);
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
        /*if(perfil.getNivelHabilidad().equals("Bueno")){
            nivel.setTextColor(Color.GREEN);
        }else if(perfil.getNivelHabilidad().equals("Regular")){
            nivel.setTextColor(Color.YELLOW);
        }else if(perfil.getNivelHabilidad().equals("Malo")){
            nivel.setTextColor(Color.RED);
        }
         */
        amigos.setText(Integer.toString(perfil.sizeAmigos()));
        /*ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Deportes, R.layout.text_color_spinner_deportes);
        adapter.setDropDownViewResource(R.layout.deportes_dropdown);
        deportes.setAdapter(adapter);
         */

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
    }

    private void amigoVista() {
        buttonEventos.setVisibility(View.GONE);
    }

    private void actualizarEventosUsuario(){
        eventosAdapter = new EventosAdapter(this, perfil.getEventos());
        listEventos.setAdapter(eventosAdapter);
    }
}
