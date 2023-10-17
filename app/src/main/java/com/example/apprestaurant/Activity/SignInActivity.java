package com.example.apprestaurant.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apprestaurant.Activity.MainActivity;
import com.example.apprestaurant.R;
import com.example.apprestaurant.controller.SignInController;
import com.example.apprestaurant.model.SignInModel;
import com.google.android.material.snackbar.Snackbar;

public class SignInActivity extends AppCompatActivity {

    private EditText mEditTextCredentials, mEditTextPassword;
    private Button mButtonSignIn;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mSharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        mEditTextCredentials = findViewById(R.id.inputEmail);
        mEditTextPassword = findViewById(R.id.inputPassword);
        mButtonSignIn = findViewById(R.id.buttonSignIn);

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextCredentials.getText().toString().trim();
                String senha = mEditTextPassword.getText().toString().trim();

                // Validar se os campos não estão vazios
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                    String enter_valid = getString(R.string.enter_valid);
                    Snackbar.make(mButtonSignIn, enter_valid, Snackbar.LENGTH_LONG).show();
                    return; // Sair da função para evitar a tentativa de login inválida
                }

                // Se os campos não estão vazios, continuar com a tentativa de login
                SignInController controller = new SignInController();
                SignInModel dadosRetornados = controller.login(new SignInModel(email, senha), getApplicationContext());

                // Verificar o retorno do login
                // Verifying the login response
                if (dadosRetornados != null && !dadosRetornados.getUsuario().equals(getString(R.string.data_not_found))) {
                    performNextActivity(dadosRetornados.getNome(), dadosRetornados.getOcupacao(), dadosRetornados.getIdFunc());
                } else {
                    Snackbar.make(mButtonSignIn, getString(R.string.incorrect_username_password), Snackbar.LENGTH_LONG).show();
                }

            }
        });

        verifyLogged();
    }

    private void verifyLogged() {
        if (mSharedPreferences.getString("logged", "false").equals("true")) {
            String be_welcome = getString(R.string.be_welcome);
            Toast.makeText(this, be_welcome, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish(); // Encerrar a atividade atual para que o usuário não possa voltar para a tela de login pressionando o botão "Voltar"
        }
    }

    private void performNextActivity(String nome, String ocupacao, int idFunc) {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("logged", "true");
        mEditor.putString("nome", nome);
        mEditor.putString("ocupacao", ocupacao);
        mEditor.putInt("idFunc", idFunc); // Armazena o ID do funcionário
        mEditor.apply();

        String be_welcome = getString(R.string.be_welcome);
        Toast.makeText(this, be_welcome, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish(); // Encerrar a atividade atual para que o usuário não possa voltar para a tela de login pressionando o botão "Voltar"
    }
}
