package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaDiaRutinaUsuario;
import com.example.yim.modelo.tablas.TablaEjercicioRutinaUsuario;
import com.google.android.material.imageview.ShapeableImageView;

public class EjerciciosRutinasAdaptador extends RecyclerView.Adapter<EjerciciosRutinasAdaptador.EjerciciosRutinasViewHolder> {
    private TablaDiaRutinaUsuario diaRutinaUsuario;
    private Context context;
    Animation animation;
    boolean visible;
    public EjerciciosRutinasAdaptador(Context context, TablaDiaRutinaUsuario diaRutinaUsuario) {
        this.context = context;
        this.diaRutinaUsuario = diaRutinaUsuario;
        this.diaRutinaUsuario.ordenarSemana();
        visible = false;
    }

    @NonNull
    @Override
    public EjerciciosRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicios_rutinas, parent, false);

        return new EjerciciosRutinasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EjerciciosRutinasViewHolder holder, int position) {
        TablaEjercicioRutinaUsuario ejercicioRutinaUsuario = diaRutinaUsuario.getEjercicios().get(position);
        String musculos = "";

        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible) {
                    animation = AnimationUtils.loadAnimation(context, R.anim.deslizar_abajo);
                    holder.editar.setVisibility(View.GONE);
                    holder.opciones.setVisibility(View.GONE);
                } else {
                    animation = AnimationUtils.loadAnimation(context, R.anim.deslizar_arriba);
                    holder.editar.setVisibility(View.VISIBLE);
                    holder.opciones.setVisibility(View.VISIBLE);
                }
                holder.editar.startAnimation(animation);
                holder.opciones.startAnimation(animation);
                visible = !visible;
            }
        });

        holder.imagen.setImageResource(R.drawable.curl_de_biceps_hombre);
        holder.nombre.setText(ejercicioRutinaUsuario.getNombre());

        for(String musculo : ejercicioRutinaUsuario.getMusculos()){
            musculos += musculo + ", ";
        }
        musculos = musculos.substring(0, musculos.length() - 2);
        holder.musculos.setText(musculos);

        holder.minutos.setText(String.valueOf((int) ejercicioRutinaUsuario.getTiempo_descanso() / 60));
        holder.segundos.setText(String.valueOf(ejercicioRutinaUsuario.getTiempo_descanso() % 60));


        holder.series.setText(String.valueOf(ejercicioRutinaUsuario.getSeries()));
        holder.repeticiones.setText(String.valueOf(ejercicioRutinaUsuario.getRepeticiones()));

        if(position == 0){
            holder.subirIB.setImageResource(R.drawable.flecha_gris);
        }
        if (position+1 == diaRutinaUsuario.getEjercicios().size()) {
            holder.bajarIB.setImageResource(R.drawable.flecha_gris);
        }

    }

    @Override
    public int getItemCount() {
        return diaRutinaUsuario.getEjercicios().size();
    }

    public static class EjerciciosRutinasViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ejercicio;
        ShapeableImageView imagen;
        TextView nombre, musculos;
        LinearLayout editar, opciones;
        EditText minutos, segundos, series, repeticiones;
        ImageButton subirIB, bajarIB;
        Button borrar;

        public EjerciciosRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);

            imagen = itemView.findViewById(R.id.imagen);

            nombre = itemView.findViewById(R.id.nombre);
            musculos = itemView.findViewById(R.id.musculos);

            editar = itemView.findViewById(R.id.editar);
            opciones = itemView.findViewById(R.id.opciones);

            minutos = itemView.findViewById(R.id.minutos);
            segundos = itemView.findViewById(R.id.segundos);
            series = itemView.findViewById(R.id.series);
            repeticiones = itemView.findViewById(R.id.repeticiones);

            subirIB = itemView.findViewById(R.id.subir);
            bajarIB = itemView.findViewById(R.id.bajar);

            borrar = itemView.findViewById(R.id.borrar);
        }
    }
}
