package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaEjercicioCreado;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.vista.PopupRutinas;
import com.example.yim.vista.vista.PopupVerEjerciciosCreados;

import java.util.ArrayList;


public class InicioEjerciciosAdaptador extends RecyclerView.Adapter<InicioEjerciciosAdaptador.InicioEjerciciosViewHolder> {
    private ArrayList<TablaEjercicioCreado> ejerciciosCreados;
    private Context context;

    public InicioEjerciciosAdaptador(Context context, ArrayList<TablaEjercicioCreado> ejerciciosCreados) {
        this.context = context;
        this.ejerciciosCreados = ejerciciosCreados;
    }

    @NonNull
    @Override
    public InicioEjerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inicio, parent, false);

        return new InicioEjerciciosViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull InicioEjerciciosViewHolder holder, int position) {
        TablaEjercicioCreado ejercicioCreado = ejerciciosCreados.get(position);
        String nombre;

        holder.ejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, ejercicioCreado);
            }
        });

        if(ejercicioCreado.getImagen() != null){
            Imagenes.mostrarImagen(context, ejercicioCreado.getImagen(),  holder.imagen);
        }else{
            holder.nombreEjercicioFL.setVisibility(View.VISIBLE);
            holder.imagen.setVisibility(View.GONE);
            nombre = ejercicioCreado.getNombre().toUpperCase();
            nombre = nombre.substring(1);
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            holder.nombreEjercicioTV.setText(nombre);
        }



    }

    @Override
    public int getItemCount() {
        return ejerciciosCreados.size();
    }

    public static class InicioEjerciciosViewHolder extends RecyclerView.ViewHolder {
        FrameLayout ejercicio, nombreEjercicioFL;
        ImageView imagen;
        TextView nombreEjercicioTV;

        public InicioEjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicio = itemView.findViewById(R.id.imagen_fl);
            imagen = itemView.findViewById(R.id.imagen);
            nombreEjercicioFL = itemView.findViewById(R.id.nombre_fl);
            nombreEjercicioTV = itemView.findViewById(R.id.nombre_tv);
        }
    }
}
