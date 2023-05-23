package com.example.hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {


    List<Todo> data;
    Context c;




    public Adapter(Context context,List<Todo> list) {

        this.data=new ArrayList<>();
        this.data=list;
        this.c=context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);

        View v=inflater.inflate(R.layout.card,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {

        Todo t=data.get(i);

        holder.name.setText(t.getTopic());
        holder.intro.setText(t.getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class Holder extends RecyclerView.ViewHolder{


        TextView name,intro;
        //Button more;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            intro=itemView.findViewById(R.id.intro);
            //more=itemView.findViewById(R.id.more);
        }
    }

}
