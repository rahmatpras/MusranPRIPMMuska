package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simamat.musranpripmmuska.Model.Pemilih;

public class IsiDataPemilihActivity extends AppCompatActivity {

    EditText etNamaPemilih, etKelasPemilih;
    Button btnSubmitPemilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data_pemilih);

        etNamaPemilih = (EditText) findViewById(R.id.et_nama_pemilih);
        etKelasPemilih = (EditText) findViewById(R.id.et_kelas_pemilih);
        btnSubmitPemilih = (Button) findViewById(R.id.btn_submit_pemilih);

        //init firebase
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference table_pemilih = database.getReference("Pemilih");

        btnSubmitPemilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(IsiDataPemilihActivity.this);
                mDialog.setMessage("Mohon Tunggu ....");
                mDialog.show();

//                table_pemilih.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        mDialog.dismiss();
//                        Pemilih pemilih = new Pemilih(etKelasPemilih.getText().toString());
//                        table_pemilih.child(etNamaPemilih.getText().toString()).setValue(pemilih);
//                        Toast.makeText(IsiDataPemilihActivity.this, "Nama terinput", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
                Intent movePilihan = new Intent(IsiDataPemilihActivity.this, PilihanActivity.class);
                String texNama = (etNamaPemilih.getText().toString());
                String textKelas = (etKelasPemilih.getText().toString());
                movePilihan.putExtra("nama", texNama);
                movePilihan.putExtra("kelas", textKelas);
                mDialog.dismiss();
                startActivity(movePilihan);
                finish();
            }
        });

    }
}
