package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.simamat.musranpripmmuska.Model.Calon;
import com.simamat.musranpripmmuska.Model.Pemilih;
import com.simamat.musranpripmmuska.ViewHolder.PemilihViewHolder;

public class DaftarPemilihActivity extends AppCompatActivity {

    private RecyclerView rvPemilih;
    private FirebaseRecyclerAdapter adapterPemilih;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pemilih);

        //setting actionbar
        getSupportActionBar().setTitle("Daftar Pemilih");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //load pemilih
        rvPemilih = (RecyclerView) findViewById(R.id.recycle_view_daftar_pemilih);
        rvPemilih.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvPemilih.setLayoutManager(layoutManager);

        loadCalon();

    }

    private void loadCalon() {

        //get data intent dari HomeAdminActivity
        Intent intent = getIntent();
        final String namaCalon = intent.getStringExtra("namaCalon");

        //firebase database query menampilkan seluruh pemilih dari nama terpilih
        Query query = FirebaseDatabase.getInstance()
                .getReference("Pemilih")
                .orderByChild("pilihan")
                .equalTo(namaCalon);

        //parse query to Pemilih.java
        final FirebaseRecyclerOptions<Pemilih> options =
                new FirebaseRecyclerOptions.Builder<Pemilih>()
                        .setQuery(query, new SnapshotParser<Pemilih>() {
                            @NonNull
                            @Override
                            public Pemilih parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Pemilih(snapshot.child("id").getValue().toString(),
                                        snapshot.child("nama").getValue().toString(),
                                        snapshot.child("kelas").getValue().toString(),
                                        snapshot.child("pilihan").getValue().toString());
                            }
                        })
                        .build();

        //adapter untuk recycle view pemilih
        adapterPemilih = new FirebaseRecyclerAdapter<Pemilih, PemilihViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PemilihViewHolder holder, int position, @NonNull Pemilih model) {
                holder.tvPemilihNama.setText(model.getNama());
                holder.tvPemilihKelas.setText(model.getKelas());
            }


            @NonNull
            @Override
            public PemilihViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
                final View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pemilih_item, parent, false);
                return new PemilihViewHolder(view);
            }
        };

        rvPemilih.setAdapter(adapterPemilih);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterPemilih.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterPemilih.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish(); // keluar activity ini dan kebali ke activity sebelumnya
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeAdminActivity.class));
        finish();
    }

}
