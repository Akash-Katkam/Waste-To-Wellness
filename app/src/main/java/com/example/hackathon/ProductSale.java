package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductSale extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference root;
    EditText desc,topic;
    FloatingActionButton add;
    //String[] names={"CaptainAmerica","IranMan","SpiderMan","Thor","AntMan","CaptainMarvel","BlackPather","Dr. Hulk","StarLoard","Gomoora","Groot"};

    RecyclerView rc;

    List<todo2> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sale);

        add=findViewById(R.id.add);
        topic=findViewById(R.id.topic);
        desc=findViewById(R.id.desc);
        rc=findViewById(R.id.rc);


        rc.setLayoutManager(new LinearLayoutManager(ProductSale.this));

        list=new ArrayList<>();

        database=FirebaseDatabase.getInstance();
        root=database.getReference("todo2");


        //recieving the data from server
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                try {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        todo2 t = d.getValue(todo2.class);

                        list.add(t);
                    }
                    rc.setAdapter(new Adapter2(ProductSale.this, list));
                } catch (Exception e){}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //rc=findViewById(R.id.rc);
        //rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //Adapter a=new Adapter(MainActivity.this,names);

        //rc.setAdapter(a);
        //root.setValue(new Todo("math","Today i have to do chapter 12","not done"));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=root.push().getKey();
                root.child(key).setValue(new Todo(topic.getText().toString(),desc.getText().toString(),"not done")).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductSale.this,"added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

}
