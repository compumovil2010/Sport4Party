package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

public class InformacionEvento extends AppCompatActivity {
    TextView nombre;
    TextView descripcion;
    TextView cupos;
    TextView deporte;
    TextView habilidad;
    TextView fechaYHora;
    TextView precio;
    Button inscribirse;
    Button participantes;
    void inflate()
    {
        nombre=(TextView)findViewById(R.id.nombre);
        descripcion=(TextView)findViewById(R.id.descripcion);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        cupos=(TextView)findViewById(R.id.cupos);
        deporte=(TextView)findViewById(R.id.deporte);
        habilidad=(TextView)findViewById(R.id.habilidad);
        fechaYHora=(TextView) findViewById(R.id.fechayhor);
        precio=(TextView)findViewById(R.id.precio);
        inscribirse=(Button)findViewById(R.id.button3);
        participantes=(Button)findViewById(R.id.button4);
    }
    private void imprimirInfo()
    {
        nombre.setText("Olimpiada 2020");
        descripcion.setText("Los Juegos Olímpicos modernos se inspiraron en los Juegos Olímpicos de la antigüedad del siglo VIII a. C. organizados en la antigua Grecia con sede en la ciudad de Olimpia, realizados entre los años 776 a. C. y el 393 de nuestra era. En el siglo XIX, surgió la idea de realizar unos eventos similares a los organizados en la antigüedad, los que se concretarían principalmente gracias a las gestiones del noble francés Pierre Frèdy, barón de Coubertin. El barón de Coubertin fundó el Comité Olímpico Internacional (COI) en 1894. Desde entonces, el COI se ha convertido en el órgano coordinador del Movimiento Olímpico, con la Carta Olímpica que define su estructura y autoridad.");
        cupos.setText("Cupos: 10/20");
        deporte.setText("Futbol");
        habilidad.setText("Avanzado");
        fechaYHora.setText("Fecha: (2020/11/31)");
        precio.setText("20000 COP");
    }
    private  void mostrarParaOrganizador()
    {
        inscribirse.setText("Editar");
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
        inscribirse.setText("Inscribirse");
        inscribirse.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               //Aqui va para inscribirse
                                               //Intent intent=new Intent(v.getContext(), CrearEvento.class);
                                               //intent.putExtra("pantalla",0);
                                               //startActivity(intent);
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
                                               //Aqui va para ver participantes
                                               //Intent intent=new Intent(v.getContext(), CrearEvento.class);
                                               //intent.putExtra("pantalla",0);
                                               //startActivity(intent);
                                           }
                                       }
        );
    }

}
