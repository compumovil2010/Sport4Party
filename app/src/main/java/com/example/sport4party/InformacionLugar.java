package com.example.sport4party;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Opinion;
import com.example.sport4party.Modelo.Ubicacion;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class InformacionLugar extends AppCompatActivity {
    ArrayList<Evento>eventos=new ArrayList<>();
    ArrayList<Opinion> opns = new ArrayList<>();
    OpinionesAdapter opnAdapter;
    TextView nombreLugar;
    TextView deportesDisp;
    int cantCalificaciones=0;
    TextView horario;
    ListView opiniones;
    Button miCalificacion;
    EventosAdapter evAdapter;
    TextView textoVariable;
    Ubicacion ubicacion;
    Double promedioCalificaciones=0.0;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    String idLocalizacion;
    private void registrarLugarParaEvento()
    {
        miCalificacion.setText("Agregar Lugar Al Evento");
        miCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("nombreLugar", nombreLugar.getText().toString().trim());
                intent.putExtra("id",idLocalizacion);
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
        miCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),popupCalificaciones.class);
                intent.putExtra("idLugar",idLocalizacion);
                startActivity(intent);
                finish();
            }
        });
        star1=findViewById(R.id.star1);
        star2=findViewById(R.id.star2);
        star3=findViewById(R.id.star3);
        star4=findViewById(R.id.star4);
        star5=findViewById(R.id.star5);



        //nombreLugar.setText("Centro deportivo de los andes");
        deportesDisp.setText("");
        //horario.setText("Lunes a Sabado 8 a.m - 7 p.m");


        idLocalizacion=getIntent().getStringExtra("id");
        idLocalizacion="69";
        Almacenamiento buscarLocalizacion=new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                nombreLugar.setText((String)data.get("descripcion"));
                opiniones.setAdapter(opnAdapter);
                buscarCalificaciones(data, key);
                buscarDeportes(data,key);
            }
        };
        opnAdapter = new OpinionesAdapter(this, opns);
        buscarLocalizacion.buscarPorID("Ubicacion/",idLocalizacion);
        if(getIntent().getIntExtra("pantalla",-1)==3)
        {
            Ubicacion ubicacionPa = new Ubicacion("ubicacion de prueba", new Date(), new Double(0), new Double(0), true);
            Jugador remitente = new Jugador("lol","asd", "asd", "Brandonn Cruz", "Masculino");
            Jugador remitente2 = new Jugador("lal","asd", "asd", "Santiago Chaparro", "Masculino");
            Opinion opinion1 = new Opinion(5.3, "Que emocionate", ubicacionPa, remitente);
            Opinion opinion2 = new Opinion(4.5, "Un lugar excelente", ubicacionPa, remitente2);
            registrarLugarParaEvento();
        }
        else
        {



            textoVariable.setText("Eventos disponibles en el Lugar");
            quemarEventos();

            eventos=new ArrayList<Evento>();
            buscarEventosBD(eventos, idLocalizacion);
            verSiYaVoto();

        }


}

