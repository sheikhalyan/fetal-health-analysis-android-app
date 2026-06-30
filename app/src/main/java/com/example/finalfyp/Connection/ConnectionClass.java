package com.example.finalfyp.Connection;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
private static final String SERVER = "YOUR_SERVER_IP"; // Replace with your SQL Server IP
private static final String USER = "YOUR_DB_USER";
private static final String PASSWORD = "YOUR_DB_PASSWORD";
private static final String DATABASE = "fetalhealthanalysis";

    @SuppressLint("NewApi")
    public static Connection connectionClass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName(JDBC_DRIVER);
            connectionURL = "jdbc:jtds:sqlserver://" + SERVER + "/" + DATABASE + ";user=" + USER + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception e) {
            Log.e("SQL Connection Error: ", e.getMessage());
        }
        return connection;
    }
}

