package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.Model.Calon;
import com.simamat.musranpripmmuska.ViewHolder.CalonViewHolder;
import com.squareup.picasso.Picasso;

public class PilihanActivity extends AppCompatActivity {

    private RecyclerView rvCalon;
    private FirebaseRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);

        //load calon
        rvCalon = (RecyclerView) findViewById(R.id.recycle_view);
        rvCalon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvCalon.setLayoutManager(layoutManager);
        
        loadCalon();

    }

    private void loadCalon() {

        Query query = FirebaseDatabase.getInstance()
                .getReference("Calon");

        FirebaseRecyclerOptions<Calon> options =
                new FirebaseRecyclerOptions.Builder<Calon>()
                .setQuery(query, new SnapshotParser<Calon>() {
                    @NonNull
                    @Override
                    public Calon parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Calon(snapshot.child("image").getValue().toString(),
                                snapshot.child("nama").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<Calon, CalonViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CalonViewHolder holder, int position, @NonNull Calon model) {
                holder.tvCalonNama.setText(model.getNama());
                Picasso.get()
                        .load(model.getImage())
                        .resize(108, 108)
                        .centerCrop()
                        .into(holder.ivCalonImage);

               final Calon clickItem = model;
               holder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                       Toast.makeText(PilihanActivity.this, ""+clickItem.getNama(), Toast.LENGTH_SHORT).show();
                   }
               });
            }

            @NonNull
            @Override
            public CalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calon_item, parent, false);
                return new CalonViewHolder(view);
            }
        };
        rvCalon.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,IsiDataPemilihActivity.class));
        finish();
    }
}
