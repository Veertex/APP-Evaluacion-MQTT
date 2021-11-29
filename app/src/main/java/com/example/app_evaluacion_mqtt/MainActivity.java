package com.example.app_evaluacion_mqtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_evaluacion_mqtt.Adaptador.Adapter;
import com.example.app_evaluacion_mqtt.Controlador.DBCapital;
import com.example.app_evaluacion_mqtt.Controlador.DBGasto;
import com.example.app_evaluacion_mqtt.Controlador.DBIngreso;
import com.example.app_evaluacion_mqtt.Modelo.Gasto;
import com.example.app_evaluacion_mqtt.Modelo.Ingreso;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBIngreso dbIngreso;
    DBGasto dbGasto;
    DBCapital dbCapital;
    TextView montoActualCapital;
    EditText textMonto;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.montoActualCapital=findViewById(R.id.montoActualCapital);
        this.textMonto=findViewById(R.id.textMonto);
        this.dbIngreso = new DBIngreso(getApplicationContext());
        this.dbGasto = new DBGasto(getApplicationContext());
        this.dbCapital = new DBCapital(getApplicationContext());
        this.recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        actualizar();
    }


    public void actualizar(){
        mostrarMontoTotalInicio();

        ArrayList<Operaciones> operaciones = listarOperaciones();

        operaciones = ordernarOperaciones(operaciones);

        this.adapter = new Adapter(operaciones,this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.smoothScrollToPosition(this.adapter.getItemCount());

        adapter = new Adapter(operaciones,this);
        recyclerView.setAdapter(adapter);
        ArrayList<Operaciones> finalOperaciones = operaciones;
        Intent editarRegistro = new Intent(this, com.example.app_evaluacion_mqtt.editarRegistro.class);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarRegistro.putExtra("id",String.valueOf(finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getId()));
                if (finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getTipo().equals("INGRESO")){
                    editarRegistro.putExtra("monto",String.valueOf(dbIngreso.cargarRegistro(finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getId()).getMonto()));
                }else if(finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getTipo().equals("GASTO")){
                    editarRegistro.putExtra("monto",String.valueOf(dbGasto.cargarRegistro(finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getId()).getMonto()));
                }
                editarRegistro.putExtra("tipo",finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                editarRegistro.putExtra("fecha",String.valueOf(finalOperaciones.get(recyclerView.getChildAdapterPosition(v)).getFecha()));
                startActivity(editarRegistro);
            }
        });

    }

    public void añadirIngreso(View view){
        this.dbIngreso.insertarDatos(dbCapital.cargarRegistros().get(0).getId(),Integer.parseInt(this.textMonto.getText().toString()));
        Toast.makeText(this, "Registro ingresado!", Toast.LENGTH_SHORT).show();
        actualizar();

    }
    public void añadirGastos(View view){
        this.dbGasto.insertarDatos(dbCapital.cargarRegistros().get(0).getId(),Integer.parseInt(this.textMonto.getText().toString()));
        Toast.makeText(this, "Registro ingresado!", Toast.LENGTH_SHORT).show();
        actualizar();
    }

    public void mostrarMontoTotalInicio(){
        if(dbCapital.cargarRegistros().isEmpty()){
            dbCapital.insertarDatos(0);
        }else{
            ArrayList<Ingreso> ingresos = dbIngreso.cargarRegistros();
            int totalIngresos=0;
            for(int x=0;x<ingresos.size();x++){
                totalIngresos=totalIngresos+ingresos.get(x).getMonto();
            }

            ArrayList<Gasto> gastos = dbGasto.cargarRegistros();
            int totalGastos=0;
            for(int x=0;x<gastos.size();x++){
                totalGastos=totalGastos+gastos.get(x).getMonto();
            }

            int total=totalIngresos-totalGastos;


            dbCapital.actualizarRegistro(dbCapital.cargarRegistros().get(0).getId(),total);
            this.montoActualCapital.setText(String.valueOf(dbCapital.cargarRegistros().get(0).getMonto()));

        }
    }

    public ArrayList<Operaciones> listarOperaciones(){
        ArrayList<Ingreso> ingresos = dbIngreso.cargarRegistros();
        ArrayList<Gasto> gastos = dbGasto.cargarRegistros();
        ArrayList<Operaciones> operaciones = new ArrayList<>();

        for(int x=0; x<ingresos.size();x++){
            Operaciones operacion = new Operaciones(ingresos.get(x).getId_ingreso(),"INGRESO",ingresos.get(x).getHora());
            operaciones.add(operacion);
        }

        for(int y=0; y<gastos.size();y++){
            Operaciones operacion = new Operaciones(gastos.get(y).getId_gasto(),"GASTO",gastos.get(y).getHora());
            operaciones.add(operacion);
        }

        return operaciones;
    }

    public ArrayList<Operaciones> ordernarOperaciones(ArrayList<Operaciones> operaciones){
        ArrayList<Operaciones> operacionesOrdenadas = operaciones;

        for(int x=0;x<operacionesOrdenadas.size();x++){
            if(x!=0){
                if(operacionesOrdenadas.get(x-1).getFecha().after(operacionesOrdenadas.get(x).getFecha())){

                    Operaciones operacionCambio = new Operaciones(operacionesOrdenadas.get(x).getId(),
                            operacionesOrdenadas.get(x).getTipo(),
                            operacionesOrdenadas.get(x).getFecha());

                    operacionesOrdenadas.get(x).setId(operacionesOrdenadas.get(x-1).getId());
                    operacionesOrdenadas.get(x).setTipo(operacionesOrdenadas.get(x-1).getTipo());
                    operacionesOrdenadas.get(x).setFecha(operacionesOrdenadas.get(x-1).getFecha());

                    operacionesOrdenadas.get(x-1).setId(operacionCambio.getId());
                    operacionesOrdenadas.get(x-1).setTipo(operacionCambio.getTipo());
                    operacionesOrdenadas.get(x-1).setFecha(operacionCambio.getFecha());

                    if(x!=0){
                        for(int y=x-1;y>0;y--){
                            if(operacionesOrdenadas.get(y-1).getFecha().after(operacionesOrdenadas.get(y).getFecha())) {

                                Operaciones operacionCambio2 = new Operaciones(operacionesOrdenadas.get(y).getId(),
                                        operacionesOrdenadas.get(y).getTipo(),
                                        operacionesOrdenadas.get(y).getFecha());

                                operacionesOrdenadas.get(y).setId(operacionesOrdenadas.get(y - 1).getId());
                                operacionesOrdenadas.get(y).setTipo(operacionesOrdenadas.get(y - 1).getTipo());
                                operacionesOrdenadas.get(y).setFecha(operacionesOrdenadas.get(y - 1).getFecha());

                                operacionesOrdenadas.get(y - 1).setId(operacionCambio2.getId());
                                operacionesOrdenadas.get(y - 1).setTipo(operacionCambio2.getTipo());
                                operacionesOrdenadas.get(y - 1).setFecha(operacionCambio2.getFecha());
                            }
                        }
                    }

                }
            }

        }

        return operacionesOrdenadas;
    }


    public class Operaciones{
        int id;
        String tipo;
        Timestamp fecha;

        public Operaciones(int id, String tipo, Timestamp fecha) {
            this.id = id;
            this.tipo = tipo;
            this.fecha = fecha;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Timestamp getFecha() {
            return fecha;
        }

        public void setFecha(Timestamp fecha) {
            this.fecha = fecha;
        }
    }

}

