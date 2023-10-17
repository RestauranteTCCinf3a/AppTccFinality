package com.example.apprestaurant.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurant.Adapter.CartListAdapter;
import com.example.apprestaurant.Domain.Helper.ChangeNumberItemsListener;
import com.example.apprestaurant.Domain.Helper.ManagmentCart;
import com.example.apprestaurant.R;
import com.example.apprestaurant.controller.CardapioController;
import com.example.apprestaurant.controller.MesaController;
import com.example.apprestaurant.controller.PedidoController;
import com.example.apprestaurant.model.CardapioModel;
import com.example.apprestaurant.model.MesaModel;
import com.example.apprestaurant.model.PedidoModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private CartListAdapter adapter;
    private RecyclerView recyclerViewList;
    private ManagmentCart managmentCart;
    private TextView totalTxt, emptyTxt;
    private ScrollView scrollView;
    private ImageView backBtn;
    private List<MesaModel> listaMesas;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView descriptionTxt;

    private MesaController mesaController;
    private SharedPreferences mSharedPreferences;
    private int funcionarioId;

    private DecimalFormat decimalFormat;
    private String currencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mesaController = new MesaController();

        Locale userLocale = getResources().getConfiguration().locale;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(userLocale);
        currencySymbol = symbols.getCurrencySymbol();
        decimalFormat = new DecimalFormat("0.00", symbols);

        managmentCart = new ManagmentCart(getApplicationContext());
        initView();
        initList();
        setVariable();

        listaMesas = mesaController.getMesasAtivas(this);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<Integer> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getNumerosMesasCadastradas());
        autoCompleteTextView.setAdapter(autoCompleteAdapter);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMesaSelectionDialog(autoCompleteTextView);
            }
        });

        descriptionTxt = findViewById(R.id.descriptionTxt);

        Button orderButton = findViewById(R.id.button_order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    String message_table_number = getString(R.string.select_a_table_number);
                    Toast.makeText(CartActivity.this, message_table_number, Toast.LENGTH_SHORT).show();
                } else {
                    enviarPedidos();
                }
            }
        });
    }

    private void initView() {
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.recycler3);
        scrollView = findViewById(R.id.scrollView3);
        backBtn = findViewById(R.id.backBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managmentCart.getListCard(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managmentCart.getListCard().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

        calculateCart();
    }

    private void setVariable() {
        backBtn.setOnClickListener(view -> finish());
    }

    private void calculateCart() {
        double totalFee = managmentCart.getTotalFee();
        totalTxt.setText(currencySymbol + decimalFormat.format(totalFee));
    }

    private void showMesaSelectionDialog(final AutoCompleteTextView autoCompleteTextView) {
        String[] mesasArray = new String[listaMesas.size()];
        for (int i = 0; i < listaMesas.size(); i++) {
            mesasArray[i] = String.valueOf(listaMesas.get(i).getNumeromesa());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String selectTableTitle = getString(R.string.select_a_table);
        builder.setTitle(selectTableTitle);
        builder.setItems(mesasArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                autoCompleteTextView.setText(mesasArray[which]);
                String tableSelectedMessage = getString(R.string.table_selected_message, mesasArray[which]);
                Toast.makeText(CartActivity.this, tableSelectedMessage, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private List<Integer> getNumerosMesasCadastradas() {
        List<Integer> numerosMesas = new ArrayList<>();
        for (MesaModel mesa : listaMesas) {
            numerosMesas.add(mesa.getNumeromesa());
        }
        return numerosMesas;
    }

    private void enviarPedidos() {
        String numeroMesaStr = autoCompleteTextView.getText().toString();
        String descricao = descriptionTxt.getText().toString();

        int mesaId = mesaController.getIdMesaPorNumero(getApplicationContext(), numeroMesaStr);

        if (mesaId == -1) {
            String invalid_table_number = getString(R.string.invalid_table_number);
            Toast.makeText(this, invalid_table_number, Toast.LENGTH_SHORT).show();
            return;
        }

        mSharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        funcionarioId = mSharedPreferences.getInt("idFunc", -1);

        if (funcionarioId == -1) {
            String error_id_func = getString(R.string.error_id_func);
            Toast.makeText(this, error_id_func, Toast.LENGTH_SHORT).show();
            return;
        }

        PedidoController pedidoController = new PedidoController(getApplicationContext());
        ArrayList<CardapioModel> listaItensCarrinho = managmentCart.getListCard();

        for (int i = 0; i < listaItensCarrinho.size(); i++) {
            CardapioModel item = listaItensCarrinho.get(i);
            CardapioController cardapioController = new CardapioController();
            int idCardapio = cardapioController.getIdCardapioPorNomeItem(getApplicationContext(), item.getNome());

            double valorTotalItem = item.getPreco() * item.getNumeroNoCarrinho();

            int quantidade = adapter.getItemQuantity(i);

            String situacao = "ATIVO";

            PedidoModel pedido = new PedidoModel(0, mesaId, idCardapio, valorTotalItem, descricao, quantidade, situacao, funcionarioId);

            boolean pedidoAdicionado = pedidoController.adicionarPedido(pedido);

            if (!pedidoAdicionado) {
                String error_order_item = getString(R.string.error_order_item);
                Toast.makeText(this, error_order_item + item.getNome(), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        managmentCart.clearCart();
        adapter.notifyDataSetChanged();
        calculateCart();
        String order_success = getString(R.string.order_success);
        Toast.makeText(this,order_success, Toast.LENGTH_SHORT).show();
    }
}
