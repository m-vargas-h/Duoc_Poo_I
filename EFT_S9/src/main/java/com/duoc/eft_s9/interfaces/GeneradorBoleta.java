package com.duoc.eft_s9.interfaces;

import com.duoc.eft_s9.model.Vehiculo;

public interface GeneradorBoleta {
    double IVA = 0.19;
    double DESCUENTO_CARGA = 0.12;
    double DESCUENTO_PASAJEROS = 0.08;

    void generarBoleta(Vehiculo vehiculo, int dias);
}