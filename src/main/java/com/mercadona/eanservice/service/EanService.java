package com.mercadona.eanservice.service;

import java.util.List;

import com.mercadona.eanservice.dto.EanRequestDTO;
import com.mercadona.eanservice.dto.EanResponseDTO;

public interface EanService {

    EanResponseDTO crearEan(EanRequestDTO request);

    EanResponseDTO consultarEan(String ean);

    EanResponseDTO actualizarEan(String ean, EanRequestDTO request);

    void eliminarEan(String ean);

    List<EanResponseDTO> listarEans();
}