package com.example.apprestaurant.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apprestaurant.Domain.Helper.ChangeNumberItemsListener;
import com.example.apprestaurant.Domain.Helper.ManagmentCart;
import com.example.apprestaurant.R;
import com.example.apprestaurant.conexao.Util;
import com.example.apprestaurant.model.CardapioModel;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<CardapioModel> listFoodSelected;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;
    private String currencySymbol;

    public CartListAdapter(ArrayList<CardapioModel> listFoodSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listFoodSelected = listFoodSelected;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;

        // Obter o símbolo monetário local
        Locale userLocale = context.getResources().getConfiguration().locale;
        Currency currency = Currency.getInstance(userLocale);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(userLocale);
        symbols.setCurrency(currency);
        currencySymbol = symbols.getCurrencySymbol();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardapioModel food = listFoodSelected.get(position);

        holder.title.setText(food.getNome());

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        holder.feeEachItem.setText(currencySymbol + decimalFormat.format(food.getPreco()));
        holder.totalEachItem.setText(currencySymbol + decimalFormat.format(food.getNumeroNoCarrinho() * food.getPreco()));

        holder.num.setText(String.valueOf(food.getNumeroNoCarrinho()));

        if (food.getImagem().length > 0) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] byteArray = food.getImagem();
            Bitmap compressedBitmap = Util.converterByteToBipmap(byteArray);
            holder.pic.setImageBitmap(compressedBitmap);
        }

        holder.plusItem.setOnClickListener(view -> managmentCart.plusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.minusItem.setOnClickListener(view -> managmentCart.minusNumberFood(listFoodSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    public int getItemQuantity(int position) {
        return listFoodSelected.get(position).getNumeroNoCarrinho();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTxt1);
            pic = itemView.findViewById(R.id.pic_image);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn1);
            minusItem = itemView.findViewById(R.id.minusCartBtn1);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberCartBtn1);
        }
    }
}
