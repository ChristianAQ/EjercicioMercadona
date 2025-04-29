package com.mercadona.eanservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EanResponseDTO {
    private String ean;
    private String proveedor;
    private String codigoProducto;
    private String destino;
}