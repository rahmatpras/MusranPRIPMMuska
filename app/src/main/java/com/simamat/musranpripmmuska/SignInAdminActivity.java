package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simamat.musranpripmmuska.Common.Common;
import com.simamat.musranpripmmuska.Model.Admin;

public class SignInAdminActivity extends AppCompatActivity {

    private EditText etUsernameAdmin, etPasswordAdmin;
    private Button btnSubmitAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_admin);

        getSupportActionBar().setTitle("Sign In Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etUsernameAdmin = (EditText) findViewById(R.id.et_username_admin);
        etPasswordAdmin = (EditText) findViewById(R.id.et_password_admin);
        btnSubmitAdmin = (Button) findViewById(R.id.btn_submit_admin);

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_admin = database.getReference("Admin");

        btnSubmitAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignInAdminActivity.this);
                mDialog.setMessage("Mohon Tunggu ....");
                mDialog.show();

                table_admin.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //cek data
                        if (dataSnapshot.child(etUsernameAdmin.getText().toString()).exists()) {
                            //ambil informasi user
                            mDialog.dismiss();
                            Admin admin = dataSnapshot.child(etUsernameAdmin.getText().toString()).getValue(Admin.class);
                            if (etPasswordAdmin.getText().toString().equals(admin.getPassword().toString())) {
                                Intent homeAdmin = new Intent(SignInAdminActivity.this, HomeAdminActivity.class);
                                Common.currentAdmin = admin;
                                startActivity(homeAdmin);
                                finish();
                            } else {
                                Toast.makeText(SignInAdminActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignInAdminActivity.this);
                            alertDialog.setMessage("Username salah !!!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
