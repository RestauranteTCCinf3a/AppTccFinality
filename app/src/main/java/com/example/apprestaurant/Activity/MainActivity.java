package com.example.apprestaurant.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.Adapter.ComboListAdapter;
import com.example.apprestaurant.R;
import com.example.apprestaurant.controller.CardapioController;
import com.example.apprestaurant.controller.FuncionarioController;
import com.example.apprestaurant.model.CardapioModel;
import com.example.apprestaurant.model.FuncionarioModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    private ImageView imageView_logout;
    private TextView textViewNome;
    private TextView textViewOcupacao;
    private ComboListAdapter comboListAdapter;
    int ID_FUNC = 1;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerview();
        initPerfilUsuario();
        bottomNavigation();

        mSharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        showNomeOcupacao();

        imageView_logout = findViewById(R.id.imageView_logout);
        imageView_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        comboListAdapter = (ComboListAdapter) adapterFoodList;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                comboListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void logoutUser() {
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("logged", "false");
        mEditor.remove("nome");
        mEditor.remove("ocupacao");
        mEditor.apply();

        String logout_success = getString(R.string.logout_success);
        Toast.makeText(this, logout_success, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
        finish();
    }

    private void showNomeOcupacao() {
        textViewNome.setText(mSharedPreferences.getString("nome", ""));
        textViewOcupacao.setText(mSharedPreferences.getString("ocupacao", ""));
    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementar a ação do botão Home aqui (se necessário)
            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void initRecyclerview() {
        CardapioController cardapioController = new CardapioController();
        List<CardapioModel> combosAtivos = cardapioController.getCombosAtivosFromDatabase(this);

        recyclerViewFood = findViewById(R.id.view1);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(this));

        adapterFoodList = new ComboListAdapter(combosAtivos);
        recyclerViewFood.setAdapter(adapterFoodList);
    }

    private void initPerfilUsuario() {
        FuncionarioController funcionarioController = new FuncionarioController();
        FuncionarioModel funcionario = funcionarioController.getFuncionarioById(ID_FUNC, this);

        textViewNome = findViewById(R.id.textView_nome);
        textViewOcupacao = findViewById(R.id.textView_ocupacao);

        if (funcionario != null) {
            textViewNome.setText(funcionario.getNome());
            textViewOcupacao.setText(funcionario.getOcupacao());
        } else {
            String employee_not_found = getString(R.string.employee_not_found);
            textViewNome.setText(employee_not_found);
            textViewOcupacao.setText("");
        }
    }
}
