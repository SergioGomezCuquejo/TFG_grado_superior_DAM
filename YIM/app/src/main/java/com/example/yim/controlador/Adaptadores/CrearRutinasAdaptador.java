package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.vista.EjerciciosRutinas;

import java.util.ArrayList;
import java.util.HashMap;


public class CrearRutinasAdaptador extends RecyclerView.Adapter<CrearRutinasAdaptador.CrearRutinasViewHolder> {
    private TablaRutinaUsuario rutinasUsuario;
    private Context context;
    HashMap<String, ColoresMusculoUsuario> musculosHM;

    public CrearRutinasAdaptador(Context context, TablaRutinaUsuario rutinasUsuario, HashMap<String, ColoresMusculoUsuario> musculosHM) {
        this.context = context;
        this.rutinasUsuario = rutinasUsuario;
        this.musculosHM = musculosHM;
        this.rutinasUsuario.ordenarSemana();
    }

    @NonNull
    @Override
    public CrearRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crear_rutinas, parent, false);

        return new CrearRutinasViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull CrearRutinasViewHolder holder, int position) {
        String musculo, fondo, fuente;
        TablaDiaRutinaUsuario diaRutinaUsuario = rutinasUsuario.getSemana().get(position);
        int dia = position;

        holder.dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, EjerciciosRutinas.class, rutinasUsuario, dia, musculosHM);
            }
        });

        holder.numeroDia.setText(String.valueOf(diaRutinaUsuario.getDia()));

        if(diaRutinaUsuario.getMusculos() != null){
            ArrayList<String> musculosUsuario = diaRutinaUsuario.getMusculos();
            if (musculosUsuario.size() >= 2){
                holder.musculoIzquierda.setBackgroundResource(R.drawable._style2_borde_blanco_izquierda);
                holder.musculoCentro.setVisibility(View.VISIBLE);
                if(musculosUsuario.size() == 3){
                    holder.musculoCentro.setBackgroundResource(R.drawable._style_borde_blanco_0);
                    holder.musculoDerecha.setVisibility(View.VISIBLE);
                }
            }
            holder.musculoIzquierda.setTextSize(20);
            holder.musculoCentro.setTextSize(17);
            for (int i = 0; i < musculosUsuario.size(); i++){
                musculo = musculosUsuario.get(i);
                if(musculosHM.containsKey(musculo)){
                    fondo = musculosHM.get(musculo).getColor_fondo();
                    fuente = musculosHM.get(musculo).getColor_fuente();
                }else{
                    fondo = "#FFFFFFFF";
                    fuente = "#FF000000";
                }

                if(musculo.equals("Descanso")){
                    musculo = musculo.toUpperCase();
                }
                if(i == 0){
                    holder.musculoIzquierda.setTextSize(20);
                    holder.musculoIzquierda.setText(musculo);
                    holder.musculoIzquierda.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                    holder.musculoIzquierda.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
                } else if (i == 1) {
                    holder.musculoIzquierda.setTextSize(17);
                    holder.musculoCentro.setText(musculo);
                    holder.musculoCentro.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                    holder.musculoCentro.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
                }else {
                    holder.musculoIzquierda.setTextSize(15);
                    holder.musculoCentro.setTextSize(15);
                    holder.musculoDerecha.setText(musculo);
                    holder.musculoDerecha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fondo)));
                    holder.musculoDerecha.setTextColor(ColorStateList.valueOf(Color.parseColor(fuente)));
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return rutinasUsuario.getSemana().size();
    }

    public static class CrearRutinasViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dia;
        TextView numeroDia, musculoIzquierda, musculoCentro, musculoDerecha;

        public CrearRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            dia = itemView.findViewById(R.id.dia);
            numeroDia = itemView.findViewById(R.id.numero_dia);
            musculoIzquierda = itemView.findViewById(R.id.musculo_izquierda);
            musculoCentro = itemView.findViewById(R.id.musculo_centro);
            musculoDerecha = itemView.findViewById(R.id.musculo_derecha);
        }
    }
}
