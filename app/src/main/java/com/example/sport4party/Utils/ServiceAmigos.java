package com.example.sport4party.Utils;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sport4party.MisAmigos;
import com.example.sport4party.Perfil;
import com.example.sport4party.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceAmigos extends IntentService {
    private static final String CHANNEL_ID = "MyApp";
    int notificationId = 0;
    private NotificationCompat.Builder mBuilder;
    private FirebaseAuth mAuth;
    private ArrayList<String> idAmigos;
    private ArrayList<String> idAmigosUltimo;
    private boolean esPrimero = true;

    public ServiceAmigos() {
        super("ServiceAmigos");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mAuth = FirebaseAuth.getInstance();
        createNotification();
        createNotificationChannel();
        idAmigos = new ArrayList<>();
        idAmigosUltimo = new ArrayList<>();
        Almacenamiento almacenamiento = new Almacenamiento() {
            @Override
            public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot) {
                if (datos.containsKey("amigos")) {
                    DataSnapshot amigos = singleSnapShot.child("amigos/");
                    idAmigosUltimo.clear();
                    for (DataSnapshot i : amigos.getChildren()) {
                        idAmigosUltimo.add(i.getKey());
                        if (!idAmigos.contains(i.getKey())) {
                            idAmigos.add(i.getKey());
                            if (!esPrimero)
                                launchNotification("Nuevo amigo");
                        }
                    }
                    esPrimero = false;
                    for (int i = 0; i<idAmigos.size(); i++) {
                        String idAmigo = idAmigos.get(i);
                        if(!idAmigosUltimo.contains(idAmigo)){
                            launchNotification("Amigo eliminado");
                            idAmigos.remove(idAmigo);
                        }
                    }
                }
            }
        };
        almacenamiento.obtenerPorID("Jugador/", mAuth.getCurrentUser().getUid());
    }

    private void launchNotification(String mensaje) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        mBuilder.setContentTitle(mensaje);
        notificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }

    private void createNotification() {
        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_notifications_active)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(),
                        R.drawable.ic_stat_notifications_active))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, Perfil.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
