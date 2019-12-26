package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.Model.Calon;
import com.simamat.musranpripmmuska.Model.Pemilih;
import com.simamat.musranpripmmuska.ViewHolder.CalonViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PilihanActivity extends AppCompatActivity {

    private RecyclerView rvCalon;
    private FirebaseRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View diaogView;
    TextView tvDialogPilihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);

        //setting action bar
        getSupportActionBar().setTitle("Pilih Calon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //load calon
        rvCalon = (RecyclerView) findViewById(R.id.recycle_view);
        rvCalon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvCalon.setLayoutManager(layoutManager);
        
        loadCalon();

    }

    private void loadCalon() {

        //query firebasse database menampilkan seluruh data di table Calon
        Query query = FirebaseDatabase.getInstance()
                .getReference("Calon");

        final FirebaseRecyclerOptions<Calon> options =
                new FirebaseRecyclerOptions.Builder<Calon>()
                .setQuery(query, new SnapshotParser<Calon>() {
                    @NonNull
                    @Override
                    public Calon parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Calon(snapshot.child("image").getValue().toString(),
                                snapshot.child("nama").getValue().toString(),
                                snapshot.child("nomer").getValue().toString());
                    }
                })
                .build();

        //adapter recycle view Pilih Calon
        adapter = new FirebaseRecyclerAdapter<Calon, CalonViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CalonViewHolder holder, int position, @NonNull final Calon model) {
                holder.tvCalonNama.setText(model.getNama());
                Picasso.get()
                        .load(model.getImage())
                        .resize(108, 108)
                        .centerCrop()
                        .into(holder.ivCalonImage);
                holder.tvCalonNomer.setText(model.getNomer());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //get data from IsiDataPemilih Activity
                        Intent intent = getIntent();
                        final String namaPemilih = intent.getStringExtra("nama");
                        final String kelasPemilih = intent.getStringExtra("kelas");
                        final String pilihan = options.getSnapshots().getSnapshot(position)
                                .child("nama").getValue().toString();

                        //init firebase
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference table_pemilih = database.getReference("Pemilih").push();

                        dialog = new AlertDialog.Builder(PilihanActivity.this);
                        inflater = getLayoutInflater();
                        diaogView = inflater.inflate(R.layout.dialog_pilihan, null);
                        dialog.setView(diaogView);
                        dialog.setCancelable(true);

                        tvDialogPilihan = (TextView) diaogView.findViewById(R.id.tv_dialog_nama_pilihan);
                        tvDialogPilihan.setText(pilihan);

                        dialog.setPositiveButton("Iya, Saya yakin", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", table_pemilih.getKey());
                                map.put("nama", namaPemilih);
                                map.put("kelas", kelasPemilih);
                                map.put("pilihan", pilihan);
                                table_pemilih.setValue(map);
                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), PopActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        dialog.setNegativeButton("Saya coba pikir lagi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                });
//
            }

            @NonNull
            @Override
            public CalonViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calon_item, parent, false);
                final CalonViewHolder holder = new CalonViewHolder(view);
                return holder;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,IsiDataPemilihActivity.class));
        finish();
    }
}
