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

import java.util.ArrayList;

public class EjerciciosRutinasAdaptador extends RecyclerView.Adapter<EjerciciosRutinasAdaptador.EjerciciosRutinasViewHolder> {
    private TablaDiaRutinaUsuario diaRutinaUsuario;
    private Context context;
    private RecyclerView recyclerView;
    Animation animation;
    boolean visible;

    public EjerciciosRutinasAdaptador(Context context, TablaDiaRutinaUsuario diaRutinaUsuario, RecyclerView recyclerView) {
        this.context = context;
        this.diaRutinaUsuario = diaRutinaUsuario;
        this.diaRutinaUsuario.ordenarSemana();
        this.recyclerView = recyclerView;
        visible = false;
    }

    @NonNull
    @Override
    public EjerciciosRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicios_rutinas, parent, false);

        return new EjerciciosRutinasViewHolder(view);
    }

    public ArrayList<TablaEjercicioRutinaUsuario> obtenerEjerciciosActualizados() {
        ArrayList<TablaEjercicioRutinaUsuario> ejerciciosActualizados = new ArrayList<TablaEjercicioRutinaUsuario>();

        for (int i = 0; i < diaRutinaUsuario.getEjercicios().size(); i++) {
            TablaEjercicioRutinaUsuario ejercicioOriginal = diaRutinaUsuario.getEjercicios().get(i);

            EjerciciosRutinasViewHolder holder = (EjerciciosRutinasViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            if (holder != null) {
                TablaEjercicioRutinaUsuario ejercicioActualizado = new TablaEjercicioRutinaUsuario();
                ejercicioActualizado.setMusculos(ejercicioOriginal.getMusculos());
                ejercicioActualizado.setNombre(ejercicioOriginal.getNombre());
                ejercicioActualizado.setPosicion(ejercicioOriginal.getPosicion());

                ejercicioActualizado.setRepeticiones(Integer.parseInt(holder.repeticiones.getText().toString()));
                ejercicioActualizado.setSeries(Integer.parseInt(holder.series.getText().toString()));

                int segundos = Integer.parseInt(holder.minutos.getText().toString()) * 60 + Integer.parseInt(holder.segundos.getText().toString());
                ejercicioActualizado.setTiempo_descanso(segundos);

                ejerciciosActualizados.add(ejercicioActualizado);
            }
        }

        return ejerciciosActualizados;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EjerciciosRutinasViewHolder holder, @SuppressLint("RecyclerView") int position) {
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


        int posicion = ejercicioRutinaUsuario.getPosicion();
        if(posicion == 0){
            holder.subirIB.setImageResource(R.drawable.flecha_gris);
        }else{
            holder.subirIB.setImageResource(R.drawable.flecha_verde);
            holder.subirIB.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    TablaEjercicioRutinaUsuario ejercicioAnterior = diaRutinaUsuario.getEjercicios().get(posicion - 1);

                    ejercicioRutinaUsuario.setPosicion(posicion - 1);
                    ejercicioAnterior.setPosicion(posicion);
                    diaRutinaUsuario.getEjercicios().set(posicion - 1, ejercicioRutinaUsuario);
                    diaRutinaUsuario.getEjercicios().set(posicion, ejercicioAnterior);
                    notifyItemMoved(posicion, posicion - 1);
                    notifyDataSetChanged();
                }
            });

        }
        if (posicion+1 == diaRutinaUsuario.getEjercicios().size()) {
            holder.bajarIB.setImageResource(R.drawable.flecha_gris);
        }else{
            holder.bajarIB.setImageResource(R.drawable.flecha_roja);
            holder.bajarIB.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    TablaEjercicioRutinaUsuario ejercicioSiguiente = diaRutinaUsuario.getEjercicios().get(posicion + 1);

                    ejercicioRutinaUsuario.setPosicion(posicion + 1);
                    ejercicioSiguiente.setPosicion(posicion);
                    diaRutinaUsuario.getEjercicios().set(posicion + 1, ejercicioRutinaUsuario);
                    diaRutinaUsuario.getEjercicios().set(posicion, ejercicioSiguiente);

                    notifyItemMoved(posicion, posicion + 1);
                    notifyDataSetChanged();
                }
            });
        }

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                diaRutinaUsuario.getEjercicios().remove(position);

                for (int i = position; i < diaRutinaUsuario.getEjercicios().size(); i++) {
                    TablaEjercicioRutinaUsuario ejercicio = diaRutinaUsuario.getEjercicios().get(i);
                    ejercicio.setPosicion(i);
                }

                notifyDataSetChanged();
            }
        });

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
        Button eliminar;

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
            series = itemView.findViewById(R.id.series_bt);
            repeticiones = itemView.findViewById(R.id.repeticiones);

            subirIB = itemView.findViewById(R.id.subir);
            bajarIB = itemView.findViewById(R.id.bajar);

            eliminar = itemView.findViewById(R.id.eliminar);
        }
    }
}
