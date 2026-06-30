package com.example.finalfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalfyp.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    TextView txtsignin;
    EditText inputUserName, userEmail,inputPassword;
    Button btnRegister;
    SessionManager sessionManager;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtsignin = findViewById(R.id.txtsignin);
        btnRegister = findViewById(R.id.btnRegister);
        inputUserName = findViewById(R.id.inputUserName);
        userEmail = findViewById(R.id.userEmail);
        inputPassword = findViewById(R.id.inputPassword);



        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new RegisterUserTask().execute();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class RegisterUserTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(RegisterActivity.this, "Sending Data to Database", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String message = "";
            try {
                con = ConnectionClass.connectionClass();
                if (con == null) {
                    message = "Check Your Internet Connection";
                } else {
                    String sql = "INSERT INTO RegisteredUserss (name, email, password) VALUES ('" + inputUserName.getText().toString() + "', '" + userEmail.getText().toString() + "', '" + inputPassword.getText().toString() + "')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    message = "Registration Successful";
                }
            } catch (SQLException e) {
                message = e.getMessage();
                Log.e("SQL Error", e.getMessage());
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        Log.e("SQL Error", "Failed to close statement: " + e.getMessage());
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        Log.e("SQL Error", "Failed to close connection: " + e.getMessage());
                    }
                }
            }
            return message;
        }

        @Override
        protected void onPostExecute(String message) {
            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            if (message.equals("Registration Successful")) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                inputUserName.setText("");
                userEmail.setText("");
                inputPassword.setText("");
            }
        }
    }
}