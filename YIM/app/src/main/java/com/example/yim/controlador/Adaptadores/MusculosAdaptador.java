package com.example.yim.controlador.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaMusculosUsuario;
import com.example.yim.vista.vista.PopupMusculos;

import java.util.ArrayList;


public class MusculosAdaptador extends RecyclerView.Adapter<MusculosAdaptador.MusculosViewHolder> {

    private ArrayList<TablaMusculosUsuario> musculos;

    private Context context;

    public MusculosAdaptador(Context context, ArrayList<TablaMusculosUsuario> musculos) {
        this.context = context;
        this.musculos = musculos;
    }

    @NonNull
    @Override
    public MusculosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_musculos, parent, false);

        return new MusculosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusculosViewHolder holder, int position) {
        TablaMusculosUsuario musculosUsuario = musculos.get(position);

        holder.musculo.setText(musculosUsuario.getNombre());
        holder.musculo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculosUsuario.getColor_fondo())));
        holder.musculo.setTextColor(Color.parseColor(musculosUsuario.getColor_fuente()));

        holder.musculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopupMusculos.class);
                intent.putExtra("musculoUsuario", musculosUsuario.getNombre());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musculos.size();
    }

    public static class MusculosViewHolder extends RecyclerView.ViewHolder {
        TextView musculo;

        public MusculosViewHolder(@NonNull View itemView) {
            super(itemView);
            musculo = itemView.findViewById(R.id.musculo);
        }
    }
}
