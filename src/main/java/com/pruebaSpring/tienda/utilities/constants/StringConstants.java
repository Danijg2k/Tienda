package com.pruebaSpring.tienda.utilities.constants;

public abstract class StringConstants {
    // Regex para comprobar formato de 'ref' en prendas
    public static final String regexRef = "^[S|M|L][0-9a-zA-Z]{9}$";
    // Mensaje de la validación del patrón anterior (PrendaModel)
    public static final String regexPatternAdvice = "debe seguir el patrón de referencia";
}
