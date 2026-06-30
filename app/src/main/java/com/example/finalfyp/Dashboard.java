package com.example.finalfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    SessionManager sessionManager;
    TextView getname;
    LinearLayout btnAbdominal,btnBpd,btnAloka,btnVle6,BtnVls8,BtnVls10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(getApplicationContext());
        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        getname = findViewById(R.id.getname);
        btnAbdominal = findViewById(R.id.btnAbdominal);
        btnBpd = findViewById(R.id.btnBpd);
        btnAloka = findViewById(R.id.btnAloka);
        btnVle6 = findViewById(R.id.btnVle6);
        BtnVls8 = findViewById(R.id.BtnVls8);
        BtnVls10 = findViewById(R.id.BtnVls10);
        String username = getIntent().getStringExtra("name");
        getname.setText(username);
        // Set the username to the TextView
        // Check if the user is logged in
        if (!sessionManager.isLoggedIn()) {
            // Redirect to LoginActivity if not logged in
            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);

            }
        });
        btnAbdominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AbdominalCircumference.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });

        btnBpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, BPDIameter.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
        btnAloka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Aloka.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
        btnVle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, VolusionE6.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
        BtnVls8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, VolusionS8.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
        BtnVls10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, VolusionS10.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
    }



    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    sessionManager.logoutUser();
                    // Handle logout action
                    Toast.makeText(Dashboard.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}