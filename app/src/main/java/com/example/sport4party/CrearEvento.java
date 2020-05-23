package com.example.sport4party;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Ubicacion;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.microedition.khronos.egl.EGLDisplay;

public class CrearEvento extends AppCompatActivity {
    private int idLugar=-1;

    private Evento evento;
    private Deporte deporteAux;
    private Ubicacion ubicacionAux;

    Button editar;
    Button seleccionarUbicacion;
    EditText nombre;
    EditText descripcion;
    Spinner deporte;
    Button fecha;
    Button hora;
    EditText cupos;
    Spinner habilidad;
    EditText precio;
    DatePickerDialog.OnDateSetListener picker;
    TimePickerDialog timePickerDialog;
    TextView textViewTittle;
    Date fechaAux;
    boolean fechaPuesta=false;
    boolean horaPuesta=false;
    void editar()
    {
        //Aqui se extraerian los datos ya existentes


        textViewTittle.setText("Editar Evento");
        nombre.setText("Olimpiada 2020");
        descripcion.setText("Los Juegos Olímpicos modernos se inspiraron en los Juegos Olímpicos de la antigüedad del siglo VIII a. C. organizados en la antigua Grecia con sede en la ciudad de Olimpia, realizados entre los años 776 a. C. y el 393 de nuestra era. En el siglo XIX, surgió la idea de realizar unos eventos similares a los organizados en la antigüedad, los que se concretarían principalmente gracias a las gestiones del noble francés Pierre Frèdy, barón de Coubertin. El barón de Coubertin fundó el Comité Olímpico Internacional (COI) en 1894. Desde entonces, el COI se ha convertido en el órgano coordinador del Movimiento Olímpico, con la Carta Olímpica que define su estructura y autoridad.");
        cupos.setText("Cupos: 10/20");
        fecha.setText("Fecha: (2020/11/31)");
        hora.setText("Hora (08:30)");
        precio.setText("20000 COP");
        habilidad.setSelection(1);
        deporte.setSelection(1);
        editar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Hay que verificar que todos los campos esten llenos
                                                //Intent intent=new Intent(v.getContext(), CrearEvento.class);
                                                //intent.putExtra("pantalla",0);
                                                //startActivity(intent);
                                            }
                                        }
        );
    }

    void crear()
    {

        editar.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //Hay que verificar que todos los campso esten llenos
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
        setContentView(R.layout.activity_crear_evento);
        inflate();
        int modo=getIntent().getIntExtra("pantalla",-1); //1 modo crear 0 modo editar
        if(modo==1)
        {


        }
        if(modo==0)
        {
            editar();

        }
    initFechaEvents();
    initHoras();
        seleccionarUbicacion.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //Aqui se abre seleccionar ubicacion
                                          Intent intent=new Intent(v.getContext(), Mapa.class);
                                          intent.putExtra("pantalla",1);
                                          startActivityForResult(intent,666);
                                      }
                                  }
        );

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(v.getContext(),Mapa.class);
                if(extraerInformación())
                {
                    Toast.makeText(v.getContext(),"Evento publicado",Toast.LENGTH_LONG).show();
                    startActivity(back);
                }
                else
                {
                    Toast.makeText(v.getContext(),"Información incorrecta o incompleta",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


private boolean extraerInformación()
{
    evento=new Evento();
    if(nombre.getText().length()>0)
    {
        evento.setNombre(nombre.getText().toString().trim());
    }
    else
    {
        return  false;
    }

    if(descripcion.getText().length()>0)
    {
        evento.setDescripcion(descripcion.getText().toString().trim());
    }
    else
    {
        return false;
    }
    if(this.fechaPuesta && this.horaPuesta)
    {
        evento.setFecha(this.fechaAux);
    }
    else
    {
        return false;
    }
    if(this.cupos.getText().length()>0)
    {
        evento.setCupos(Long.parseLong(this.cupos.getText().toString().trim()));
    }
    else
    {
        return false;
    }
    evento.setPrecio(precio.getText().toString());
    evento.setNivelHabilidad(habilidad.getSelectedItem().toString());
    evento.pushFireBaseBD();
    Almacenamiento almacenamiento=new Almacenamiento()
    {
        @Override
        public void onBuscarResult(HashMap<String, Object> data, String key) {
            super.onBuscarResult(data, key);
            HashMap<String,Object>mapAux=(HashMap<String, Object>) data.get("eventos");

            if(mapAux==null)
            {
                mapAux=new HashMap<String, Object>();
            }
            mapAux.put(evento.getId(),evento.getId());
            Almacenamiento almacenamiento1=new Almacenamiento();
            almacenamiento1.push(mapAux,"Jugador/"+FirebaseAuth.getInstance().getUid()+"/","eventosCreados");

        }
    };
    almacenamiento.buscarPorID("Jugador/", FirebaseAuth.getInstance().getUid());
    return true;
}
    private  void initHoras()
    {
        timePickerDialog=new TimePickerDialog(CrearEvento.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora.setText("Hora ("+hourOfDay+":"+minute+")");
                fechaAux.setHours(hourOfDay);
                fechaAux.setMinutes(minute);
                horaPuesta=true;

            }
        },0,0,false);

        hora.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                timePickerDialog.show();
            }
        });
    }
    private void initFechaEvents()
    {
        fecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                Calendar cal= Calendar.getInstance();
                int anio= cal.get(Calendar.YEAR);
                int mes= cal.get(Calendar.MONTH);
                int dia= cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(
                        CrearEvento.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        picker,
                        anio, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        picker= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date= "Fecha ("+(month+1)+"/"+dayOfMonth+"/"+year+")";

                fechaAux.setMonth(month);
                fechaAux.setDate(dayOfMonth);
                fechaAux.setYear(year-1990);
                fecha.setText(date);
                fechaPuesta=true;
            }
        };
    }
    void inflate()
    {
        editar=(Button)findViewById(R.id.button);
        seleccionarUbicacion=(Button)findViewById(R.id.button2);
        nombre=(EditText) findViewById(R.id.nombre);
        descripcion=(EditText) findViewById(R.id.descripcion);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        deporte=(Spinner) findViewById(R.id.deporte);
        fecha=(Button) findViewById(R.id.fecha);
        hora=(Button) findViewById(R.id.hora);
        cupos=(EditText)findViewById(R.id.cupos);
        habilidad=(Spinner)findViewById(R.id.habilidad);
        precio=(EditText) findViewById(R.id.precio);
        textViewTittle=(TextView) findViewById(R.id.toolbar_title);
        fechaAux=new Date();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 666:
                if(Activity.RESULT_OK==resultCode)
                {
                    String nombreLugar=data.getStringExtra("nombreLugar");

                    seleccionarUbicacion.setText(nombreLugar);
                }
                else
                {

                }

                break;
        }
    }

    @Override
    protected void onResume() {
        if(idLugar!=-1)
        {
            seleccionarUbicacion.setText("lol");
        }
        super.onResume();
    }
}
