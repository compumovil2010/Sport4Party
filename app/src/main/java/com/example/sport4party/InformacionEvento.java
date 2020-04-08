package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Deportista;

import java.util.ArrayList;

public class InformacionEvento extends AppCompatActivity {
    TextView nombre;
    TextView descripcion;
    TextView cupos;
    TextView deporte;
    TextView habilidad;
    TextView fechaYHora;
    TextView precio;
    TextView editarEinscribir;
    TextView chatT;
    ImageButton inscribirse;
    ImageButton participantes;
    ImageButton chatB;
    ArrayList<Deportista>deportistas;
    void inflate()
    {
        nombre=(TextView)findViewById(R.id.nombreEvento);
        descripcion=(TextView)findViewById(R.id.infoEvento);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        cupos=(TextView)findViewById(R.id.CuposResult);
        deporte=(TextView)findViewById(R.id.seVaAjugarResult);
        habilidad=(TextView)findViewById(R.id.NivelDeHabilidadResultInfo);
        fechaYHora=(TextView) findViewById(R.id.fechaResult);
        precio=(TextView)findViewById(R.id.percioResult);
        inscribirse=(ImageButton)findViewById(R.id.EditarButton);
        participantes=(ImageButton)findViewById(R.id.ParticipantesButton);
        chatB = (ImageButton)findViewById(R.id.chatButton);
        chatT = (TextView)findViewById(R.id.chatText);
        editarEinscribir = (TextView)findViewById(R.id.editarText);
    }
    private void imprimirInfo()
    {
        nombre.setText("Olimpiada 2020");
        descripcion.setText("Los Juegos Olímpicos modernos se inspiraron en los Juegos Olímpicos de la antigüedad del siglo VIII a. C. organizados en la antigua Grecia con sede en la ciudad de Olimpia, realizados entre los años 776 a. C. y el 393 de nuestra era. En el siglo XIX, surgió la idea de realizar unos eventos similares a los organizados en la antigüedad, los que se concretarían principalmente gracias a las gestiones del noble francés Pierre Frèdy, barón de Coubertin. El barón de Coubertin fundó el Comité Olímpico Internacional (COI) en 1894. Desde entonces, el COI se ha convertido en el órgano coordinador del Movimiento Olímpico, con la Carta Olímpica que define su estructura y autoridad.");
        cupos.setText("10/20");
        deporte.setText("Futbol");
        habilidad.setText("Avanzado");
        fechaYHora.setText("2020/11/31");
        precio.setText("$20000");
    }
    private  void mostrarParaOrganizador()
    {
        editarEinscribir.setText("Editar");
        inscribirse.setImageResource(R.drawable.edit);
        chatB.setVisibility(View.VISIBLE);
        chatB.setClickable(true);
        chatT.setVisibility(View.VISIBLE);
        inscribirse.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent=new Intent(v.getContext(), CrearEvento.class);
                                              intent.putExtra("pantalla",0);
                                              startActivity(intent);
                                          }
                                      }
        );

    }
    private  void mostrarParaUnaPersonaNormal()
    {
        editarEinscribir.setText("Inscribirse");
        inscribirse.setImageResource(R.drawable.join);
        chatB.setVisibility(View.INVISIBLE);
        chatB.setClickable(false);
        chatT.setVisibility(View.INVISIBLE);
        inscribirse.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               Intent intent=new Intent(v.getContext(), Mapa.class);
                                               Toast.makeText(v.getContext(),"Inscripcion realizada",Toast.LENGTH_LONG).show();
                                               startActivity(intent);
                                           }
                                       }
        );

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_evento);
        
        int opcion= getIntent().getIntExtra("pantalla",-1);//0 para mostrar info 1 para lo mismo pero editando
        inflate();
        imprimirInfo();
        if(opcion==1)
        {
            mostrarParaUnaPersonaNormal();
        }
        if(opcion==0)
        {
            mostrarParaOrganizador();
        }
        participantes.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               deportistas = new ArrayList<>();
                                               deportistas.add(new Deportista(1,"Juan Francisco Hamon", "Bueno", 4f));
                                               deportistas.add(new Deportista(2,"Diego Barajas", "Regular", 3f));
                                               deportistas.add(new Deportista(3,"Brandonn Cruz", "Malo", 2f));
                                               deportistas.add(new Deportista(4,"Santiago Chaparro", "Bueno", 5f));
                                               deportistas.add(new Deportista(5,"Pedro Fernandez", "Bueno", 4f));
                                               deportistas.add(new Deportista(6,"Santiago Herrera", "Regular", 3f));
                                               deportistas.add(new Deportista(7,"Carlos Orduz", "Malo", 2f));
                                               deportistas.add(new Deportista(8,"Diego Ignacio Martinez", "Bueno", 5f));
                                               Intent participantes =new Intent(v.getContext(), VerParticipantes.class);
                                               Bundle info = new Bundle();
                                               info.putSerializable("participantes",deportistas);
                                               participantes.putExtra("listaParticipantes",info);
                                               startActivity(participantes);
                                           }
                                       }
        );
    }

}