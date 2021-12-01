package com.app_balance.app_evaluacion_mqtt.Modelo;

import java.sql.Timestamp;

public class Gasto {
    private int id_gasto;
    private int id_capital;
    private int monto;
    private Timestamp hora;

    public Gasto(int id_capital, int monto, Timestamp hora) {
        this.id_capital = id_capital;
        this.monto = monto;
        this.hora = hora;
    }

    public Gasto(int id_gasto, int id_capital, int monto, Timestamp hora) {
        this.id_gasto = id_gasto;
        this.id_capital = id_capital;
        this.monto = monto;
        this.hora = hora;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(int id_gasto) {
        this.id_gasto = id_gasto;
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
