
package com.duoc.exp1_s2.utilidades;

/*
 * Clase creada para almacenar y organizar de mejor forma algunos métodos auxiliares como por ejemplo
 * aquellos de dan formato a ciertos registros, asi como algunos menus.
 */

public class Auxiliares {

    public static void mostrarMenu() {
        System.out.println("\n---- Menú Principal ----");
        System.out.println("1. Registrar Cliente");
        System.out.println("2. Ver Datos de Cliente");
        System.out.println("3. Depositar");
        System.out.println("4. Girar");
        System.out.println("5. Consultar Saldo");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");
    }

    public static void menuCuentas() {
        System.out.println("\n----- Selección de cuenta -----");
        System.out.println("1. Cuenta Vista");
        System.out.println("2. Cuenta Ahorro");
        System.out.println("3. Cuenta Corriente");
        System.out.println("Seleccione una de las opciones: ");
    }

    public static String formatearTelefono(String numero) {
        numero = numero.replaceAll("[^0-9]", ""); // Eliminamos caracteres no numéricos

        if (!numero.matches("\\d{8}")) {
            System.out.println("Error: El número debe contener exactamente 8 dígitos.");
            return null; // Devuelve `null` si el formato es incorrecto
        }

        return "+569" + numero; // Agrega el prefijo
    }
    
    public static String formatearRut(String rut) {
        int dashIndex = rut.indexOf('-');
        // Si no encuentra el guion, retorna el RUT tal como está
        if (dashIndex == -1) {
            return rut;
        }
        
        // Separa la parte numérica y el dígito verificador (incluye el guion)
        String numero = rut.substring(0, dashIndex);
        String digitoVerificador = rut.substring(dashIndex); // incluye el guión
        
        StringBuilder formateado = new StringBuilder();
        int contador = 0;
        // Recorre la parte numérica de derecha a izquierda
        for (int i = numero.length() - 1; i >= 0; i--) {
            formateado.insert(0, numero.charAt(i));
            contador++;
            // Cada grupo de tres dígitos se separa, si quedan dígitos por procesar
            if (contador == 3 && i != 0) {
                formateado.insert(0, '.');
                contador = 0;
            }
        }
        
        return formateado.toString() + digitoVerificador;
    }
}
