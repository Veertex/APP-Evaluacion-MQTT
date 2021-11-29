package com.example.app_evaluacion_mqtt.Controlador;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import com.example.app_evaluacion_mqtt.Modelo.Ingreso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;


public class DBIngreso extends DBHelper {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBIngreso(@Nullable Context context) {
        super(context);
        this.context=context;

    }
    public void insertarDatos(int id_capital,int monto){
        ContentValues values = new ContentValues();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        values.put("id_capital",id_capital);
        values.put("monto",monto);
        this.db.insert(TABLA_INGRESO,null,values);
    }
    public void eliminarRegistro(int id){
        Ingreso ingreso = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLA_INGRESO+" WHERE id_ingreso="+ingreso.getId_ingreso());
    }

    public void actualizarRegistro(int id,int monto){
        Ingreso ingreso = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("UPDATE "+TABLA_INGRESO+" SET monto="+monto+" WHERE id_ingreso="+ingreso.getId_ingreso());
    }

    @SuppressLint("Range")
    public ArrayList<Ingreso> cargarRegistros(){
        ArrayList<Ingreso> registroIngresos = new ArrayList<>();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getReadableDatabase();

        Cursor cursorRegistros = db.rawQuery("SELECT * FROM "+TABLA_INGRESO,null);
        if(cursorRegistros!=null) {
            if (cursorRegistros.moveToFirst()) {

                do {
                    Ingreso ingreso = new Ingreso(Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("id_ingreso"))),
                            Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("id_capital"))),
                            Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("monto"))),
                            Timestamp.valueOf(cursorRegistros.getString(cursorRegistros.getColumnIndex("hora"))));

                    registroIngresos.add(ingreso);
                } while (cursorRegistros.moveToNext());
            }
        }
        return registroIngresos;
    }

    public Ingreso cargarRegistro(int id){
        Ingreso ingreso;
        ArrayList<Ingreso> registroIngreso = cargarRegistros();
        for(int x=0;x<registroIngreso.size();x++){
            if(registroIngreso.get(x).getId_ingreso()==id){
                ingreso= new Ingreso(registroIngreso.get(x).getId_ingreso(),registroIngreso.get(x).getId_capital(),registroIngreso.get(x).getMonto(),registroIngreso.get(x).getHora());
                return ingreso;
            }
        }
        return null;
    }

}
