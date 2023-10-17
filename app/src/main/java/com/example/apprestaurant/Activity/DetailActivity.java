package com.example.apprestaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apprestaurant.Domain.Helper.ManagmentCart;
import com.example.apprestaurant.R;
import com.example.apprestaurant.conexao.Util;
import com.example.apprestaurant.model.CardapioModel;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private Button addToCartBtn;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
    private ImageView picFood;
    private CardapioModel object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;
    private DecimalFormat decimalFormat;
    private String currencySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Locale userLocale = getResources().getConfiguration().locale;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(userLocale);
        currencySymbol = symbols.getCurrencySymbol();
        decimalFormat = new DecimalFormat("0.00", symbols);

        managmentCart = new ManagmentCart(DetailActivity.this);
        initView();
        getBundle();

        ImageView backToMainActivity = findViewById(R.id.backToMainActivity);
        backToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getBundle() {
        object = (CardapioModel) getIntent().getSerializableExtra("object");

        if (object.getImagem().length > 0) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] byteArray = object.getImagem();
            Bitmap compressedBitmap = Util.converterByteToBipmap(byteArray);
            picFood.setImageBitmap(compressedBitmap);
        }

        titleTxt.setText(object.getNome());
        feeTxt.setText(currencySymbol + decimalFormat.format(object.getPreco()));
        descriptionTxt.setText(object.getDescricao());
        numberOrderTxt.setText("" + numberOrder);

        String addToCartText = getString(R.string.add_to_cart);
        String formattedText = String.format("%s - %s%s", addToCartText, currencySymbol, decimalFormat.format(numberOrder * object.getPreco()));
        addToCartBtn.setText(formattedText);

        plusBtn.setOnClickListener(view -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText(String.valueOf(numberOrder));

            String formattedTextPlus = String.format("%s - %s%s", addToCartText, currencySymbol, decimalFormat.format(numberOrder * object.getPreco()));
            addToCartBtn.setText(formattedTextPlus);
        });

        minusBtn.setOnClickListener(view -> {
            numberOrder = numberOrder - 1;
            if (numberOrder < 1) {
                numberOrder = 1;
            }
            numberOrderTxt.setText(String.valueOf(numberOrder));

            String formattedTextMinus = String.format("%s - %s%s", addToCartText, currencySymbol, decimalFormat.format(numberOrder * object.getPreco()));
            addToCartBtn.setText(formattedTextMinus);
        });

        addToCartBtn.setOnClickListener(view -> {
            object.setNumeroNoCarrinho(numberOrder);
            managmentCart.insertFood(object);
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCardBtn);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.MinusCardTxt);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        picFood = findViewById(R.id.foodPic);
    }
}
