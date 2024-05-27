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
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.vista.PopupRutinas;

import java.util.ArrayList;


public class InicioRutinasAdaptador extends RecyclerView.Adapter<InicioRutinasAdaptador.InicioRutinasViewHolder> {
    private ArrayList<TablaRutinaUsuario> rutinasUsuario;
    private Context context;

    public InicioRutinasAdaptador(Context context, ArrayList<TablaRutinaUsuario> rutinasUsuario) {
        this.context = context;
        this.rutinasUsuario = rutinasUsuario;
    }

    @NonNull
    @Override
    public InicioRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inicio, parent, false);

        return new InicioRutinasViewHolder(view);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull InicioRutinasViewHolder holder, int position) {
        TablaRutinaUsuario rutinaUsuario = rutinasUsuario.get(position);
        String nombre;

        holder.rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, PopupRutinas.class, rutinaUsuario);
            }
        });

        if(rutinaUsuario.getInformacion().getImagen() != null){
            Imagenes.mostrarImagen(context, rutinaUsuario.getInformacion().getImagen(),  holder.imagen);
        }else{
            holder.nombreRutinaFL.setVisibility(View.VISIBLE);
            holder.imagen.setVisibility(View.GONE);
            nombre = rutinaUsuario.getInformacion().getNombre().toUpperCase();
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            holder.nombreRutinaTV.setText(nombre);
        }



    }

    @Override
    public int getItemCount() {
        return rutinasUsuario.size();
    }

    public static class InicioRutinasViewHolder extends RecyclerView.ViewHolder {
        FrameLayout rutina, nombreRutinaFL;
        ImageView imagen;
        TextView nombreRutinaTV;

        public InicioRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            rutina = itemView.findViewById(R.id.imagen_fl);
            imagen = itemView.findViewById(R.id.imagen);
            nombreRutinaFL = itemView.findViewById(R.id.nombre_fl);
            nombreRutinaTV = itemView.findViewById(R.id.nombre_tv);
        }
    }
}
