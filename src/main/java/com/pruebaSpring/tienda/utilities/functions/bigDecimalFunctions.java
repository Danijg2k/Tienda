package com.pruebaSpring.tienda.utilities.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.pruebaSpring.tienda.models.PrendaModel;
import com.pruebaSpring.tienda.models.PromocionModel;

public class bigDecimalFunctions {
    // Truncate BigDecimal
    private static BigDecimal truncate(BigDecimal bd) {
        return bd.setScale(2, RoundingMode.DOWN);
    }

    // Prendas
    public static void truncatePrice(PrendaModel prenda) {
        prenda.setPrecio(truncate(prenda.getPrecio()));
    }

    public static void truncatePromPrice(PrendaModel prenda) {
        prenda.setPrecio_promocionado(truncate(prenda.getPrecio_promocionado()));
    }

    // Promociones
    public static void truncateDiscount(PromocionModel promo) {
        promo.setDescuento(truncate(promo.getDescuento()));
    }
}
