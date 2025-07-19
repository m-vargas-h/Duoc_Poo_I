package com.duoc.eft_s9.utils;

public class ValidadorFormato {
    private static final String FORMATO_PATENTE = "^(?:[A-Z]{2}-\\d{4}|[A-Z]{4}-\\d{2})$";

    public static String validarPatente(String entrada) {
        if (entrada == null || entrada.isBlank()) return null;
        String limpia = entrada.trim().toUpperCase();

        if (!limpia.matches(FORMATO_PATENTE)) {
            System.out.println("Formato inválido. Debe ser AB-1234 o ABCD-12.");
            return null;
        }
        return limpia;
    }

        /**
     * Valida que el RUT:
     *  - No sea null ni vacío
     *  - No contenga puntos
     *  - Tenga un guion separando cuerpo y dígito/verificador
     *  - El cuerpo solo tenga dígitos
     *  - El dígito/verificador sea un solo carácter [0-9Kk]
     *
     * @param rut RUT en formato “12345678-9” o “87654321-K”
     * @return true si el formato es válido
     */
    public static boolean validarFormatoRut(String rut) {
        if (rut == null) return false;
        rut = rut.trim();
        if (rut.isEmpty() || rut.contains(".")) return false;

        String[] partes = rut.split("-");
        if (partes.length != 2) return false;

        String cuerpo = partes[0];
        String dv     = partes[1];

        return cuerpo.matches("\\d+") && dv.matches("[0-9Kk]");
    }
}
