package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;
import static com.example.yim.vista.controlador.Validar.validarContrasena;
import static com.example.yim.vista.controlador.Validar.validarEmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yim.R;
import com.example.yim.modelo.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegistroSesion extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth;
    FirebaseManager firebaseManager;
    EditText nombre, email, contrasena;
    TextView error;
    Button registrarse, iniciarSesion;

    String nombreUsuario, emailUsuario, contrasenaUsuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sesion);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();;

        //Referencias de las vistas.
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        error = findViewById(R.id.error);

        registrarse = findViewById(R.id.registrarse);
        iniciarSesion = findViewById(R.id.iniciar_sesion);

        //Listeners.
        registrarse.setOnClickListener(this);
        iniciarSesion.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.registrarse) {
            nombreUsuario = nombre.getText().toString();
            emailUsuario = email.getText().toString();
            contrasenaUsuario = contrasena.getText().toString();

            if(!camposVacios(nombreUsuario, emailUsuario, contrasenaUsuario)){
                registro(nombreUsuario, emailUsuario, contrasenaUsuario);

            }

        } else if (id == R.id.iniciar_sesion) {
            cambiarActivity(InicioSesion.class);

        }
    }

    //Comprueba que todos los campos estén rellenos.
    private boolean camposVacios(String nombreUsuario, String emailUsuario, String contrasenaUsuario){
        boolean vacio;

        if(!nombreUsuario.isEmpty() && !emailUsuario.isEmpty() && !contrasenaUsuario.isEmpty() ){
            vacio = false;
        }else {
            vacio = true;
            mostrarError("Complete todos los campos");
        }

        return vacio;
    }

    //Registrar al usuario.
    private void registro(String nombreUsuario, String emailUsuario, String contrasenaUsuario){
        try{
            boolean emailCorrecto = validarEmail(emailUsuario);
            boolean contrasenaCorrecta = validarContrasena(contrasenaUsuario);

            if ( emailCorrecto && contrasenaCorrecta){
                auth.createUserWithEmailAndPassword(emailUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                            firebaseManager.agregarUsuario(id, contrasenaUsuario, emailUsuario, nombreUsuario);

                            mostrarToast("Usuario registrado correctamente");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mostrarToast("Error al registrar sesión");
                    }
                });
            } else if(!emailCorrecto){
                mostrarError("Email no valido.");

            } else {
                if(contrasenaUsuario.length() < 6){
                    mostrarError("La contraseña debe tener al ménos 6 carácteres.");
                } else {
                    mostrarError("La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial.");
                }
            }
        }catch (Exception ex){
            mostrarToast("Error al registrar sesión");
        }
    }

    //Mostrar el error por la interfaz.
    private void mostrarError(String mensaje){
        if(error.getVisibility() == View.GONE ){
            error.setVisibility(View.VISIBLE);
        }
        error.setText(mensaje);
    }

    //Cambiar a otra interfaz.
    private void cambiarActivity(Class<?> activity) {
        finish();
        cambiar(this, activity);
    }

    //Mostrar alertas (Toasts)
    private void mostrarToast(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}