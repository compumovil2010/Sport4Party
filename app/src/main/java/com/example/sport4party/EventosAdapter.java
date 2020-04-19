package com.example.sport4party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.sport4party.Modelo.Evento;

import java.util.List;

public class EventosAdapter extends BaseAdapter {
    Context context;
    List<Evento> eventos;
    boolean enMisEventos;
    boolean enPerfil;

    public EventosAdapter(Context context, List<Evento> eventos, boolean nEnMisEventos, boolean nEnPerfil) {
        this.context = context;
        this.eventos = eventos;
        this.enMisEventos = nEnMisEventos;
        this.enPerfil = nEnPerfil;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventos.get(position).getID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(enMisEventos){
            convertView = LayoutInflater.from(context).inflate(R.layout.evento,parent,false);
            TextView idEvento = (TextView)convertView.findViewById(R.id.idEventoList);
            TextView nombreEvento = (TextView)convertView.findViewById(R.id.nombreEventoList);
            ImageButton infoEvento = (ImageButton) convertView.findViewById(R.id.botonInfoEventoList);
            ImageButton rutaEvento = (ImageButton) convertView.findViewById(R.id.botonRuta);


            //idEvento.setText(eventos.get(position).getID());
            idEvento.setText(Integer.toString(eventos.get(position).getID()));
            nombreEvento.setText(eventos.get(position).getDeporte().getNombre());

            infoEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent info = new Intent(v.getContext(), InformacionEvento.class);
                    info.putExtra("pantalla",0);
                    context.startActivity(info);
                }
            });

            rutaEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent change = new Intent(v.getContext(), RutaEvento.class);
                    change.putExtra("latitud", eventos.get(position).getUbicacion().getLatitud().doubleValue());
                    change.putExtra("longitud", eventos.get(position).getUbicacion().getLongitud().doubleValue());
                    context.startActivity(change);
                }
            });
        }
        else if(enPerfil){
            convertView = LayoutInflater.from(context).inflate(R.layout.evento_en_perfil,parent,false);
            TextView idEvento = (TextView)convertView.findViewById(R.id.idEventoList);
            TextView nombreEvento = (TextView)convertView.findViewById(R.id.nombreEventoList);
            Button infoEvento = (Button) convertView.findViewById(R.id.botonInfoEventoList);

            idEvento.setText(Integer.toString(eventos.get(position).getID()));
            nombreEvento.setText(eventos.get(position).getDeporte().getNombre());

            if(eventos.get(position).isPrivado()){
                infoEvento.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrivateEvent));
            }else{
                infoEvento.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorPublicEvent));
            }

            infoEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent info = new Intent(v.getContext(), InformacionEvento.class);
                    info.putExtra("pantalla",0);
                    context.startActivity(info);
                }
            });
        }


        return convertView;
    }

}
