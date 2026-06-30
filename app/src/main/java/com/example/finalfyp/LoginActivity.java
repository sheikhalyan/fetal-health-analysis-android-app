package com.example.finalfyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    EditText userEmail, inputPassword;
    TextView txtsignup;
    SessionManager sessionManager;
    AppCompatButton btn_success, btn_failed;
    AlertDialog.Builder builderdialog;
    AlertDialog alertDialog;
    private String username;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        userEmail = findViewById(R.id.userEmail);
        inputPassword = findViewById(R.id.inputPassword);
        button = findViewById(R.id.btnLogin);
        txtsignup = findViewById(R.id.txtsignup);
        if (sessionManager.isLoggedIn()) {
            // Redirect to the dashboard if already logged in
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
            return;
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new CheckLoginTask().execute();

            }
        });

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showAlertDailog(int myLayout, final boolean isSuccess) {
        if (!isFinishing() && !isDestroyed()) {
            builderdialog = new AlertDialog.Builder(this);
            View layoutView = getLayoutInflater().inflate(myLayout, null);
            AppCompatButton dialogButton;

            if (isSuccess) {
                dialogButton = layoutView.findViewById(R.id.buttonOk);
            } else {
                dialogButton = layoutView.findViewById(R.id.buttonfailed);
            }

            if (dialogButton == null) {
                Log.e("LoginActivity", "Button not found in layout.");
                return;
            }

            builderdialog.setView(layoutView);
            alertDialog = builderdialog.create();
            alertDialog.show();


            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if (isSuccess) {
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        intent.putExtra("name", username);
                        startActivity(intent);
                        finish();
                    } else {
                        // Stay on the login screen
                    }
                }
            });
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class CheckLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            //String email = userEmail.getText().toString();
            boolean isSuccess = false;
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = ConnectionClass.connectionClass();

                if (connection != null) {
                    String email = userEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    String sql = "SELECT * FROM RegisteredUserss WHERE email = ? AND password = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        username = resultSet.getString("name");

                        isSuccess = true;
                    }


//                    isSuccess = resultSet.next();
                }
            } catch (SQLException e) {
                Log.e("LoginActivity", "SQL Error: " + e.getMessage());
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        Log.e("LoginActivity", "Error closing ResultSet: " + e.getMessage());
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        Log.e("LoginActivity", "Error closing PreparedStatement: " + e.getMessage());
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        Log.e("LoginActivity", "Error closing Connection: " + e.getMessage());
                    }
                }
            }
            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                sessionManager.createLoginSession(username, userEmail.getText().toString().trim(), inputPassword.getText().toString().trim());
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                showAlertDailog(R.layout.my_success_dialog, true);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                showAlertDailog(R.layout.my_failed_dialog, false);
            }
        }

    }
}