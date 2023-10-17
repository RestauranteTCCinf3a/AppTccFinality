package com.example.apprestaurant.conexao;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnection {

    private static final String TAG = "MSSQLConnectionHelper";

    // SOMEE.com com MSSQLSERVER hospedado em servidor na web - Run OK Emulador - Run OK Device Real
    private static String mStringServerIpName = "sql10.freesqldatabase.com";
    private static String mStringUserName = "sql10635461@ec2-52-8-112-233.us-west-1.compute.amazonaws.com";
    private static String mStringPassword = "root";
    private static String mStringDatabase = "sql10635461";
    private static String mStringPort = "3306";


    // MSSQLSERVER local - Run OK Emulador - Not RUN Device Real
//    private static String mStringServerIpName = "10.0.2.2"; // localhost 169.254.162.65  192.168.56.1    169.254.149.162 ou 192.168.0.16     127.0.0.1-192.168.0.15 your_server_ip - pesquise em sua casa 192.168.0.15 ??
//    private static String mStringUserName = "userApp"; //your_user_name_connect_database
//    private static String mStringPassword = "1234"; //your_password
//    private static String mStringDatabase = "dbLoginRegister"; //your_database_name
//    private static String mStringPort = "1433"; //your_server_port
//    private static String mStringInstance = "SQLEXPRESS";


    private static String mStringConnectionUrl; // objeto da String de Conexao ao Banco

    // método de conexão com o banco de dados
    public static Connection getConnection(Context mContext) {
        Connection mConnection = null;

        // adcione a dependencia no build.gradle (module)

        // atenção para importar jtds-1.3.1.jar  no  site oficial (http://jtds.sourceforge.net/)
        try {
            // adicionar política para criação de uma tarefa (thread)
            StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(mPolicy);

            // abordagem para MYSQL
            Class.forName("com.mysql.jdbc.Driver");
           //mStringConnectionUrl = "jdbc:mysql://sql10.freesqldatabase.com/sql10635461","sql10635461","root";

            // abordagem para MSSQLServer Local
            // mStringConnectionUrl = "jdbc:jtds:sqlserver://" + mStringServerIpName + ":" + mStringPort + "/" + mStringDatabase + ";instance=" + mStringInstance + ";user=" + mStringUserName + ";password=" + mStringPassword + ";"; // integratedSecurity=true;"; //;encrypt=true;trustServerCertificate=false;loginTimeout=30;
            // teste mStringConnectionUrl = "jdbc:jtds:sqlserver://10.0.2.2:1433/dbLoginRegister;instance=SQLEXPRESS;user=userApp;password=1234;";

            //mConnection = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com:3306/sql10635461","sql10635461","root");
            mConnection = DriverManager.getConnection("jdbc:mysql://149.100.155.114:3306/u266684669_palacecode","u266684669_matheus","Dteles100!");

            Log.i(TAG, "Connection successful");  // realizada a conexão com sucesso

        } catch (ClassNotFoundException mException) {
            String mMessage = "ClassNotFoundException: " + mException.getMessage();
            Toast.makeText(mContext, mMessage , Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
        } catch (SQLException mException) {
            String mMessage = "SQLException: " + mException.getMessage();
            Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
        } catch (Exception mException) {
            String mMessage = "Exception: " + mException.getMessage();
            Toast.makeText(mContext, mMessage , Toast.LENGTH_LONG).show();
            Log.e(TAG, mMessage);
            mException.printStackTrace();
        }

        return mConnection;
    }
}
