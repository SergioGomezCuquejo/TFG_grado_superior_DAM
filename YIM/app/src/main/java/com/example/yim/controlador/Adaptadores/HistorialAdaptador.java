package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaSerie;

import java.util.ArrayList;


public class HistorialAdaptador extends RecyclerView.Adapter<HistorialAdaptador.HistorialViewHolder> {
    private final ArrayList<String> dias;
    private final ArrayList<TablaSerie> series;
    private final Context context;
    private int posicionDia;

    public HistorialAdaptador(Context context, ArrayList<String> dias, ArrayList<TablaSerie> series) {
        this.dias = dias;
        this.series = series;
        this.context = context;
        posicionDia = 0;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false);

        return new HistorialViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        TablaSerie serie = series.get(position);
        String coste = serie.getCoste();
        int color;

        if(serie.getSerie() == 1){
            holder.diaTV.setVisibility(View.VISIBLE);
            holder.diaTV.setText("Día " + dias.get(posicionDia));
            posicionDia++;
        }else {

            holder.diaTV.setVisibility(View.GONE);
        }

        holder.serieTV.setText("Serie " + serie.getSerie() + ".");
        holder.peso_repeticionesTV.setText(serie.getPeso() + "kg x " + serie.getRepeticiones() + " reps");

        holder.costeTV.setText(coste);
        if (coste.equals("Subir")){
            color = R.color.verde_clarito;
        } else if (coste.equals("Mantener")){
            color = R.color.naranja;
        } else {
            color = R.color.rojo_clarito;
        }
        holder.costeTV.setTextColor(ContextCompat.getColor(context, color));

    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView diaTV, serieTV, peso_repeticionesTV, costeTV;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            diaTV = itemView.findViewById(R.id.dia_tv);
            serieTV = itemView.findViewById(R.id.serie_tv);
            peso_repeticionesTV = itemView.findViewById(R.id.peso_repeticiones_tv);
            costeTV = itemView.findViewById(R.id.coste_tv);
        }
    }
}
