package com.example.apprestaurant.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurant.Activity.DetailActivity;
import com.example.apprestaurant.R;
import com.example.apprestaurant.conexao.Util;
import com.example.apprestaurant.model.CardapioModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ComboListAdapter extends RecyclerView.Adapter<ComboListAdapter.ComboViewHolder> implements Filterable {

    private List<CardapioModel> combos;
    private List<CardapioModel> combosFull; // Lista completa para recuperação após filtragem

    public ComboListAdapter(List<CardapioModel> combos) {
        this.combos = combos;
        this.combosFull = new ArrayList<>(combos);
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food_list, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        CardapioModel combo = combos.get(position);
        // Configurar as views dentro do holder com os dados do combo
        holder.nomeComboTextView.setText(combo.getNome());

        if (combo.getImagem().length > 0) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] byteArray = combo.getImagem();
            Bitmap compressedBitmap = Util.converterByteToBipmap(byteArray);
            holder.pic_teste.setImageBitmap(compressedBitmap);
        }

        holder.pic_teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                mIntent.putExtra("object", combos.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder {
        TextView nomeComboTextView;
        ImageView pic_teste;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeComboTextView = itemView.findViewById(R.id.title_teste);
            pic_teste = itemView.findViewById(R.id.pic_teste);
        }
    }

    @Override
    public Filter getFilter() {
        return comboFilter;
    }

    private Filter comboFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CardapioModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(combosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CardapioModel combo : combosFull) {
                    if (combo.getNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(combo);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            combos.clear();
            combos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}