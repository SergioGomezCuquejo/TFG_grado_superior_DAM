package com.example.yim.vista.controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.yim.modelo.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Imagenes {
    private static StorageReference storageReference;
    private static FirebaseManager firebaseManager;
    public static String urlImagenPerfil = null;
    private static void inicializarStorage(){
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseManager = new FirebaseManager();
    }
    public static void subirImagen(Context context, ProgressDialog progressDialog, String mensageDialog, String ruta, Uri url) {
        inicializarStorage();
        progressDialog.setMessage(mensageDialog);

        progressDialog.show();
        StorageReference reference = storageReference.child(ruta);
        reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                if (uriTask.isSuccessful()){
                    uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri urlImagen) {
                            mostrarToast(context, "klk");
                            firebaseManager.modificarPerfil(context, urlImagen.toString());
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                mostrarToast(context, "Error al cargar foto");
            }
        });
    }

    public static boolean mostrarImagenPerfil(Context context, ImageView imageView){
        boolean mostrada = false;
        if (urlImagenPerfil != null){
            mostrarImagen(context, urlImagenPerfil, imageView);
            mostrada = true;
        }
        return mostrada;
    }

    public static void mostrarImagen(Context context, String urlImagen, ImageView imageView){
        Picasso.with(context)
                .load(urlImagen)
                .resize(150, 150)
                .into(imageView);
    }

    private static void mostrarToast(Context context, String mensaje){
        MostratToast.mostrarToast(context, mensaje);
    }
}
