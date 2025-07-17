package com.duoc.eft_s9.service;

import com.duoc.eft_s9.interfaces.GeneradorBoleta;
import com.duoc.eft_s9.model.*;
import java.time.LocalDate;

public class BoletaSimple implements GeneradorBoleta {

    @Override
    public void generarBoleta(Vehiculo vehiculo, int dias) {
        double precioUnitario = vehiculo.calcularPrecioFinal();
        double subtotal = precioUnitario * dias;
        double descuento = 0;

        if (dias >= 7) {
            if (vehiculo instanceof VehiculoCarga) {
                descuento = subtotal * DESCUENTO_CARGA;
            } else if (vehiculo instanceof VehiculoPasajeros) {
                descuento = subtotal * DESCUENTO_PASAJEROS;
            }
        }

        double montoConDescuento = subtotal - descuento;
        double iva = montoConDescuento * IVA;
        double total = montoConDescuento + iva;

        String idBoleta = GestorBoletas.generarIdBoleta();
        LocalDate fecha = LocalDate.now();

        BoletaInfo info = new BoletaInfo(
            idBoleta,
            vehiculo.getPatente(),
            vehiculo.getTipoVehiculo().name(),
            dias,
            subtotal,
            descuento,
            iva,
            total,
            fecha
        );

        GestorBoletas.registrarBoleta(info);

        System.out.println("\n===== BOLETA DE ARRIENDO =====");
        System.out.println("ID Boleta: " + idBoleta);
        System.out.println("Fecha emisión: " + fecha);
        System.out.println(vehiculo.toString());
        System.out.println("Días de arriendo: " + dias);
        System.out.printf("Subtotal: %.2f\n", subtotal);
        System.out.printf("Descuento aplicado: %.2f\n", descuento);
        System.out.printf("IVA (%.0f%%): %.2f\n", IVA * 100, iva);
        System.out.printf("TOTAL A PAGAR: %.2f\n", total);
        System.out.println("==============================\n");
    }
}
