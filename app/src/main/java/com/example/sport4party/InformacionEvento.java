package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

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
    TextView participantesT;
    ImageButton inscribirse;
    ImageButton participantes;
    ImageButton chatB;
    ArrayList<Jugador> jugadores;
    Button seleccionarLugar;
    private String idEvento;
    private int cuposOcupados=0;
    private long cuposTotales;
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
        participantesT=(TextView)findViewById(R.id.ParticipantesText);
    }
    private void imprimirInfo()
    {

        //nombre.setText("Olimpiada 2020");
        //descripcion.setText("Los Juegos Olímpicos modernos se inspiraron en los Juegos Olímpicos de la antigüedad del siglo VIII a. C. organizados en la antigua Grecia con sede en la ciudad de Olimpia, realizados entre los años 776 a. C. y el 393 de nuestra era. En el siglo XIX, surgió la idea de realizar unos eventos similares a los organizados en la antigüedad, los que se concretarían principalmente gracias a las gestiones del noble francés Pierre Frèdy, barón de Coubertin. El barón de Coubertin fundó el Comité Olímpico Internacional (COI) en 1894. Desde entonces, el COI se ha convertido en el órgano coordinador del Movimiento Olímpico, con la Carta Olímpica que define su estructura y autoridad.");
        //cupos.setText("10/20");
        //deporte.setText("Futbol");
        //habilidad.setText("Avanzado");
        //fechaYHora.setText("2020/11/31");
        //precio.setText("$20000");
        Almacenamiento buscarEvento=new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                nombre.setText((String)data.get("nombre"));
                descripcion.setText((String)data.get("descripcion"));
                precio.setText("$"+(String)data.get("precio"));
                habilidad.setText((String)data.get("nivelHabilidad"));
                HashMap<String,Object>fechaAux=(HashMap<String, Object>) data.get("fecha");

                fechaYHora.setText(((long)fechaAux.get("year")+1900)+"/"+
                        ((long)fechaAux.get("month")+1)+"/"+((long)fechaAux.get("date")));
                Almacenamiento buscarDeporte=new Almacenamiento()
                {
                    @Override
                    public void onBuscarResult(HashMap<String, Object> data, String key) {
                        super.onBuscarResult(data, key);
                        deporte.setText((String)data.get("nombre"));

                    }
                };
                buscarDeporte.buscarPorID("Deporte/",(String)data.get("deporte"));
                cuposTotales=(long)data.get("cupos");
                buscarCupos();
            }
        };
        buscarEvento.buscarPorID("Evento/",this.idEvento);

    }
    private  void mostrarParaOrganizador()
    {
        editarEinscribir.setText("Editar");
        inscribirse.setImageResource(R.drawable.edit);
        inscribirse.setVisibility(View.VISIBLE);
        inscribirse.setClickable(true);
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
        inscribirse.setVisibility(View.VISIBLE);
        inscribirse.setClickable(true);
        chatB.setVisibility(View.INVISIBLE);
        chatB.setClickable(false);
        chatT.setVisibility(View.INVISIBLE);
        verSiYaEstaInscrito();

        inscribirse.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Almacenamiento guardar=new Almacenamiento()
                                               {
                                                   @Override
                                                   public void onBuscarResult(HashMap<String, Object> data, String key) {
                                                       super.onBuscarResult(data, key);
                                                       HashMap<String,Object>objetivo=(HashMap<String,Object>)data.get("eventos");
                                                       if(objetivo!= null)
                                                       {
                                                           objetivo.put(idEvento,idEvento);
                                                       }
                                                       else
                                                       {
                                                          objetivo=new HashMap<String ,Object>() ;
                                                          objetivo.put(idEvento,idEvento);
                                                       }

                                                       this.push(objetivo,"Jugador/"+ FirebaseAuth.getInstance().getUid()+"/","eventos/");
                                                   }
                                               };

                                              guardar.buscarPorID("Jugador/",FirebaseAuth.getInstance().getUid());
                                               Intent intent=new Intent(v.getContext(), Mapa.class);

                                               Toast.makeText(v.getContext(),"Inscripcion realizada",Toast.LENGTH_LONG).show();
                                               startActivity(intent);
                                           }
                                       }
        );

    }

    private void verSiYaEstaInscrito() {
        Almacenamiento verSiEstaInscrito=new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                HashMap<String,Object> aux=(HashMap<String, Object>) data.get("eventos");
                if(aux!=null)
                {
                    for(String eventoKey: aux.keySet())
                    {
                        if(eventoKey.equals(idEvento))
                        {
                            inscribirse.setVisibility(View.INVISIBLE);
                            inscribirse.setClickable(false);
                            editarEinscribir.setText("");
                        }
                    }
                }
            }

        };
        verSiEstaInscrito.buscarPorID("Jugador/",FirebaseAuth.getInstance().getUid());

    }

    public void mostrarParaInscrito()
    {
        editarEinscribir.setText("");
        inscribirse.setVisibility(View.INVISIBLE);
        inscribirse.setClickable(false);
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
        idEvento=getIntent().getStringExtra("id");
        Log.i("idEvento",idEvento);
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
        if(opcion == 2){
            mostrarParaInscrito();
        }
        participantes.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               jugadores = new ArrayList<>();
                                               jugadores.add(new Jugador("666","asd","asd", "Juan Francisco Hamon", "Masculino"));
                                               jugadores.add(new Jugador("33","asd","asd", "Diego Barajas", "Masculino"));
                                               jugadores.add(new Jugador("88","asd","asd", "Brandonn Cruz", "Masculino"));
                                               jugadores.add(new Jugador("99","asd","asd", "Santiago Chaparro", "Masculino"));
                                               jugadores.add(new Jugador("77","asd","asd", "Pedro Fernandez", "Masculino"));
                                               jugadores.add(new Jugador("88","asd","asd", "Santiago Herrera", "Masculino"));
                                               jugadores.add(new Jugador("99","asd","asd", "Carlos Orduz", "Masculino"));
                                               jugadores.add(new Jugador("33","asd","asd", "Diego Ignacio Martinez", "Masculino"));
                                               Intent participantes =new Intent(v.getContext(), VerParticipantes.class);
                                               Bundle info = new Bundle();
                                               info.putSerializable("participantes", jugadores);
                                               participantes.putExtra("listaParticipantes",info);
                                               idEvento = "-M7HGJzL8MayFwf68Xzj";
                                               participantes.putExtra("idEvento",idEvento );
                                               startActivity(participantes);
                                           }
                                       }
        );
    }

    int buscarCupos()
    {
        cupos.setText(0+"/"+cuposTotales);
        Almacenamiento buscarGenteLigada=new Almacenamiento()
        {
            @Override
            public void leerDatos(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatos(datos, singleSnapShot);
                HashMap<String,Object>eventos=(HashMap<String, Object>) datos.get("eventos");
                if(eventos!=null)
                for(String llaves: eventos.keySet())
                {
                    if(llaves.equals(idEvento))
                    {
                        cuposOcupados++;
                        cupos.setText(cuposOcupados+"/"+cuposTotales);
                        if(cuposOcupados >= cuposTotales)
                        {
                            inscribirse.setVisibility(View.INVISIBLE);
                            inscribirse.setClickable(false);
                        }


                    }
                }
            }
        };
        buscarGenteLigada.loadOnce("Jugador");
        return 0;
    }
}
