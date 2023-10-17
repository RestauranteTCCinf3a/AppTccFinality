package com.example.apprestaurant.Domain.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.apprestaurant.Activity.DetailActivity;
import com.example.apprestaurant.Domain.FoodDomain;
import com.example.apprestaurant.R;
import com.example.apprestaurant.model.CardapioModel;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

//    public ManagmentCart(DetailActivity context) {
//        this.context = context;
//        this.tinyDB = new TinyDB(context);
//    }


    public ManagmentCart(Context context) {
        this.context = context;
        tinyDB = new TinyDB(context);
    }

//    public void insertFood(CardapioModel item) {
//        ArrayList<CardapioModel> listfood = getListCard();
//        boolean existAlready=false;
//        int n=0;
//        for(int k = 0; k<listfood.size(); k++){
//            if(listfood.get(k).getNome().equals(item.getNome())){
//                existAlready=true;
//                n=k;
//                break;
//            }
//        }

    public void insertFood(CardapioModel item) {
        ArrayList<CardapioModel> listfood = getListCard();
        boolean existAlready=false;
        int n=0;
        for (int k = 0; k < listfood.size(); k++) {
            CardapioModel food = listfood.get(k);
            if (food != null && food.getNome() != null && food.getNome().equals(item.getNome())) {
                existAlready = true;
                n = k;
                break;
            }
        }

        if(existAlready){
            listfood.get(n).setNumeroNoCarrinho(item.getNumeroNoCarrinho());
        } else {
            listfood.add(item);
        }

        tinyDB.putListObject("CartList",listfood);
        String added_to_cart = context.getString(R.string.added_to_cart);
        Toast.makeText(context, added_to_cart, Toast.LENGTH_SHORT).show();


    }
    public ArrayList<CardapioModel> getListCard(){
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<CardapioModel> listfood,int position, ChangeNumberItemsListener changeNumberItemsListener){

        if(listfood.get(position).getNumeroNoCarrinho() == 1){
            listfood.remove(position);
        } else {
            listfood.get(position).setNumeroNoCarrinho(listfood.get(position).getNumeroNoCarrinho() - 1);
        }
        tinyDB.putListObject("CartList",listfood);
        changeNumberItemsListener.changed();

    }

    public void plusNumberFood(ArrayList<CardapioModel> listfood,int position, ChangeNumberItemsListener changeNumberItemsListener){
        listfood.get(position).setNumeroNoCarrinho(listfood.get(position).getNumeroNoCarrinho() + 1);
        tinyDB.putListObject("CartList",listfood);
        changeNumberItemsListener.changed();

    }

    public Double getTotalFee(){
        ArrayList<CardapioModel> listfood2=getListCard();
        double fee=0;
        for (int i = 0; i < listfood2.size();i++){
            fee = fee+(listfood2.get(i).getPreco() * listfood2.get(i).getNumeroNoCarrinho());
        }
        return fee;
    }

    // Método para limpar o carrinho
    public void clearCart() {
        tinyDB.remove("CartList");
        String clean_cart = context.getString(R.string.clean_cart);
        Toast.makeText(context, clean_cart, Toast.LENGTH_SHORT).show();
    }
}

