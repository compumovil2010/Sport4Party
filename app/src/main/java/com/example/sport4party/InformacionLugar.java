package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deportista;
import com.example.sport4party.Modelo.Opinion;

import java.util.ArrayList;

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

        Deportista perfil1 = new Deportista(0, "Brandonn Cruz", "Bueno", 5);
        Deportista perfil2 = new Deportista(0, "Santiago Chaparro", "Bueno", 5);

        Opinion opinion1 = new Opinion(5f, "Que emocionate");
        Opinion opinion2 = new Opinion(5f, "Un lugar excelente");

        opinion1.setRemitente(perfil1);
        opinion2.setRemitente(perfil2);

        opns.add(opinion1);
        opns.add(opinion2);

        opnAdapter = new OpinionesAdapter(this, opns);
        opiniones.setAdapter(opnAdapter);

    }
}
