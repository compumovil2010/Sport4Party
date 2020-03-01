package com.example.sport4party;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class EventosAdapter extends CursorAdapter {
    private static final int EVENTO_ID_INDICE = 0;
    private  static final int EVENTO_NOMBRE_INDICE = 1;

    public EventosAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.evento, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewNumero = (TextView) view.findViewById(R.id.textViewNumero);
        TextView textViewNombreEvento = (TextView) view.findViewById(R.id.textViewNombreEvento);
        int idEvemto = cursor.getInt(EVENTO_ID_INDICE);
        String nombreEvento = cursor.getString(EVENTO_NOMBRE_INDICE);
        textViewNumero.setText(String.valueOf(idEvemto));
        textViewNombreEvento.setText(nombreEvento);
    }
}
