package com.mercadona.eanservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EAN")
public class EanEntity {

    @Id
    private String ean;  // El EAN completo de 13 dígitos.

    private String proveedor;
    private String codigoProducto;
    private String destino;
}
