package com.example.app_evaluacion_mqtt.Adaptador;

import com.example.app_evaluacion_mqtt.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_evaluacion_mqtt.Controlador.DBGasto;
import com.example.app_evaluacion_mqtt.Controlador.DBIngreso;
import com.example.app_evaluacion_mqtt.MainActivity;
import com.example.app_evaluacion_mqtt.Modelo.Gasto;
import com.example.app_evaluacion_mqtt.Modelo.Ingreso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> implements View.OnClickListener {
    ArrayList<MainActivity.Operaciones> operaciones;
    Context context;
    View.OnClickListener listener;

    public Adapter(ArrayList<MainActivity.Operaciones> operaciones, Context context) {
        this.operaciones = operaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registro,parent,false);
        view.setOnClickListener(this);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.operacion.setText(operaciones.get(position).getTipo());
        holder.fecha.setText(String.valueOf(operaciones.get(position).getFecha()));

        if(operaciones.get(position).getTipo().equals("INGRESO")){
            DBIngreso dbIngreso = new DBIngreso(context);
            ArrayList<Ingreso> ingresos = dbIngreso.cargarRegistros();

            for(int x=0; x<ingresos.size();x++){
                if(ingresos.get(x).getId_ingreso()==operaciones.get(position).getId()){
                    holder.monto.setText(String.valueOf(formatearMontos(ingresos.get(x).getMonto())));
                }
            }
        }else if(operaciones.get(position).getTipo().equals("GASTO")){
            DBGasto dbGasto = new DBGasto(context);
            ArrayList<Gasto> gastos = dbGasto.cargarRegistros();

            for(int x=0; x<gastos.size();x++){
                if(gastos.get(x).getId_gasto()==operaciones.get(position).getId()){
                    holder.monto.setText(String.valueOf(formatearMontos(gastos.get(x).getMonto())));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return operaciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public String formatearMontos(int monto){
        Locale cl = new Locale("es", "CL");
        Currency dollars = Currency.getInstance(cl);
        NumberFormat clpFormat = NumberFormat.getCurrencyInstance(cl);
        return clpFormat.format(monto);
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView operacion,fecha,monto;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            operacion = itemView.findViewById(R.id.operacion);
            fecha=itemView.findViewById(R.id.fecha);
            monto=itemView.findViewById(R.id.monto);

        }
    }

}
