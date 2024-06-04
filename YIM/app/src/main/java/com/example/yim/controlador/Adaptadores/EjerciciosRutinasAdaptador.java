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
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
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
                ejercicioActualizado.setID(ejercicioOriginal.getID());

                ejercicioActualizado.setImagen(ejercicioOriginal.getImagen());

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
        String musculos, nombre;
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
        holder.imagenTexto.setVisibility(View.INVISIBLE);
        holder.imagen.setVisibility(View.VISIBLE);

        if(ejercicioRutinaUsuario.getNombre().startsWith("-")){
            if(ejercicioRutinaUsuario.getImagen() != null){
                Imagenes.mostrarImagen(context, ejercicioRutinaUsuario.getImagen(),  holder.imagen);
            }else{
                holder.imagenTexto.setVisibility(View.VISIBLE);
                holder.imagen.setVisibility(View.INVISIBLE);
                nombre = ejercicioRutinaUsuario.getNombre().toUpperCase();
                nombre = nombre.substring(1);
                if (nombre.length() >= 3) {
                    nombre = nombre.substring(0, 3);
                }
                holder.imagenTexto.setText(nombre);
            }
            holder.nombre.setText(ejercicioRutinaUsuario.getNombre().substring(1));
        }else{

            if(ejercicioRutinaUsuario.getImagen() != null){
                MostratToast.mostrarToast(context, ejercicioRutinaUsuario.getImagen());
                holder.imagen.setImageResource(context.getResources().getIdentifier(ejercicioRutinaUsuario.getImagen(), "drawable", context.getPackageName()));
            }
            holder.nombre.setText(ejercicioRutinaUsuario.getNombre());
        }

        musculos= "";
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
        }else{
            holder.subirIB.setImageResource(R.drawable.flecha_verde);
            holder.subirIB.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    TablaEjercicioRutinaUsuario ejercicioAnterior = diaRutinaUsuario.getEjercicios().get(position - 1);

                    ejercicioRutinaUsuario.setPosicion(position - 1);
                    ejercicioAnterior.setPosicion(position);
                    diaRutinaUsuario.getEjercicios().set(position - 1, ejercicioRutinaUsuario);
                    diaRutinaUsuario.getEjercicios().set(position, ejercicioAnterior);
                    notifyItemMoved(position, position - 1);
                    notifyDataSetChanged();
                }
            });

        }
        if (position+1 == diaRutinaUsuario.getEjercicios().size()) {
            holder.bajarIB.setImageResource(R.drawable.flecha_gris);
        }else{
            holder.bajarIB.setImageResource(R.drawable.flecha_roja);
            holder.bajarIB.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    TablaEjercicioRutinaUsuario ejercicioSiguiente = diaRutinaUsuario.getEjercicios().get(position + 1);

                    ejercicioRutinaUsuario.setPosicion(position + 1);
                    ejercicioSiguiente.setPosicion(position);
                    diaRutinaUsuario.getEjercicios().set(position + 1, ejercicioRutinaUsuario);
                    diaRutinaUsuario.getEjercicios().set(position, ejercicioSiguiente);

                    notifyItemMoved(position, position + 1);
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
        TextView nombre, musculos, imagenTexto;
        LinearLayout editar, opciones;
        EditText minutos, segundos, series, repeticiones;
        ImageButton subirIB, bajarIB;
        Button eliminar;

        public EjerciciosRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.ejercicio);

            imagen = itemView.findViewById(R.id.imagen);
            imagenTexto = itemView.findViewById(R.id.imagen_texto);

            nombre = itemView.findViewById(R.id.nombreTV);
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
