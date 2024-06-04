package com.example.yim.controlador.Adaptadores;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;

import java.util.ArrayList;


public class MusculosAdaptador extends RecyclerView.Adapter<MusculosAdaptador.MusculosViewHolder> {

    private ArrayList<TablaMusculoUsuario> musculos;

    private Context context;

    public MusculosAdaptador(Context context, ArrayList<TablaMusculoUsuario> musculos) {
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
        TablaMusculoUsuario musculoUsuario = musculos.get(position);

        holder.musculo.setText(musculoUsuario.getNombre());
        holder.musculo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(musculoUsuario.getColor_fondo())));
        holder.musculo.setTextColor(Color.parseColor(musculoUsuario.getColor_fuente()));

        holder.musculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, musculoUsuario);
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
