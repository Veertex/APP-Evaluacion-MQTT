package com.app_balance.app_evaluacion_mqtt.Controlador;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.app_balance.app_evaluacion_mqtt.Modelo.Gasto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DBGasto extends DBHelper{
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    public DBGasto(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public void insertarDatos(int id_capital,int monto){
        ContentValues values = new ContentValues();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        values.put("id_capital",id_capital);
        values.put("monto",monto);
        this.db.insert(TABLA_GASTO,null,values);
    }
    public void eliminarRegistro(int id){
        Gasto gasto = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLA_GASTO+" WHERE id_gasto="+gasto.getId_gasto());
    }

    public void actualizarRegistro(int id,int monto){
        Gasto gasto = cargarRegistro(id);
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getWritableDatabase();
        db.execSQL("UPDATE "+TABLA_GASTO+" SET monto="+monto+" WHERE id_gasto="+gasto.getId_gasto());
    }

    @SuppressLint("Range")
    public ArrayList<Gasto> cargarRegistros(){
        ArrayList<Gasto> registroGastos = new ArrayList<>();
        this.dbHelper= new DBHelper(context);
        this.db=dbHelper.getReadableDatabase();

        Cursor cursorRegistros = db.rawQuery("SELECT * FROM "+TABLA_GASTO,null);
        if(cursorRegistros!=null) {
            if (cursorRegistros.moveToFirst()) {

                do {
                    Gasto gasto = new Gasto(Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("id_gasto"))),
                            Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("id_capital"))),
                            Integer.parseInt(cursorRegistros.getString(cursorRegistros.getColumnIndex("monto"))),
                            Timestamp.valueOf(cursorRegistros.getString(cursorRegistros.getColumnIndex("hora"))));

                    registroGastos.add(gasto);
                } while (cursorRegistros.moveToNext());
            }
        }
        return registroGastos;
    }

    public Gasto cargarRegistro(int id){
        Gasto gasto;
        ArrayList<Gasto> registroGasto = cargarRegistros();
        for(int x=0;x<registroGasto.size();x++){
            if(registroGasto.get(x).getId_gasto()==id){
                gasto= new Gasto(registroGasto.get(x).getId_gasto(),registroGasto.get(x).getId_capital(),registroGasto.get(x).getMonto(),registroGasto.get(x).getHora());
                return gasto;
            }
        }
        return null;
    }


}
