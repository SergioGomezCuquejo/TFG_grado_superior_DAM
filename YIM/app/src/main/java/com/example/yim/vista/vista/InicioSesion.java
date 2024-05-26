package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity implements View.OnClickListener {

    //Variables de instancias.
    private FirebaseAuth auth;
    EditText email, contrasena;
    TextView error;
    Button iniciarSesion, registrarse;
    String emailUsuario, contrasenaUsuario;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();


        //Referencias de las vistas.
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        error = findViewById(R.id.error);

        iniciarSesion = findViewById(R.id.iniciar_sesion);
        registrarse = findViewById(R.id.registrarse);


        //Listeners.
        iniciarSesion.setOnClickListener(this);
        registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        if (id.equals("iniciar_sesion")) {
            emailUsuario = email.getText().toString();
            contrasenaUsuario = contrasena.getText().toString();

            if(!camposVacios(emailUsuario, contrasenaUsuario)){
                iniciarSesion(emailUsuario, contrasenaUsuario);

            } else {
                mostrarToast("Ingrese todos los datos");
            }

        } else if (id.equals("registrarse")) {
            cambiarActivity(RegistroSesion.class);

        }
    }

    //Método para comprobar si los campos están o no vacios.
    private boolean camposVacios(String emailUsuario, String contrasenaUsuario){
        return emailUsuario.isEmpty() || contrasenaUsuario.isEmpty();
    }


    //Método que comprueba si los datos son o no correctos.
    private void iniciarSesion(String emailUsuario, String contrasenaUsuario){
        try{
            boolean emailCorrecto = ValidarDatos.validarEmail(emailUsuario);
            boolean contrasenaCorrecta = ValidarDatos.validarContrasena(contrasenaUsuario);

            if ( emailCorrecto && contrasenaCorrecta){
                auth.signInWithEmailAndPassword(emailUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mostrarToast("Bienvenido a YIM");
                            cambiarActivity(Inicio.class);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mostrarError("Correo o contraseña incorrectos.");
                    }
                });

            } else if(!emailCorrecto){
                mostrarError("Email no valido.");

            } else {
                if(contrasenaUsuario.length() < 6){
                    mostrarError("La contraseña debe tener al ménos 6 caracteres.");
                } else {
                    mostrarError("La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial.");
                }
            }
        }catch (Exception ex){
            mostrarToast( "Error al inicciar sesión");
            ex.printStackTrace();
        }
    }


    //Método para mostrar el error por la interfaz.
    private void mostrarError(String mensaje){
        if(error.getVisibility() == View.GONE ){
            error.setVisibility(View.VISIBLE);
        }
        error.setText(mensaje);
    }


    //Se comprueba si ha iniciado sesión anteriormente y, si es así se redirige al inicio.
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            cambiarActivity(Inicio.class);
        }
    }


    //Métodos para llamar a CambiarActivity.java. (Clase que permite el cambio de activity)
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }


    //Métodos para llamar a MostratToast.java. (Clase que muestra un mensaje por pantalla)
    private void mostrarToast(String mensaje){
        MostratToast.mostrarToast(this, mensaje);
    }

}