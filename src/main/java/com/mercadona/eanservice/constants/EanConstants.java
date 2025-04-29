package com.mercadona.eanservice.constants;

public class EanConstants {

    private EanConstants() {
    }

    public static final String EAN_REGEX = "\\d{13}";
    public static final String EAN_INVALIDO_LONGITUD_O_NUMERICO = "El EAN debe tener una longitud de 13 caracteres numéricos";

    public static final String DESTINO_TIENDAS_ESPANA = "Tiendas Mercadona España";
    public static final String DESTINO_TIENDAS_PORTUGAL = "Tiendas Mercadona Portugal";
    public static final String DESTINO_ALMACENES = "Almacenes";
    public static final String DESTINO_OFICINAS = "Oficinas Mercadona";
    public static final String DESTINO_COLMENAS = "Colmenas";

    public static final String EAN_NO_ENCONTRADO = "EAN no encontrado: ";
    public static final String DESTINO_INVALIDO ="Destino no válido para el dígito: ";

}
