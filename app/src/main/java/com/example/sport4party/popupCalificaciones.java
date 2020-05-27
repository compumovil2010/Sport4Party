package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sport4party.Modelo.Jugador;
import com.example.sport4party.Modelo.Opinion;
import com.example.sport4party.Modelo.Ubicacion;
import com.example.sport4party.Utils.Almacenamiento;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class popupCalificaciones extends AppCompatActivity {

    String idLugar;
    EditText titulo;
    EditText descripcion;
    Button aceptar;
    Button cancelar;
    ImageButton star1;
    ImageButton star2;
    ImageButton star3;
    ImageButton star4;
    ImageButton star5;
    Double calificacionActual=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_calificaciones);
        DisplayMetrics medidasVentana= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho= medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho*0.8),(int)(alto*0.5));
        inflate();




    }
    private void inflate() {
        idLugar = getIntent().getStringExtra("idLugar");
        descripcion = (EditText)findViewById(R.id.detalles);
        aceptar = (Button) findViewById(R.id.subirCalificacion);
        cancelar = (Button) findViewById(R.id.cancelarCalificacion);
        star1 = (ImageButton)findViewById(R.id.star10);
        star2 = (ImageButton)findViewById(R.id.star20);
        star3 = (ImageButton)findViewById(R.id.star30);
        star4 = (ImageButton)findViewById(R.id.star40);
        star5 = (ImageButton)findViewById(R.id.star50);
        star1.setImageResource(R.drawable.star_empty);
        star2.setImageResource(R.drawable.star_empty);
        star3.setImageResource(R.drawable.star_empty);
        star4.setImageResource(R.drawable.star_empty);
        star5.setImageResource(R.drawable.star_empty);
        initListeners();
    }

    private boolean subirCalificacion()
    {
        Opinion opinion=new Opinion();

        if(descripcion.getText().toString().length()>0)
        {
            opinion.setDescripcion(descripcion.getText().toString());
        }
        else
        {
            return  false;
        }
        opinion.setCalificacion(calificacionActual);
        Ubicacion ubi=new Ubicacion();
        Log.i("idLugar",idLugar);
        ubi.setId(idLugar.toString());
        opinion.setDetalles(ubi);
        Almacenamiento almacenamiento=new Almacenamiento();
        Jugador jugador=new Jugador();
        jugador.setId(FirebaseAuth.getInstance().getUid());
        opinion.setRemitente(jugador);
        opinion.pushFireBaseBD();
        final String idOpinion=opinion.getId();

        Almacenamiento almacenamiento2= new Almacenamiento()
        {
            @Override
            public void onBuscarResult(HashMap<String, Object> data, String key) {
                super.onBuscarResult(data, key);
                HashMap<String,Object> mapa=(HashMap<String, Object>) data.get("opiniones");
                if(mapa==null)
                    mapa=new HashMap<String, Object>();
                mapa.put(idOpinion, idOpinion);
                this.push(mapa,"Jugador/", key +"/opiniones/");
            }
        };
        almacenamiento2.buscarPorID("Jugador/",FirebaseAuth.getInstance().getUid());

return true;
    }
    private void initListeners()
    {
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subirCalificacion())
                {
                    Intent intent=new Intent(v.getContext(), InformacionLugar.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), InformacionLugar.class);
                startActivity(intent);
                finish();
            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star_empty);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                calificacionActual=1.0;
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star_empty);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                calificacionActual=2.0;
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star_empty);
                star5.setImageResource(R.drawable.star_empty);
                calificacionActual=3.0;
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star_empty);
                calificacionActual=4.0;
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star);
                calificacionActual=5.0;
            }
        });

    }


}
