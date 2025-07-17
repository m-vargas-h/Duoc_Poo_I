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
}
