package com.example.sport4party;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sport4party.Modelo.Deportista;

import java.util.ArrayList;


public class DeportistaAdapter extends BaseAdapter {

    private Context aContext;
    //Lista de deportistas que maneja el adaptador
    private ArrayList<Deportista> deports;
    //Boolean para indicar si se va a desplegar en pantalla de Ver participantes
    private boolean enParticipantes;
    //Boolena para indicar si se va a desplegar en pantalla de Invitar amigos
    private boolean invitarAmigos;

    public DeportistaAdapter(Context nContext, ArrayList<Deportista>nDeports, boolean nparticipantes, boolean ninvitar){
        this.aContext = nContext;
        this.deports = nDeports;
        this.enParticipantes = nparticipantes;
        this.invitarAmigos = ninvitar;
    }

    @Override
    public int getCount() {
        return deports.size();
    }

    @Override
    public Object getItem(int position) {
        return deports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = View.inflate(aContext, R.layout.casilla_participante,null);

        ArrayList<ImageButton> stars = new ArrayList<>();

        TextView userName = vista.findViewById(R.id.userName);
        TextView userLevel = vista.findViewById(R.id.userLevel);
        Button add = vista.findViewById(R.id.addButton);
        ImageButton star1 = vista.findViewById(R.id.star1);
        ImageButton star2 = vista.findViewById(R.id.star2);
        ImageButton star3 = vista.findViewById(R.id.star3);
        ImageButton star4 = vista.findViewById(R.id.star4);
        ImageButton star5 = vista.findViewById(R.id.star5);

        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);

        userName.setText(deports.get(position).getNombre());
        String popularity = deports.get(position).getNivelHabilidad();
        userLevel.setText(popularity);
        if(popularity.equals("Bueno")){
            userLevel.setTextColor(Color.GREEN);
        }else if(popularity.equals("Regular")){
            userLevel.setTextColor(Color.YELLOW);
        }else if(popularity.equals("Malo")){
            userLevel.setTextColor(Color.RED);
        }

        setStars(deports.get(position),stars);

        if(enParticipantes){
            add.setText("Agregar");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Participante agregado a la lista de amigos",Toast.LENGTH_LONG).show();
                }
            });
        }else if(invitarAmigos){
            add.setText("Invitar");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Amigo agregado al evento",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            add.setVisibility(View.GONE);
            star1.setClickable(false);
            star2.setClickable(false);
            star3.setClickable(false);
            star4.setClickable(false);
            star5.setClickable(false);
        }

        return vista;
    }

    public void setStars(Deportista toSet, ArrayList<ImageButton> stars){
        for(int i = 0; i < Math.round(toSet.getPopularidad()); i++){
            stars.get(i).setImageResource(R.drawable.icons8_star_30px);
        }
    }
}
