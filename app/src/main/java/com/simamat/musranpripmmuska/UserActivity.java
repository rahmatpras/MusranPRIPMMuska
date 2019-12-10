package com.simamat.musranpripmmuska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.simamat.musranpripmmuska.Common.Common;
import com.simamat.musranpripmmuska.Model.User;

public class UserActivity extends AppCompatActivity {

    private EditText etUsernameUser, etPasswordUser;
    private Button btnSubmitUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        etUsernameUser = (EditText) findViewById(R.id.et_username_user);
        etPasswordUser = (EditText) findViewById(R.id.et_password_user);
        btnSubmitUser = (Button) findViewById(R.id.btn_submit_user);

        //init firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSubmitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(UserActivity.this);
                mDialog.setMessage("Mohon Tunggu ....");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //cek data
                        if (dataSnapshot.child(etUsernameUser.getText().toString()).exists()) {
                            //ambil informasi user
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etUsernameUser.getText().toString()).getValue(User.class);
                            if (etPasswordUser.getText().toString().equals(user.getPassword())) {
                                Intent isiDataPemilihIntent = new Intent(UserActivity.this, IsiDataPemilihActivity.class);
                                Common.currentUser = user;
                                startActivity(isiDataPemilihIntent);
                                finish();
                            } else {
                                Toast.makeText(UserActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
//                            Toast.makeText(SignInActivity.this, "User tidak ada di database", Toast.LENGTH_SHORT).show();
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserActivity.this);
                            alertDialog.setMessage("User salah !!!")
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
}
