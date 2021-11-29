package com.example.app_evaluacion_mqtt.Controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION_BD=1;
    private static final String NOMBRE_BD="registros.db";
    public static final String TABLA_INGRESO="tabla_ingreso";
    public static final String TABLA_GASTO="tabla_gasto";
    public static final String TABLA_CAPITAL="tabla_capital";

    public DBHelper(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+TABLA_CAPITAL+"("+"id_capital INTEGER PRIMARY KEY AUTOINCREMENT," +
                "monto INTEGER NOT NULL"+")");

        db.execSQL("CREATE TABLE "+TABLA_INGRESO+"("+"id_ingreso INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_capital INTEGER NOT NULL,"+"monto INTEGER NOT NULL,"+"hora DEFAULT CURRENT_TIMESTAMP"+")");

        db.execSQL("CREATE TABLE "+TABLA_GASTO+"("+"id_gasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_capital INTEGER NOT NULL,"+"monto INTEGER NOT NULL,"+"hora DEFAULT CURRENT_TIMESTAMP"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLA_CAPITAL);
        db.execSQL("DROP TABLE "+TABLA_INGRESO);
        db.execSQL("DROP TABLE "+TABLA_GASTO);

    }
}
