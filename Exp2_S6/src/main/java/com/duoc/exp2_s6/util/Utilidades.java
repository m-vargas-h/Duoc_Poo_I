package com.duoc.exp2_s6.util;

/**
 * Clase con métodos auxiliares estáticos (String, validaciones, etc.).
 */
public final class Utilidades {

    private Utilidades() {
        // constructor privado para evitar instanciación
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
        // El cuerpo solo dígitos
        if (!cuerpo.matches("\\d+")) return false;
        // Dígito verificador: un carácter, dígito o 'K'/'k'
        return dv.matches("[0-9Kk]");
    }

    /**
     * Valida un email genérico:
     * - parte local: letras, dígitos, . _ % + -
     * - @
     * - dominio: letras, dígitos, . -
     * - . y TLD de 2 o más letras (acepta .cl, .com, .org, .io, etc.)
     */
    public static boolean validarEmail(String email) {
        if (email == null) return false;
        email = email.trim();
        if (email.isEmpty()) return false;
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }


    // TODO: agregar aquí otros métodos auxiliares
}