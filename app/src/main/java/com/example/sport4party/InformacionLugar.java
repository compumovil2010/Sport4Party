package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Opinion;
import com.example.sport4party.Modelo.Ubicacion;

import java.util.ArrayList;
import java.util.Date;

public class InformacionLugar extends AppCompatActivity {

    ArrayList<Opinion> opns = new ArrayList<>();
    OpinionesAdapter opnAdapter;
    TextView nombreLugar;
    TextView deportesDisp;
    TextView horario;
    ListView opiniones;
    Button miCalificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_lugar);



        nombreLugar = findViewById(R.id.placeName);
        deportesDisp = findViewById(R.id.resultDeportDisp);
        horario = findViewById(R.id.resultHorario);
        opiniones = findViewById(R.id.listViewParticipantes);
        miCalificacion = findViewById(R.id.botonSubirCalif);

        nombreLugar.setText("Centro deportivo de los andes");
        deportesDisp.setText("Futbol y baloncesto");
        horario.setText("Lunes a Sabado 8 a.m - 7 p.m");

        Ubicacion ubicacionPa = new Ubicacion("ubicacion de prueba", new Date(), (long)0, (long) 0, true);
        Jugador remitente = new Jugador("asd", "asd", "Brandonn Cruz", "Masculino");
        Jugador remitente2 = new Jugador("asd", "asd", "Santiago Chaparro", "Masculino");
        Opinion opinion1 = new Opinion(5f, "Que emocionate", ubicacionPa, remitente);
        Opinion opinion2 = new Opinion(5f, "Un lugar excelente", ubicacionPa, remitente2);

        opns.add(opinion1);
        opns.add(opinion2);

        opnAdapter = new OpinionesAdapter(this, opns);
        opiniones.setAdapter(opnAdapter);

    }
}
