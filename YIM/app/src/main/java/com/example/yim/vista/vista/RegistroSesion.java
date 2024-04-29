package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

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
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
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
    EditText nombre, correo, contrasena;
    TextView error;
    Button registrarse, iniciarSesion;

    String nombreUsuario, correoUsuario, contrasenaUsuario;

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
        correo = findViewById(R.id.correo);
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
            correoUsuario = correo.getText().toString();
            contrasenaUsuario = contrasena.getText().toString();

            if(!camposVacios(nombreUsuario, correoUsuario, contrasenaUsuario)){
                registro(nombreUsuario, correoUsuario, contrasenaUsuario);

            }

        } else if (id == R.id.iniciar_sesion) {
            cambiarActivity(InicioSesion.class);

        }
    }

    //Comprueba que todos los campos estén rellenos.
    public boolean camposVacios(String nombreUsuario, String eamilUsuario, String contrasenaUsuario){
        boolean vacio;

        if(!nombreUsuario.isEmpty() && !eamilUsuario.isEmpty() && !contrasenaUsuario.isEmpty() ){
            vacio = false;
        }else {
            vacio = true;
            mostrarError("Complete todos los campos");
        }

        return vacio;
    }

    //Registrar al usuario.
    private void registro(String nombreUsuario, String correoUsuario, String contrasenaUsuario){

        if (validarEmail(correoUsuario) && validarContrasena(contrasenaUsuario)){
            auth.createUserWithEmailAndPassword(correoUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String id = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                        firebaseManager.agregarUsuario(id, contrasenaUsuario, correoUsuario, nombreUsuario);

                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al registrar sesión.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Comprobar si la estructura del correo es correcta.
    private boolean validarEmail(String correoUsuario) {
        boolean correcto;

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(pattern.matcher(correoUsuario).matches()){
            correcto = true;
        }else{
            correcto = false;
            mostrarError("Correo incorrecto.");
        }

        return correcto;
    }

    //Comprobar que la contraseña tenga mínimo 6 carácteres.
    private boolean validarContrasena(String contrasenaUsuario){
        boolean correcta;

        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+");

        if( contrasena.length() >= 6 ){
            if(pattern.matcher(contrasenaUsuario).matches()) {
                correcta = true;
            }else{
                correcta = false;

                mostrarError("La contraseña debe tener al menos un número, una mayúscula y un carácter.");
            }
        }else{
            correcta = false;

            mostrarError("La contraseña debe tener 6 caracteres como mínimo.");
        }
        return correcta;
    }

    //Cambiar a otra interfaz.
    private void cambiarActivity(Class<?> activity) {
        cambiar(this, activity);
    }

    //Mostrar el error por la interfaz.
    private void mostrarError(String mensaje){
        if(error.getVisibility() == View.GONE ){
            error.setVisibility(View.VISIBLE);
        }
        error.setText(mensaje);
    }
}