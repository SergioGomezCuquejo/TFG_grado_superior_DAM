package com.example.yim.controlador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;

import java.util.ArrayList;


public class ApartadoPopupVerEjerciciosAdaptador extends RecyclerView.Adapter<ApartadoPopupVerEjerciciosAdaptador.ApartadoPopupVerEjerciciosViewHolder> {

    private ArrayList<String> textos;

    private Context context;

    public ApartadoPopupVerEjerciciosAdaptador(Context context, ArrayList<String> textos) {
        this.context = context;
        this.textos = textos;
    }

    @NonNull
    @Override
    public ApartadoPopupVerEjerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_apartados_popup_ver_ejercicios, parent, false);

        return new ApartadoPopupVerEjerciciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartadoPopupVerEjerciciosViewHolder holder, int position) {
        String texto = textos.get(position);

        holder.numeroTV.setText(String.valueOf(position+1));
        holder.textoTV.setText(texto);

    }

    @Override
    public int getItemCount() {
        return textos.size();
    }

    public static class ApartadoPopupVerEjerciciosViewHolder extends RecyclerView.ViewHolder {
        TextView numeroTV, textoTV;

        public ApartadoPopupVerEjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroTV = itemView.findViewById(R.id.numero_tv);
            textoTV = itemView.findViewById(R.id.texto_tv);
        }
    }
}
