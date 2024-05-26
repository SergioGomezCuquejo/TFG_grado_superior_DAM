package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaRutinaUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.example.yim.vista.controlador.Imagenes;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.vista.PopupRutinas;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class VerRutinasAdaptador extends RecyclerView.Adapter<VerRutinasAdaptador.VerRutinasViewHolder> {
    private ArrayList<TablaRutinaUsuario> rutinasUsuario;
    private Context context;

    public VerRutinasAdaptador(Context context, ArrayList<TablaRutinaUsuario> rutinasUsuario) {
        this.context = context;
        this.rutinasUsuario = rutinasUsuario;
    }

    @NonNull
    @Override
    public VerRutinasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ver_rutinas, parent, false);

        return new VerRutinasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VerRutinasViewHolder holder, int position) {
        TablaRutinaUsuario rutinaUsuario = rutinasUsuario.get(position);
        String nombre;

        if(rutinaUsuario.getInformacion().isActivo()){
            holder.rutina.setBackgroundResource(R.drawable._style2_borde_amarillo_20__padding_10);
        }

        holder.rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, PopupRutinas.class, rutinaUsuario);
            }
        });

        if(rutinaUsuario.getInformacion().getImagen() != null){
            Imagenes.mostrarImagen(context, rutinaUsuario.getInformacion().getImagen(),  holder.imagenIV);
        }else{
            holder.nombreRutinaFL.setVisibility(View.VISIBLE);
            holder.imagenIV.setVisibility(View.GONE);
            nombre = rutinaUsuario.getInformacion().getNombre().toUpperCase();
            if (nombre.length() >= 3) {
                nombre = nombre.substring(0, 3);
            }
            holder.nombreRutinaTV.setText(nombre);
        }
        holder.nombre.setText(rutinaUsuario.getInformacion().getNombre());

    }

    @Override
    public int getItemCount() {
        return rutinasUsuario.size();
    }

    public static class VerRutinasViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rutina;
        ShapeableImageView imagenIV;
        TextView nombre, nombreRutinaTV;
        FrameLayout nombreRutinaFL;

        public VerRutinasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreRutinaFL = itemView.findViewById(R.id.nombre_rutina_fl);
            nombreRutinaTV = itemView.findViewById(R.id.nombre_rutina_tv);
            rutina = itemView.findViewById(R.id.rutina);
            imagenIV = itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombreTV);
        }
    }
}
