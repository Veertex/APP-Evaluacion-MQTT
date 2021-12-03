package com.app_balance.app_evaluacion_mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app_balance.app_evaluacion_mqtt.Controlador.DBGasto;
import com.app_balance.app_evaluacion_mqtt.Controlador.DBIngreso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class editarRegistro extends AppCompatActivity {
    EditText monto2,operacion2,fecha2;
    int id;
    DBIngreso dbIngreso;
    DBGasto dbGasto;
    Locale cl;
    Currency dollars;
    NumberFormat clpFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_registro);
        this.monto2=findViewById(R.id.monto2);
        this.operacion2=findViewById(R.id.operacion2);
        this.fecha2=findViewById(R.id.fecha2);
        this.dbGasto = new DBGasto(getApplicationContext());
        this.dbIngreso = new DBIngreso(getApplicationContext());
        this.monto2.setText(getIntent().getStringExtra("monto"));
        this.operacion2.setText(getIntent().getStringExtra("tipo"));
        this.fecha2.setText(String.valueOf(getIntent().getStringExtra("fecha")));
        id=Integer.parseInt(getIntent().getStringExtra("id"));
        this.cl = new Locale("es", "CL");
        this.dollars = Currency.getInstance(cl);
        this.clpFormat = NumberFormat.getCurrencyInstance(cl);
    }
    public boolean verificarCampos(EditText editText){
        if(editText.getText().toString().equals(String.valueOf("").trim())|editText.getText().toString()==null){
            Toast.makeText(this,"Sin monto ("+this.dollars.getSymbol()+") a registrar!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void actualizar(View view){
        if(verificarCampos(this.monto2)){
            if(this.operacion2.getText().toString().equals("INGRESO")){
                dbIngreso.actualizarRegistro(Integer.parseInt(getIntent().getStringExtra("id")),Integer.parseInt(this.monto2.getText().toString()));
                Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

            }else if(this.operacion2.getText().toString().equals("GASTO")){
                dbGasto.actualizarRegistro(Integer.parseInt(getIntent().getStringExtra("id")),Integer.parseInt(this.monto2.getText().toString()));
                Toast.makeText(this, "Registro actualizado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void eliminar(View view){
        if(this.operacion2.getText().toString().equals("INGRESO")){
            dbIngreso.eliminarRegistro(Integer.parseInt(getIntent().getStringExtra("id")));
            Toast.makeText(this, "Registro eliminado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if(this.operacion2.getText().toString().equals("GASTO")){
            dbGasto.eliminarRegistro(Integer.parseInt(getIntent().getStringExtra("id")));
            Toast.makeText(this, "Registro eliminado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}