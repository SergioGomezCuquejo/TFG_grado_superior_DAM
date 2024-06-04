package com.example.yim.vista.vista;

import static com.example.yim.vista.controlador.CambiarActivity.cambiar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yim.R;
import com.example.yim.modelo.Callbacks.FirebaseCallbackBoolean;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjercicios;
import com.example.yim.modelo.Callbacks.FirebaseCallbackEjerciciosPorDefecto;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogros;
import com.example.yim.modelo.Callbacks.FirebaseCallbackLogrosUsuario;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculos;
import com.example.yim.modelo.Callbacks.FirebaseCallbackMusculosUsuario;
import com.example.yim.modelo.FirebaseManager;
import com.example.yim.modelo.tablas.TablaEjercicio;
import com.example.yim.modelo.tablas.TablaEjercicioPorDefecto;
import com.example.yim.modelo.tablas.TablaEjerciciosUsuario;
import com.example.yim.modelo.tablas.TablaLogro;
import com.example.yim.modelo.tablas.TablaLogroUsuario;
import com.example.yim.modelo.tablas.TablaMusculo;
import com.example.yim.modelo.tablas.TablaMusculoUsuario;
import com.example.yim.modelo.tablas.TablaPerfil;
import com.example.yim.modelo.tablas.TablaUsuario;
import com.example.yim.vista.controlador.MostratToast;
import com.example.yim.vista.controlador.ValidarDatos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class RegistroSesion extends AppCompatActivity implements View.OnClickListener {

    // Variables de instancias.
    private FirebaseAuth auth;
    private FirebaseManager firebaseManager;
    EditText nombre, email, contrasena;
    TextView error, aceptarTV, politicasTV;
    CheckBox aceptarPoliticas;
    Button registrarse, iniciarSesion;
    String nombreUsuario, emailUsuario, contrasenaUsuario;
    ProgressBar cargando;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sesion);

        //Inicializar instancias.
        auth = FirebaseAuth.getInstance();
        firebaseManager = new FirebaseManager();


        //Referencias de las vistas.
        cargando = findViewById(R.id.cargando);

        nombre = findViewById(R.id.nombreTV);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        error = findViewById(R.id.error);

        aceptarPoliticas = findViewById(R.id.aceptar_politicas);
        aceptarTV = findViewById(R.id.aceptar_tv);
        politicasTV = findViewById(R.id.politicas_tv);

        registrarse = findViewById(R.id.registrarse);
        iniciarSesion = findViewById(R.id.iniciar_sesion);


        //Listeners.
        aceptarTV.setOnClickListener(this);
        politicasTV.setOnClickListener(this);

        registrarse.setOnClickListener(this);
        iniciarSesion.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id) {
            case "registrarse":
                cargando.setVisibility(View.VISIBLE);
                nombreUsuario = nombre.getText().toString();
                emailUsuario = email.getText().toString();
                contrasenaUsuario = contrasena.getText().toString();

                if(camposCorrectos()){
                    registro();
                }

                break;
            case "iniciar_sesion":
                cambiarActivity(InicioSesion.class);
                finish();
                break;

            case "aceptar_tv":
                aceptarPoliticas.setChecked(true);
                break;

            case "politicas_tv":
                cambiarActivity(PoliticasPrivacidad.class);
                break;
        }
    }


    // Método para agregar el usuario a Firebase Authentication.
    private void registro(){
        try{
            auth.createUserWithEmailAndPassword(emailUsuario, contrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String IDAuth = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        crearUsuario(IDAuth, nombreUsuario, emailUsuario);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mostrarToast("Error al registrar sesión");
                }
            });

        }catch (Exception ex){
            mostrarToast("Error al registrar sesión");
            ex.printStackTrace();
        }
    }


    // Método para crear el objeto usuario.
    private void crearUsuario(String IDAuth, String nombreUsuario, String emailUsuario){
        obtenerEjercicios(new FirebaseCallbackEjerciciosPorDefecto() {
            @Override
            public void onCallback(ArrayList<TablaEjercicioPorDefecto> ejerciciosUsuario) {

                obtenerLogros(new FirebaseCallbackLogrosUsuario() {
                    @Override
                    public void onCallback(ArrayList<TablaLogroUsuario> logrosUsuario) {

                        obtenerMusculos(new FirebaseCallbackMusculosUsuario() {
                            @Override
                            public void onCallback(ArrayList<TablaMusculoUsuario> musculosUsuario) {
                                TablaUsuario usuario = new TablaUsuario();

                                usuario.setEjercicios(new TablaEjerciciosUsuario(ejerciciosUsuario));
                                usuario.setLogros(logrosUsuario);
                                usuario.setMusculos(musculosUsuario);
                                usuario.setPerfil(new TablaPerfil(emailUsuario, nombreUsuario));

                                agregarUsuario(IDAuth, usuario);
                            }
                        });

                    }
                });

            }
        });
    }


    // Método para rellenar los ejercicios por defecto del usuario.
    private void obtenerEjercicios(FirebaseCallbackEjerciciosPorDefecto callback){
        try{
            firebaseManager.obtenerEjercicios(this, new FirebaseCallbackEjercicios() {
                @Override
                public void onCallback(ArrayList<TablaEjercicio> ejercicios) {
                    ArrayList <TablaEjercicioPorDefecto> ejerciciosUsuario = new ArrayList<>();

                    for (TablaEjercicio ejercicio : ejercicios){
                        ejerciciosUsuario.add(new TablaEjercicioPorDefecto(ejercicio));
                    }

                    callback.onCallback(ejerciciosUsuario);
                }
            });
        }catch (Exception ex){
            mostrarToast("Error al obtener los ejercicios");
            ex.printStackTrace();
        }
    }


    // Método para rellenar los logros por defecto del usuario.
    private void obtenerLogros(FirebaseCallbackLogrosUsuario callback){
        try{
            firebaseManager.obtenerLogros(this, new FirebaseCallbackLogros() {
                @Override
                public void onCallback(ArrayList<TablaLogro> logros) {
                    ArrayList <TablaLogroUsuario> logrosUsuario = new ArrayList<>();

                    for (TablaLogro logro : logros){
                        logrosUsuario.add(new TablaLogroUsuario(logro));
                    }

                    callback.onCallback(logrosUsuario);
                }
            });
        }catch (Exception ex){
            mostrarToast("Error al obtener los logros");
            ex.printStackTrace();
        }
    }


    // Método para rellenar los músculos por defecto del usuario.
    private void obtenerMusculos(FirebaseCallbackMusculosUsuario callback){
        try{
            firebaseManager.obtenerMusculos(this, new FirebaseCallbackMusculos() {
                @Override
                public void onCallback(ArrayList<TablaMusculo> musculos) {
                    ArrayList <TablaMusculoUsuario> musculosUsuario = new ArrayList<>();

                    for (TablaMusculo musculo : musculos){
                        musculosUsuario.add(new TablaMusculoUsuario(musculo));
                    }

                    callback.onCallback(musculosUsuario);
                }
            });
        }catch (Exception ex){
            mostrarToast("Error al obtener los músculos");
            ex.printStackTrace();
        }
    }


    // Método para agregar el usuario a Firebase Database.
    private void agregarUsuario(String IDUsuario, TablaUsuario usuario){
        try{
            firebaseManager.agregarUsuario(RegistroSesion.this, IDUsuario, usuario, new FirebaseCallbackBoolean() {
                @Override
                public void onCallback(boolean accionRealizada) {
                    if (accionRealizada){
                        mostrarToast( "Bienvenido a YIM");
                        cambiarActivity(Inicio.class);

                    }else{
                        mostrarToast( "Error al registrar el usuario");
                    }
                    cargando.setVisibility(View.GONE);
                }
            });
        }catch (Exception ex){
            mostrarToast("Error al registrar sesión");
            ex.printStackTrace();
        }
    }

    // Método para comprobar que todos los campos esten correctamente.
    private boolean camposCorrectos(){
        boolean camposCorrectos, emailCorrecto, contrasenaCorrecta, politicasAceptadas;

        camposCorrectos = false;
        mostrarError("");

        if(!camposVacios(nombreUsuario, emailUsuario, contrasenaUsuario)) {
            emailCorrecto = ValidarDatos.validarEmail(emailUsuario);
            contrasenaCorrecta = ValidarDatos.validarContrasena(contrasenaUsuario);
            politicasAceptadas = aceptarPoliticas.isChecked();

            if (emailCorrecto && contrasenaCorrecta && politicasAceptadas){
                camposCorrectos = true;

            } else if(!emailCorrecto){
                mostrarError("Email no valido.");
                contrasena.setText("");

            } else if (!contrasenaCorrecta) {
                if(contrasenaUsuario.length() < 6){
                    mostrarError("La contraseña debe tener al ménos 6 caracteres.");
                } else {
                    mostrarError("La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial.");
                }
                contrasena.setText("");

            } else {
                mostrarToast("Se deben aceptar las políticas de privacidad");
                aceptarPoliticas.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.rojo_clarito)));
            }

        }else{
            mostrarToast("Complete todos los campos");
        }
        return camposCorrectos;
    }



    //Método para comprobar si los campos están o no vacios.
    private boolean camposVacios(String nombreUsuario, String emailUsuario, String contrasenaUsuario){
        return nombreUsuario.isEmpty() || emailUsuario.isEmpty() || contrasenaUsuario.isEmpty();
    }

    //Mostrar el error por la interfaz.
    private void mostrarError(String mensaje){
        if(error.getVisibility() == View.GONE ){
            error.setVisibility(View.VISIBLE);
        }
        error.setText(mensaje);
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