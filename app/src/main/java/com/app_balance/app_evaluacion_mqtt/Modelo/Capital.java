package com.app_balance.app_evaluacion_mqtt.Modelo;

public class Capital {
    private int id_capital,monto;

    public Capital(int id, int monto) {
        this.id_capital = id;
        this.monto = monto;
    }

    public int getId() {
        return id_capital;
    }

    public void setId(int id) {
        this.id_capital = id;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
