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
    public static void checkPrice(PrendaModel prenda) {
        prenda.setPrecio(truncate(prenda.getPrecio()));
    }

    // Promociones
    public static void checkDiscount(PromocionModel promo) {
        promo.setDescuento(truncate(promo.getDescuento()));
    }
}
