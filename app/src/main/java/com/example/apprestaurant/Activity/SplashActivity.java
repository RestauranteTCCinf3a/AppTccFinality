package com.example.apprestaurant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apprestaurant.R;

public class SplashActivity extends AppCompatActivity {

    // Define o tempo de exibição da tela de splash em milissegundos
    private static final long SPLASH_DISPLAY_LENGTH = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Usando um Handler para adicionar um atraso antes de iniciar a próxima atividade
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cria um Intent para iniciar a próxima atividade (por exemplo, a tela principal)
                Intent mainIntent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(mainIntent);
                finish(); // Finaliza a activity atual (a tela de splash) para não voltar a ela ao pressionar o botão "Voltar"
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}