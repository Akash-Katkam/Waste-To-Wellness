package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    RecyclerView recyclerView;
	    Context context;
	    private List<Club> clubs;

        	    @Override
	    public void onCreate(Bundle savedInstanceState) {
        	        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_leaderboard);

        	        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        	        recyclerView.setHasFixedSize(true);

        	        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        	        recyclerView.setLayoutManager(layoutManager);

        	        RecyclerViewAdapter adapter = new RecyclerViewAdapter(clubs);

        	        initializeData();
        	        initializeAdapter();

        	    }

        	    private void initializeData(){
        	        clubs = new ArrayList<>();
					clubs.add(new Club("Hotel palce", R.drawable.hotel));
					clubs.add(new Club("Hotel Lord", R.drawable.hotel));
					clubs.add(new Club("Lord's Garden", R.drawable.garden));
					clubs.add(new Club("Kings and Knight garden", R.drawable.garden));
					clubs.add(new Club("Hotel ", R.drawable.hotel));
					clubs.add(new Club("Garden", R.drawable.garden));
					clubs.add(new Club("Hotel palce", R.drawable.hotel));
					clubs.add(new Club("Hotel Lord", R.drawable.hotel));
					clubs.add(new Club("Lord's Garden", R.drawable.garden));
					clubs.add(new Club("Kings and Knight garden", R.drawable.garden));
					clubs.add(new Club("Hotel ", R.drawable.hotel));
					clubs.add(new Club("Garden", R.drawable.garden));

				}

        	    private void initializeAdapter(){
        	        RecyclerViewAdapter adapter = new RecyclerViewAdapter(clubs);
        	        recyclerView.setAdapter(adapter);
        	    }
	}

        	class Club {
	    String name;
	    int logoId;

        	    Club(String name, int logoId) {
        	        this.name = name;
        	        this.logoId = logoId;
        	    }
	}