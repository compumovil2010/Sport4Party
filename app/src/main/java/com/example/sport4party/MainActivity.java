package com.example.sport4party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button crearEvento;
    Button editarEvento;
    Button infoEvento;
    Button normal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearEvento=findViewById(R.id.crearEvento);
        editarEvento=findViewById(R.id.editarEvento);
        infoEvento= findViewById(R.id.verInfo);
        normal=(Button)findViewById(R.id.PersonaNormal) ;
        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), CrearEvento.class);
                intent.putExtra("pantalla",1);
                startActivity(intent);
            }
        }
        );
        editarEvento.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent=new Intent(v.getContext(), CrearEvento.class);
                                               intent.putExtra("pantalla",0);
                                               startActivity(intent);
                                           }
                                       }
        );
        infoEvento.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent=new Intent(v.getContext(), InformacionEvento.class);
                                                intent.putExtra("pantalla",0);
                                                startActivity(intent);
                                            }
                                        }
        );
        normal.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent=new Intent(v.getContext(), InformacionEvento.class);
                                              intent.putExtra("pantalla",1);
                                              startActivity(intent);
                                          }
                                      }
        );
    }
}
