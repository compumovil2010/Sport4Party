package com.example.sport4party;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
        ImageView star1=(ImageView)convertView.findViewById(R.id.star1);
        ImageView star2=(ImageView)convertView.findViewById(R.id.star2);
        ImageView star3=(ImageView)convertView.findViewById(R.id.star3);
        ImageView star4=(ImageView)convertView.findViewById(R.id.star4);
        ImageView star5=(ImageView)convertView.findViewById(R.id.star5);

        comentario.setText(opiniones.get(position).getDescripcion());
        nombreUsuario.setText("por: "+opiniones.get(position).getRemitente().getNombreUsuario());
        int calificacion=opiniones.get(position).getCalificacion().intValue();

            star1.setImageResource(R.drawable.star_empty);
            star2.setImageResource(R.drawable.star_empty);
            star3.setImageResource(R.drawable.star_empty);
            star4.setImageResource(R.drawable.star_empty);
            star5.setImageResource(R.drawable.star_empty);

        if(calificacion>=1)
        {
            star1.setImageResource(R.drawable.star);
        }
        if(calificacion>=2)
        {
            star2.setImageResource(R.drawable.star);
        }
        if(calificacion>=3)
        {
            star3.setImageResource(R.drawable.star);
        }
        if(calificacion>=4)
        {
            star4.setImageResource(R.drawable.star);
        }
        if(calificacion==5)
        {
            star5.setImageResource(R.drawable.star);
        }



        //Faltaria funcionalidad para calcular las estrellas

        return convertView;
    }
}
