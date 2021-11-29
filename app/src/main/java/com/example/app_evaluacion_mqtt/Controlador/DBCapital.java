package com.example.app_evaluacion_mqtt.Controlador;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_evaluacion_mqtt.Modelo.Capital;

import java.util.ArrayList;


public class DBCapital extends DBHelper{
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBCapital(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public void insertarDatos(int monto){
        ContentValues values = new ContentValues();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        values.put("monto",monto);
        this.db.insert(TABLA_CAPITAL,null,values);
    }
    public void eliminarRegistro(int id){
        Capital capital = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLA_CAPITAL+" WHERE id="+id);
    }

    public void actualizarRegistro(int id,int monto){
        Capital capital = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("UPDATE "+TABLA_CAPITAL+" SET monto="+monto+" WHERE id_capital="+capital.getId());
    }

    @SuppressLint("Range")
    public ArrayList<Capital> cargarRegistros(){
        ArrayList<Capital> registrosCapital = new ArrayList<>();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getReadableDatabase();

        Cursor cursorRegistros = db.rawQuery("SELECT * FROM "+TABLA_CAPITAL,null);
        if(cursorRegistros!=null) {
            if (cursorRegistros.moveToFirst()) {

                do {
                    Capital capital = new Capital(Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("id_capital"))),
                            Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("monto"))));
                    registrosCapital.add(capital);
                } while (cursorRegistros.moveToNext());
            }
        }
        return registrosCapital;
    }

    public Capital cargarRegistro(int id){
        Capital capital;
        ArrayList<Capital> registrosCapital = cargarRegistros();
        for(int x=0;x<registrosCapital.size();x++){
            if(registrosCapital.get(x).getId()==id){
                capital= new Capital(registrosCapital.get(x).getId(),registrosCapital.get(x).getMonto());
                return capital;
            }
        }
        return null;
    }

}
