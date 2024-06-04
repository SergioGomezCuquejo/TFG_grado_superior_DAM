package com.example.yim.controlador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.ColoresMusculoUsuario;
import com.example.yim.modelo.tablas.TablaEjercicioRutinaUsuario;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.vista.EjerciciosRutinas;

import java.util.ArrayList;
import java.util.HashMap;


public class PopupAgregarEjercicioPorDefectoAdaptador extends RecyclerView.Adapter<PopupAgregarEjercicioPorDefectoAdaptador.PopupAgregarEjercicioPorDefectoAdaptadorViewHolder> {

    private ArrayList<TablaEjercicioPorDefecto> ejercicios;
    private TablaRutinaUsuario rutinaUsuario;
    private HashMap<String, ColoresMusculoUsuario> musculosSemana;
    private int dia;
    private Context context;

    public PopupAgregarEjercicioPorDefectoAdaptador(Context context, ArrayList<TablaEjercicioPorDefecto> ejercicios, TablaRutinaUsuario rutinaUsuario, int dia, HashMap<String, ColoresMusculoUsuario> musculosSemana) {
        this.context = context;
        this.ejercicios = ejercicios;
        this.rutinaUsuario = rutinaUsuario;
        this.dia = dia;
        this.musculosSemana = musculosSemana;
    }

    @NonNull
    @Override
    public PopupAgregarEjercicioPorDefectoAdaptadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup_agregar_ejercicio, parent, false);

        return new PopupAgregarEjercicioPorDefectoAdaptadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopupAgregarEjercicioPorDefectoAdaptadorViewHolder holder, int position) {
        TablaEjercicioPorDefecto ejercicio = ejercicios.get(position);
        String musculos = "";

        holder.ejercicioRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( rutinaUsuario.getSemana().get(dia).getEjercicios() == null){
                    rutinaUsuario.getSemana().get(dia).setEjercicios(new ArrayList<>());
                }
                rutinaUsuario.getSemana().get(dia).getEjercicios().add(new TablaEjercicioRutinaUsuario(ejercicio, ejercicios.size()));

                CambiarActivity.cambiar(context, EjerciciosRutinas.class, rutinaUsuario, dia, musculosSemana);
            }
        });

        if(ejercicio.getImagen() != null){
            holder.imagen.setImageResource(context.getResources().getIdentifier(ejercicio.getImagen(), "drawable", context.getPackageName()));
        }

        holder.nombreTV.setText(ejercicio.getNombre());

        for(String musculo : ejercicio.getMusculos()){
            musculos += musculo + ", ";
        }
        musculos = musculos.substring(0, musculos.length() - 2);
        holder.musculosTV.setText(musculos);

    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public static class PopupAgregarEjercicioPorDefectoAdaptadorViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ejercicioRL;
        ImageView imagen;
        TextView nombreTV, musculosTV;

        public PopupAgregarEjercicioPorDefectoAdaptadorViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicioRL = itemView.findViewById(R.id.ejercicio_rl);
            imagen = itemView.findViewById(R.id.imagen);
            nombreTV = itemView.findViewById(R.id.nombre_tv);
            musculosTV = itemView.findViewById(R.id.musculos_tv);
        }
    }
}
