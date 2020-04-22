package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Opinion;
import com.example.sport4party.Modelo.Ubicacion;

import java.util.ArrayList;
import java.util.Date;

public class InformacionLugar extends AppCompatActivity {
    ArrayList<Evento>eventos=new ArrayList<>();
    ArrayList<Opinion> opns = new ArrayList<>();
    OpinionesAdapter opnAdapter;
    TextView nombreLugar;
    TextView deportesDisp;
    TextView horario;
    ListView opiniones;
    Button miCalificacion;
    EventosAdapter evAdapter;
    TextView textoVariable;
    private void registrarLugarParaEvento()
    {
        miCalificacion.setText("Agregar Lugar Al Evento");
        miCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("nombreLugar", "Centro deportivo internacional");
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
    });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_lugar);
        nombreLugar = findViewById(R.id.placeName);
        deportesDisp = findViewById(R.id.resultDeportDisp);
        horario = findViewById(R.id.resultHorario);
        opiniones = findViewById(R.id.listViewParticipantes);
        miCalificacion = findViewById(R.id.botonSubirCalif);
        textoVariable=findViewById(R.id.textoVariable);

        nombreLugar.setText("Centro deportivo de los andes");
        deportesDisp.setText("Futbol y baloncesto");
        horario.setText("Lunes a Sabado 8 a.m - 7 p.m");



        if(getIntent().getIntExtra("pantalla",-1)==3)
        {
            Ubicacion ubicacionPa = new Ubicacion("ubicacion de prueba", new Date(), new Double(0), new Double(0), true);
            Jugador remitente = new Jugador("asd", "asd", "Brandonn Cruz", "Masculino");
            Jugador remitente2 = new Jugador("asd", "asd", "Santiago Chaparro", "Masculino");
            Opinion opinion1 = new Opinion(5f, "Que emocionate", ubicacionPa, remitente);
            Opinion opinion2 = new Opinion(5f, "Un lugar excelente", ubicacionPa, remitente2);

            opns.add(opinion1);
            opns.add(opinion2);

            opnAdapter = new OpinionesAdapter(this, opns);
            opiniones.setAdapter(opnAdapter);
            registrarLugarParaEvento();
        }
        else
        {
            textoVariable.setText("Eventos disponibles en el Lugar");
            quemarEventos();
            evAdapter=new EventosAdapter(this,eventos,false,false);
            opiniones.setAdapter(evAdapter);

        }


}
    private void quemarEventos()
    {
        Deporte deportePrueba = new Deporte(12, "futbol");
        Ubicacion ubicacion1 = new Ubicacion("Prueba 1", new Date(), new Double(4.618234), new Double(-74.069133), true);
        Ubicacion ubicacion2 = new Ubicacion("Prueba 2", new Date(), new Double(4.630430), new Double(-74.0822808), true);
        Ubicacion ubicacion3 = new Ubicacion("Prueba 3", new Date(), new Double(4.588268), new Double(-74.100860), true);
        Ubicacion ubicacion4 = new Ubicacion("Prueba 4", new Date(), new Double(4.638389), new Double(-74.141524), false);

        Evento evento1 = new Evento(111, "evento 1", new Date(), "Bajo", "Evento 1", "0", false, false, deportePrueba, ubicacion1);
        Evento evento2 = new Evento(111, "evento 2", new Date(), "Bajo", "Evento 2", "0", false, false, deportePrueba, ubicacion2);
        Evento evento3 = new Evento(111, "evento 3", new Date(), "Bajo", "Evento 3", "0", false, false, deportePrueba, ubicacion2);
        Evento evento4 = new Evento(111, "evento 4", new Date(), "Bajo", "Evento 4", "0", false, true, deportePrueba, ubicacion3);

        eventos = new ArrayList<>();
        this.eventos.add(evento1);
        this.eventos.add(evento2);
        this.eventos.add(evento3);
        this.eventos.add(evento4);
    }
}