private void verSiYaVoto()
    {
        Almacenamiento almacenamiento= new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                HashMap<String, Object>mapa= (HashMap<String,Object>)data.get("opiniones");
                for(String lal: mapa.keySet())
                {
                    Almacenamiento almacenamiento1=new Almacenamiento()
                    {
                        @Override
                        public void onBuscarResult(HashMap<String, Object> data, String key) {
                            super.onBuscarResult(data, key);

                            if(   ((String)(data.get("detalles"))).trim().equals(idLocalizacion)   )
                            {
                                miCalificacion.setVisibility(View.INVISIBLE);
                                miCalificacion.setClickable(false);
                            }
                        }
                    };
                    almacenamiento1.buscarPorID("Opinion/",lal);
                }

            }
        };
        almacenamiento.buscarPorID("Jugador/", FirebaseAuth.getInstance().getUid());
    }

    private  void buscarCalificaciones(HashMap<String, Object> data, final String key)
{
    Almacenamiento buscarCalificaciones= new Almacenamiento()
    {
        @Override
        public void leerDatos(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
            super.leerDatos(datos, singleSnapShot);
            if(((String)datos.get("detalles")).equals(key))
            {
                promedioCalificaciones=promedioCalificaciones+ (Long) datos.get("calificacion");
                cantCalificaciones++;
                actualizarCalificacion();

                final Opinion opinion=new Opinion();
                opinion.setCalificacion(((Long) datos.get("calificacion")).doubleValue());
                opinion.setDescripcion((String)datos.get("descripcion"));
                Almacenamiento buscarRemitente=new Almacenamiento()
                {
                    @Override
                    public void onBuscarResult(HashMap<String, Object> data, String key) {
                        super.onBuscarResult(data, key);
                        Jugador jugador=new Jugador();
                        jugador.setNombreUsuario((String)data.get("nombreUsuario"));
                        jugador.setId(key);
                        opinion.setRemitente(jugador);
                        opns.add(opinion);
                        actualizarLista();
                        Log.i("lul","lul");
                        actualizaOpiniones();
                    }
                };
                Log.i("lal","lal");
                buscarRemitente.buscarPorID("Jugador/",(String)datos.get("remitente"));

            }

        }
    };
    buscarCalificaciones.loadOnce("Opinion/");
}
public  void actualizarCalificacion()
{
    Double promedio=promedioCalificaciones/cantCalificaciones;
    Log.i("Calificacion actual",promedio.toString());

    if(promedio<5)
    {
        star5.setImageResource(R.drawable.star_empty);
        if(4< promedio)
        {
           star5.setImageResource(R.drawable.star_half);
        }
    }

    if(promedio<4)
    {
        star4.setImageResource(R.drawable.star_empty);
        if(3<promedio)
        {
            star4.setImageResource(R.drawable.star_half);
        }
    }
    if(promedio<3)
    {
        star3.setImageResource(R.drawable.star_empty);
        if(2<promedio)
        {
            star3.setImageResource(R.drawable.star_half);
        }
    }
    if(promedio<2)
    {
        star2.setImageResource(R.drawable.star_empty);
        if(1<promedio)
        {
            star2.setImageResource(R.drawable.star_half);
        }
    }
    if (promedio<1)
    {
        star1.setImageResource(R.drawable.star_empty);
        if(0<promedio)
        {
            star1.setImageResource(R.drawable.star_half);
        }
    }
}

private void buscarDeportes(HashMap<String, Object> data, final String key)
{
    HashMap<String, Object>deportesID=(HashMap<String, Object>) data.get("deportesDisponibles");
    for(String id : deportesID.keySet())
    {
        Almacenamiento buscarTitulo=new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                //Log.i("Deportes", (String) data.get("nombre"));
                deportesDisp.setText(deportesDisp.getText()+((String)data.get("nombre"))+" ");
            }
        };
        //Log.i("Deportes",id.toString());
        buscarTitulo.buscarPorID("Deporte",id);
    }

}
    private void buscarEventosBD(final ArrayList<Evento> eventos, final String idLocalizacion) {
        Almacenamiento buscarEventos=new Almacenamiento()
        {
            @Override
            public void leerDatos(final HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                super.leerDatos(datos, singleSnapShot);
                if(datos.get("ubicacion")!=null)
                if(datos.get("ubicacion").equals(idLocalizacion))
                {
                    Log.i("eventos","Lol");
                    final Evento evento=new Evento();
                    evento.setNombre(((String)datos.get("nombre")));
                    evento.setPrivado((Boolean)datos.get("privado"));
                    evento.setId(singleSnapShot.getKey());
                    Almacenamiento buscarDeporte=new Almacenamiento(){
                        @Override
                        public void onBuscarResult(HashMap<String, Object> data, String key) {
                            super.onBuscarResult(data, key);
                            Log.i("eventos","Lal");
                            Deporte deporte=new Deporte();
                            deporte.setNombre((String) data.get("nombre"));
                            deporte.setId(key);

                            evento.setDeporte(deporte);
                            eventos.add(evento);
                            Log.i("eventos","Lul");
                            actualizarLista();
                        }
                    };
                    buscarDeporte.buscarPorID("Deporte/",(String) datos.get("deporte"));
                }
            }
        };
        buscarEventos.loadOnce("Evento/");

    }
    private void actualizarLista()
    {
        evAdapter=new EventosAdapter(this,eventos,false,false);
        opiniones.setAdapter(evAdapter);
    }
    private void actualizaOpiniones()
    {
        opnAdapter = new OpinionesAdapter(this, opns);
        opiniones.setAdapter(opnAdapter);
    }

    private void quemarEventos()
    {
        /*
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
        this.eventos.add(evento4);*/
    }
}
