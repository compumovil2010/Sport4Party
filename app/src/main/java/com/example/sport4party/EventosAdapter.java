package com.example.sport4party;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sport4party.Modelo.Evento;

import java.util.ArrayList;

public class EventosAdapter extends BaseAdapter {
    Context context;
    ArrayList<Evento> eventos;

    public EventosAdapter(Context context, ArrayList<Evento> eventos) {
        this.context = context;
        this.eventos = eventos;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.evento,parent,false);
        TextView idEvento = (TextView)convertView.findViewById(R.id.textViewNumero);
        TextView nombreEvento = (TextView)convertView.findViewById(R.id.textViewNombreEvento);
        Button infoEvento = (Button) convertView.findViewById(R.id.buttonVer);

        //idEvento.setText(eventos.get(position).getID());
        idEvento.setText(Integer.toString(eventos.get(position).getID()));
        nombreEvento.setText(eventos.get(position).getDeporte());

        infoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(v.getContext(), InformacionEvento.class);
                info.putExtra("pantalla",0);
                context.startActivity(info);
            }
        });
        return convertView;
    }
}
