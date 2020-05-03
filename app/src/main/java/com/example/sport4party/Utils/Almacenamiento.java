package com.example.sport4party.Utils;


import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Almacenamiento {
    private String PATH;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //@Override
    public void onLoadUserResponse(Object entrada)
    {

    }
    //@Override
    public void onLoadUserError()
    {

    }
    public Almacenamiento(String PATH) {
        this.PATH = PATH;
        database=FirebaseDatabase.getInstance();
    }

    public void push(Object objectoPush, String referencia) {
        myRef=database.getReference(PATH+referencia);
        myRef.setValue(objectoPush);
    }

    public void erase(String referencia) {
        myRef=database.getReference(PATH+referencia);
        myRef.removeValue();
    }

    public void loadOnce(final Class valueType) {
        myRef=database.getReference(PATH);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren())
                {

                    Object entrada=singleSnapShot.getValue(valueType);
                    onLoadUserResponse(entrada);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                onLoadUserError();
            }
        });
    }

    public void loadSubscription(final Class valueType) {
        myRef=database.getReference(PATH);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapShot: dataSnapshot.getChildren())
                {
                    Object entrada=singleSnapShot.getValue(valueType);
                    onLoadUserResponse(entrada);
                }
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
