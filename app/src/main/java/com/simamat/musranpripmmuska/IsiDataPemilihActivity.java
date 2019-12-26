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

        //menghilangkan actionbar di activity ini
        getSupportActionBar().hide();

        etNamaPemilih = (EditText) findViewById(R.id.et_nama_pemilih);
        etKelasPemilih = (EditText) findViewById(R.id.et_kelas_pemilih);
        btnSubmitPemilih = (Button) findViewById(R.id.btn_submit_pemilih);

        btnSubmitPemilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(IsiDataPemilihActivity.this);
                mDialog.setMessage("Mohon Tunggu ....");
                mDialog.show();

                Intent movePilihan = new Intent(IsiDataPemilihActivity.this, PilihanActivity.class);

                //pindah activity dan mengirim data ke activity yang dituju
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
