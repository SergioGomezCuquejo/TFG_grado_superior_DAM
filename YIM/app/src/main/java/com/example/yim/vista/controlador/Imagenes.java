package com.example.yim.vista.controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackUri;
import com.example.yim.modelo.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Imagenes {

    // Variables de instancias.
    private static StorageReference storageReference;
    private static FirebaseManager firebaseManager;
    public static String urlImagenPerfil = null;


    // Método para inicializar instancias.
    private static void inicializarStorage(){
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseManager = new FirebaseManager();
    }


    // Método para subir la imagen al Firebase Storage.
    public static void subirImagen(Context context, ProgressDialog progressDialog, String mensageDialog, String ruta, Uri url, FirebaseCallbackUri callback) {
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
                            if(ruta.startsWith("perfil/")){
                                subirImagenPerfil(context, progressDialog, urlImagen, new FirebaseCallbackBoolean() {
                                    @Override
                                    public void onCallback(boolean accionRealizada) {
                                        callback.onCallback(urlImagen);
                                    }
                                });

                            }else{
                                int index = ruta.indexOf('/');
                                String ID = ruta.substring(index + 1);

                                if(ruta.startsWith("rutina/")){
                                    subirImagenRutina(context, progressDialog, urlImagen, ID, new FirebaseCallbackBoolean() {
                                        @Override
                                        public void onCallback(boolean accionRealizada) {
                                            callback.onCallback(urlImagen);
                                        }
                                    });
                                } if(ruta.startsWith("ejercicio/")){
                                    subirImagenEjercicio(context, progressDialog, urlImagen, ID, new FirebaseCallbackBoolean() {
                                        @Override
                                        public void onCallback(boolean accionRealizada) {
                                            callback.onCallback(urlImagen);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                mostrarToast(context, "Error al cargar la imagen");
            }
        });
    }


    // Método para subir la imagen de perfil.
    public static void subirImagenPerfil(Context context, ProgressDialog progressDialog, Uri urlImagen, FirebaseCallbackBoolean callback){
        try{
            firebaseManager.actualizarPerfil(context, urlImagen.toString(), new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        progressDialog.dismiss();
                    } else {
                        mostrarToast(context, "Error al subir la imagen de perfil");
                    }
                    callback.onCallback(accionRealizada);
                }
            });

        } catch (Exception ex) {
            mostrarToast(context, "Error al actualizar la rutina.");
            ex.printStackTrace();
        }
    }

    // Método para subir la imagen de la rutina.
    public static void subirImagenRutina(Context context, ProgressDialog progressDialog, Uri urlImagen, String IDRutina, FirebaseCallbackBoolean callback){
        try{
            firebaseManager.actualizarRutina(context,IDRutina ,urlImagen.toString(), new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        progressDialog.dismiss();
                    } else {
                        mostrarToast(context, "Error al subir la imagen de la rutina");
                    }
                    callback.onCallback(accionRealizada);
                }
            });

        } catch (Exception ex) {
            mostrarToast(context, "Error al actualizar la rutina.");
            ex.printStackTrace();
        }
    }

    // Método para subir la imagen de la ejercicio.
    public static void subirImagenEjercicio(Context context, ProgressDialog progressDialog, Uri urlImagen, String IDRutina, FirebaseCallbackBoolean callback){
        try{
            firebaseManager.actualizarEjercicioCreado(context,IDRutina ,urlImagen.toString(), new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada) {
                        progressDialog.dismiss();
                    } else {
                        mostrarToast(context, "Error al subir la imagen del ejercicio");
                    }
                    callback.onCallback(accionRealizada);
                }
            });

        } catch (Exception ex) {
            mostrarToast(context, "Error al actualizar el ejercicio.");
            ex.printStackTrace();
        }
    }


    // Método para mostrar la imagen de perfil.
    public static boolean mostrarImagenPerfil(Context context, ImageView imageView){
        boolean mostrada = false;
        if (urlImagenPerfil != null){
            mostrarImagen(context, urlImagenPerfil, imageView);
            mostrada = true;
        }
        return mostrada;
    }


    // Método para mostrar una imagen usando Picasso.
    public static void mostrarImagen(Context context, String urlImagen, ImageView imageView){
        Picasso.with(context)
                .load(urlImagen)
                .resize(150, 150)
                .into(imageView);
    }

    // Método para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    public static void mostrarToast(Context context, String mensaje){
        MostratToast.mostrarToast(context, mensaje);
    }
}
