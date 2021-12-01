package com.app_balance.app_evaluacion_mqtt.Modelo;

import java.sql.Timestamp;
import java.util.Date;

public class Ingreso {
    private int id_ingreso;
    private int id_capital;
    private int monto;
    private Timestamp hora;

    public Ingreso(int id_ingreso, int id_capital, int monto, Timestamp hora) {
        this.id_ingreso = id_ingreso;
        this.id_capital = id_capital;
        this.monto = monto;
        this.hora = hora;
    }

    public Ingreso(int id_capital, int monto) {
        this.id_capital = id_capital;
        this.monto = monto;
    }

    public int getId_ingreso() {
        return id_ingreso;
    }

    public void setId_ingreso(int id_ingreso) {
        this.id_ingreso = id_ingreso;
    }

    public int getId_capital() {
        return id_capital;
    }

    public void setId_capital(int id_capital) {
        this.id_capital = id_capital;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }
}
