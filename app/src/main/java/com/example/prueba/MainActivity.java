package com.example.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //ATRIBUTOS
    EditText cajaNombre,cajaCedula,cajaCargo;
    Button botonRegistrar,botonBuscar;

    Map<String, Object> empleadoColeccion = new HashMap<>();
    FirebaseFirestore baseDatos = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cajaNombre=findViewById(R.id.nombre);
        cajaCedula=findViewById(R.id.cedula);
        cajaCargo=findViewById(R.id.cargo);

        botonRegistrar=findViewById(R.id.botonRegistrar);
        botonBuscar=findViewById(R.id.botonBuscar);


        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre=cajaNombre.getText().toString();
                String cedula=cajaCedula.getText().toString();
                String cargo=cajaCargo.getText().toString();

                empleadoColeccion.put("nombre",nombre);
                empleadoColeccion.put("cedula",cedula);
                empleadoColeccion.put("cargo",cargo);

                agregarEmpleados();

            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Listado.class);
                startActivity(intent);

            }
        });

    }


    private void agregarEmpleados(){

        baseDatos.collection("empleados")
                .add(empleadoColeccion)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"exito",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error "+e,Toast.LENGTH_LONG).show();
                    }
                });

    }

    private Map<String, Object> crearColeccionEmpleado(String nombre, String cedula, String cargo){

           Map<String, Object> coleccion = new HashMap<>();
           coleccion.put("nombre",nombre);
           coleccion.put("cedula",cedula);
           coleccion.put("cargo",cargo);

           return coleccion;


    }




}