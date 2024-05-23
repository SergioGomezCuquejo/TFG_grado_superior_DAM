package com.example.yim.controlador.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yim.R;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.vista.controlador.CambiarActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;


public class LogrosAdaptador extends RecyclerView.Adapter<LogrosAdaptador.LogrosViewHolder> {
    private ArrayList<TablaLogroUsuario> logrosUsuario;
    private Context context;

    public LogrosAdaptador(Context context, ArrayList<TablaLogroUsuario> logrosUsuario) {
        this.context = context;
        this.logrosUsuario = logrosUsuario;
    }

    @NonNull
    @Override
    public LogrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_logros, parent, false);

        return new LogrosViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LogrosViewHolder holder, int position) {
        TablaLogroUsuario logro = logrosUsuario.get(position);

        holder.logro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarActivity.cambiar(context, logro);
            }
        });

        holder.imagen.setImageResource(R.drawable.logro_50_flexiones);

        if(logro.getProgreso() < logro.getTotal()) {
            holder.progreso.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)  holder.progreso.getLayoutParams();
            int progreso = (int) (( ((double) logro.getProgreso()) / logro.getTotal()) * 80);
            layoutParams.width = (int) ((80 - progreso) * Resources.getSystem().getDisplayMetrics().density);
            layoutParams.setMarginStart( (int) (progreso * Resources.getSystem().getDisplayMetrics().density));

            holder.progreso.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return logrosUsuario.size();
    }

    public static class LogrosViewHolder extends RecyclerView.ViewHolder {
        FrameLayout logro;
        ShapeableImageView imagen;
        View progreso;

        public LogrosViewHolder(@NonNull View itemView) {
            super(itemView);
            logro = itemView.findViewById(R.id.logro);
            imagen = itemView.findViewById(R.id.imagen);
            progreso = itemView.findViewById(R.id.progreso);
        }
    }
}
