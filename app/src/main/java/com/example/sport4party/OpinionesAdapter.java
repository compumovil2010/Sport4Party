package com.example.sport4party;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.sport4party.Modelo.Opinion;

import java.util.ArrayList;

public class OpinionesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Opinion> opiniones;

    public OpinionesAdapter(Context context, ArrayList<Opinion> opinion) {
        this.context = context;
        this.opiniones = opinion;
    }

    @Override
    public int getCount() {
        return opiniones.size();
    }

    @Override
    public Object getItem(int position) {
        return opiniones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.opinion,parent,false);
        TextView comentario = (TextView)convertView.findViewById(R.id.comentario);
        TextView nombreUsuario = (TextView)convertView.findViewById(R.id.userDelComentario);

        comentario.setText(opiniones.get(position).getDescripcion());
        nombreUsuario.setText("por: "+opiniones.get(position).getRemitente().getNombreUsuario());

        //Faltaria funcionalidad para calcular las estrellas
        return convertView;
    }
}
