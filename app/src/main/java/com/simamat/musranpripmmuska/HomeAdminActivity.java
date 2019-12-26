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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.simamat.musranpripmmuska.Common.Common;
import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.Model.Calon;
import com.simamat.musranpripmmuska.ViewHolder.CalonViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class HomeAdminActivity extends AppCompatActivity {

    private RecyclerView rvCalonHome;
    private FirebaseRecyclerAdapter adapterHome;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseReference pemilihRef;
    private int countPemilih = 0;

    private TextView tvTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        //setting actionbar title
        getSupportActionBar().setTitle("Home Admin");

        //load calon
        rvCalonHome = (RecyclerView) findViewById(R.id.recycle_view_home_admin);
        tvTotalCount = (TextView) findViewById(R.id.tv_total_count);

        rvCalonHome.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvCalonHome.setLayoutManager(layoutManager);

        loadCalonHome();

    }

    private void loadCalonHome() {

        //query firebase database menampilkan seluruh data di table Calon
        Query query = FirebaseDatabase.getInstance()
                .getReference("Calon");

        //parse gambar,nama,monor urut calon ke Calon.java
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

        //adapter recycle view calon ketua
        adapterHome = new FirebaseRecyclerAdapter<Calon, CalonViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CalonViewHolder holder, final int position, @NonNull final Calon model) {
                holder.tvCalonNama.setText(model.getNama());
                Picasso.get()
                        .load(model.getImage())
                        .resize(108, 108)
                        .centerCrop()
                        .into(holder.ivCalonImage);
                holder.tvCalonNomer.setText(model.getNomer());

                pemilihRef = FirebaseDatabase.getInstance().getReference("Pemilih");
                final String pilihan = options.getSnapshots().getSnapshot(position)
                        .child("nama").getValue().toString();

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent moveDaftarPemilih = new Intent(HomeAdminActivity.this,
                                DaftarPemilihActivity.class);
                        moveDaftarPemilih.putExtra("namaCalon", pilihan);
                        startActivity(moveDaftarPemilih);
                        finish();
                    }
                });

                pemilihRef.orderByChild("pilihan").equalTo(pilihan).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (pilihan.equals(model.getNama())) {
                                countPemilih = (int) dataSnapshot.getChildrenCount();
                                holder.tvJumlahSuaraCalon.setText(Integer.toString(countPemilih));
                            }
                        } else {
                            holder.tvJumlahSuaraCalon.setText("0");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                pemilihRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (pilihan.equals(model.getNama())) {
                                countPemilih = (int) dataSnapshot.getChildrenCount();
                                tvTotalCount.setText("Total suara masuk : "+Integer.toString(countPemilih));
                            }
                        } else {
                            tvTotalCount.setText("Total suara masuk : 0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public CalonViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calon_item_admin, parent, false);
                final CalonViewHolder holder = new CalonViewHolder(view);
                return holder;
            }
        };
        rvCalonHome.setAdapter(adapterHome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterHome.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterHome.stopListening();
    }

}
