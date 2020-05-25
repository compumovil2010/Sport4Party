package com.example.sport4party;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sport4party.Modelo.Mensaje;

import java.text.SimpleDateFormat;
import java.util.List;

public class MensajeAdapter extends BaseAdapter {
    Context context;
    List<Mensaje> mensajes;

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        this.context = context;
        this.mensajes = mensajes;
    }

    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return mensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mensaje, parent,false);
        TextView remitente = (TextView)convertView.findViewById(R.id.textViewRemitente);
        TextView contenido = (TextView)convertView.findViewById(R.id.textViewContenido);
        TextView fecha = (TextView)convertView.findViewById(R.id.textViewFecha);

        Mensaje mensaje = mensajes.get(position);
        String formato = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        remitente.setText(mensaje.getRemitente().getNombreUsuario());
        contenido.setText(mensaje.getContenido());
        fecha.setText(dateFormat.format(mensaje.getFecha()));
        return convertView;
    }
}
