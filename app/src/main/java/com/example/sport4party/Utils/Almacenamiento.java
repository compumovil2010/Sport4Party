package com.example.sport4party.Utils;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sport4party.Modelo.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Almacenamiento {
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //@Override
    public void onLoadUserError() { }

    public Almacenamiento(){
        database=FirebaseDatabase.getInstance();
    }

    public void addValueToReference(String path, String value){
        myRef = database.getReference(path);
        myRef.setValue(value);
    }
    ///Usar cuando se vaya a empujar un objeto nuevo
    public String push(Object objectoPush, String path) {
        myRef=database.getReference(path);
        String key= myRef.push().getKey();
        myRef=database.getReference(path+key);
        myRef.setValue(objectoPush);
        return key;

    }
    ///usar cuando vaya a hacerle push a un objeto ya disponible siendo el id el objeto especifico
    public void push(Object objectoPush, String path, String id) {
        myRef=database.getReference(path+id);
        myRef.setValue(objectoPush);
    }

    public void erase(String referencia) {
        myRef=database.getReference(referencia);
        myRef.removeValue();
    }

    //@Override
    public void onBuscarResult(HashMap<String,Object> data, String key)
    {

    }

    public void buscarPorID(String path, final String id)
    {
        myRef=database.getReference(path);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren())
                {
                    if(singleSnapShot.getKey().equals(id))
                    {
                        onBuscarResult((HashMap<String,Object>)singleSnapShot.getValue(), singleSnapShot.getKey());
                    }
                }
                onComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onLoadUserError();
            }
        });



    }

    //@Override
    public void leerDatos(HashMap<String, Object> datos, DataSnapshot singleSnapShot){ }
    //@Override
    public void onComplete()
    {

    }
    public void loadOnce(String path) {
        myRef=database.getReference(path);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren())
                {


                    HashMap<String, Object> datos = (HashMap<String,Object>) singleSnapShot.getValue();
                    leerDatos(datos, singleSnapShot);

                    /*
                        HashMap<String, Object> datos = (HashMap<String, Object>) singleSnapShot.getValue();
                        for(String i : datos.keySet()){
                            Log.i("DATOS", "KEY: " + i + " VALUE: " + datos.get(i));
                        }
                        if(datos.containsKey("amigos")){
                            HashMap<String,String> amigitos= (HashMap<String,String>)datos.get("amigos");
                            for(String i : amigitos.keySet()){
                                Log.i("AMIGUITOS", "KEY: " + i + " VALUE: " + datos.get(i));
                            }
                        }
                     */

                }
                onComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onLoadUserError();
            }
        });
    }

    //nesesita Override
    public void leerDatosSubscrito(HashMap<String, Object> datos, DataSnapshot singleSnapShot){ }

    public void loadSubscription(String path) {
        myRef=database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren())
                {
                    HashMap<String, Object> datos = (HashMap<String,Object>) singleSnapShot.getValue();
                    leerDatosSubscrito(datos, singleSnapShot);
                }
                onComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onLoadUserError();
            }
        });
    }

    public void stopLoad() {
        if(myRef!=null){
            //myRef.removeEventListener();
        }
    }

}
