package com.duoc.eft_s9.service;

import com.duoc.eft_s9.interfaces.GeneradorBoleta;
import com.duoc.eft_s9.model.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class BoletaSimple implements GeneradorBoleta {

    @Override
    public void generarBoleta(Vehiculo vehiculo, int dias) {
        if (!vehiculo.isDisponible()) {
            System.out.println("El vehículo ya está en arriendo. No se puede emitir otra boleta.");
            return;
        }

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
        vehiculo.setDisponible(false); // Marcar como no disponible

        // Formato de moneda
        NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "CL"));

        System.out.println("\n-------- BOLETA DE ARRIENDO --------");
        System.out.println("N° Boleta: " + idBoleta);
        System.out.println("Fecha emisión: " + fecha);

        System.out.println("\n----- INFORMACIÓN DEL VEHÍCULO -----");
        System.out.println("Patente: " + vehiculo.getPatente());
        System.out.println("Marca: " + vehiculo.getMarca());
        System.out.println("Año: " + vehiculo.getAnio());
        System.out.println("Tipo: " + vehiculo.getTipoVehiculo().name());

        if (vehiculo instanceof VehiculoCarga carga) {
            System.out.println("Capacidad: " + carga.getCapacidadToneladas() + "t");
        } else if (vehiculo instanceof VehiculoPasajeros pasajeros) {
            System.out.println("Capacidad: " + pasajeros.getCapacidadPasajeros() + "p");
        }

        System.out.println("Días de arriendo: " + dias);

        System.out.println("\n-------------- DETALLE -------------");
        System.out.printf("%-22s %13s%n", "Subtotal:", moneda.format(subtotal));
        System.out.printf("%-22s %13s%n", "Descuento aplicado:", moneda.format(descuento));
        System.out.printf("%-22s %13s%n", "IVA (19%):", moneda.format(iva));
        System.out.printf("%-22s %13s%n", "TOTAL A PAGAR:", moneda.format(total));
        System.out.println("------------------------------------\n");

        //? [DEBUG] verificar estado del vehículo tras emisión de boleta
        ////System.out.println("[DEBUG] Nuevo estado tras boleta: " + (!vehiculo.isDisponible() ? "NO disponible" : "Disponible"));
    }
}
