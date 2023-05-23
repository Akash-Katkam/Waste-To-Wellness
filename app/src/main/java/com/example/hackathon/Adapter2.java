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

public class Adapter2 extends RecyclerView.Adapter<Adapter.Holder> {
    List<todo2> data;
    Context c;

    public Adapter2(Context context, List<todo2> list) {

        this.data=new ArrayList<>();
        this.data=list;
        this.c=context;
    }
    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);

        View v=inflater.inflate(R.layout.card,parent,false);
        return new Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {
        todo2 t=data.get(position);

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
