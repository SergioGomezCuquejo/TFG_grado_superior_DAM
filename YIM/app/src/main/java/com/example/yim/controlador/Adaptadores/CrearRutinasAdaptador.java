package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaRutinasUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.vista.PopupRutinas;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;


public class CrearRutinasAdaptador extends RecyclerView.Adapter<CrearRutinasAdaptador.CrearRutinasViewHolder> {
    private ArrayList<TablaRutinasUsuario> rutinasUsuario;
    private Context context;
    HashMap<String, String> musculosHM;

    public CrearRutinasAdaptador(Context context, ArrayList<TablaRutinasUsuario> rutinasUsuario, HashMap<String, String> musculosHM) {
        this.context = context;
        this.rutinasUsuario = rutinasUsuario;
        this.musculosHM = musculosHM;
    }

    @NonNull
    @Override
    public CrearRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crear_rutinas, parent, false);

        return new CrearRutinasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CrearRutinasViewHolder holder, int position) {
        TablaRutinasUsuario rutinaUsuario = rutinasUsuario.get(position);

        if(rutinaUsuario.getInformacion().isActivo()){
            holder.rutina.setBackgroundResource(R.drawable._style2_borde_amarillo_20__padding_10);
        }

        holder.rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, PopupRutinas.class, rutinaUsuario);
            }
        });

        holder.imagen.setImageResource(R.drawable.pierna);
        holder.nombre.setText(rutinaUsuario.getInformacion().getNombre());

    }

    @Override
    public int getItemCount() {
        return rutinasUsuario.size();
    }

    public static class CrearRutinasViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rutina;
        ShapeableImageView imagen;
        TextView nombre;

        public CrearRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            rutina = itemView.findViewById(R.id.rutina);
            imagen = itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombre);
        }
    }
}
