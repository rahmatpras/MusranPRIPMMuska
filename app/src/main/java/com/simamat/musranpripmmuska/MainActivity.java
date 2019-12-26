package com.simamat.musranpripmmuska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAdmin, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdmin = (Button) findViewById(R.id.btn_admin);
        btnUser = (Button) findViewById(R.id.btn_user);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveUser = new Intent(MainActivity.this, UserActivity.class);
                startActivity(moveUser);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveUser = new Intent(MainActivity.this, SignInAdminActivity.class);
                startActivity(moveUser);
            }
        });

    }
}
