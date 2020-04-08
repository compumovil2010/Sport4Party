package com.example.sport4party;

import android.content.Intent;
import android.os.Bundle;

import com.example.sport4party.Modelo.Deporte;
import com.example.sport4party.Modelo.Deportista;
import com.example.sport4party.Modelo.Evento;
import com.example.sport4party.Modelo.Ubicacion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class Mapa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    ArrayList<Deportista> deportistas;

    Spinner hora;
    Spinner deportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        hora = findViewById(R.id.spinnerHour);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.text_color_spinner_deportes);
        adapter.setDropDownViewResource(R.layout.deportes_dropdown);
        hora.setAdapter(adapter);

        deportes = findViewById(R.id.spinnerSport);
        ArrayAdapter adp = ArrayAdapter.createFromResource(this, R.array.sports, R.layout.text_color_spinner_deportes);
        adp.setDropDownViewResource(R.layout.deportes_dropdown);
        deportes.setAdapter(adp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

/*
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
*/
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.nav_evento){
            Intent change = new Intent(this, Mapa.class);
            startActivity(change);
        }
        else if (id == R.id.nav_mis_amigos){
            deportistas = new ArrayList<>();
            deportistas.add(new Deportista(1,"Juan Francisco Hamon", "Bueno", 4f));
            deportistas.add(new Deportista(2,"Diego Barajas", "Regular", 3f));
            deportistas.add(new Deportista(3,"Brandonn Cruz", "Malo", 2f));
            deportistas.add(new Deportista(4,"Santiago Chaparro", "Bueno", 5f));
            deportistas.add(new Deportista(5,"Pedro Fernandez", "Bueno", 4f));
            deportistas.add(new Deportista(6,"Santiago Herrera", "Regular", 3f));
            deportistas.add(new Deportista(7,"Carlos Orduz", "Malo", 2f));
            deportistas.add(new Deportista(8,"Diego Ignacio Martinez", "Bueno", 5f));
            Intent change = new Intent(this, MisAmigos.class);
            Bundle info = new Bundle();
            info.putSerializable("amigos",deportistas);
            change.putExtra("listaAmigos",info);
            startActivity(change);
        }else if(id == R.id.nav_mis_eventos){
            Intent change = new Intent(this, misEventos.class);
            Deportista miPerfil = new Deportista(0, "Mael", "Bueno", 5);

            Deporte futbol = new Deporte(10, "Futbol");
            Ubicacion ubicacion = new Ubicacion("ubicacion de prueba", new Date(), (long)0, (long) 0, true);
            Evento evento = new Evento(10, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, futbol, ubicacion);
            miPerfil.addEvento(evento);
            Evento evento2 = new Evento(10, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, futbol, ubicacion);
            miPerfil.addEvento(evento2);
            change.putExtra("deportista",miPerfil);
            startActivity(change);

        }else if(id == R.id.nav_mi_perfil){
            Intent change = new Intent(this, Perfil.class);
            Deportista miPerfil = new Deportista(0, "Mael", "Bueno", 5);

            Deporte futbol = new Deporte(10, "Futbol");
            Ubicacion ubicacion = new Ubicacion("ubicacion de prueba", new Date(), (long)0, (long) 0, true);
            Evento evento = new Evento(10, "atletismo", new Date(),"bueno", "Evento 1", "2000 pesos", true, futbol, ubicacion);
            miPerfil.addEvento(evento);
            Evento evento2 = new Evento(10, "atletismo2", new Date(),"bueno", "Evento 2", "2000 pesos", false, futbol, ubicacion);
            miPerfil.addEvento(evento2);
            change.putExtra("deportista", miPerfil);
            change.putExtra("tipo", "0");
            startActivity(change);
        }else if(id == R.id.nav_cerrar_sesion){
            Intent change = new Intent(this, InicioDeSesión.class);
            Toast.makeText(this,"Sesion cerrada",Toast.LENGTH_LONG).show();
            startActivity(change);
        }
        //Agregar todos los Intents

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toInformacionEvento(View v){
            Intent intent = new Intent(v.getContext(), InformacionEvento.class);
            intent.putExtra("pantalla",1);
            startActivity(intent);
    }

}
