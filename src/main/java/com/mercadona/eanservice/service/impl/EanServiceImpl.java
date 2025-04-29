package com.mercadona.eanservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mercadona.eanservice.constants.EanConstants;
import com.mercadona.eanservice.dto.EanRequestDTO;
import com.mercadona.eanservice.dto.EanResponseDTO;
import com.mercadona.eanservice.entity.EanEntity;
import com.mercadona.eanservice.exception.EanInvalidoException;
import com.mercadona.eanservice.mapper.EanMapper;
import com.mercadona.eanservice.repository.EanRepository;
import com.mercadona.eanservice.service.EanService;

@Service
public class EanServiceImpl implements EanService {

    private final EanRepository eanRepository;
    private final EanMapper mapper;

    public EanServiceImpl(EanRepository eanRepository, EanMapper mapper) {
        this.eanRepository = eanRepository;
        this.mapper = mapper;
    }

    @Override
    public EanResponseDTO crearEan(EanRequestDTO request) {
        validarEan(request.getEan());
        EanEntity entity = mapearEan(request.getEan());
        eanRepository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public EanResponseDTO consultarEan(String ean) {
        EanEntity entity = eanRepository.findById(ean)
                .orElseThrow(() -> new EanInvalidoException(EanConstants.EAN_NO_ENCONTRADO + ean));
        return mapper.toDTO(entity);
    }

    public EanResponseDTO actualizarEan2(String ean, EanRequestDTO request) {
        String eanRequest = request.getEan();

        validarEan(eanRequest);
        eanRepository.findById(ean)
                .orElseThrow(() -> new EanInvalidoException(EanConstants.EAN_NO_ENCONTRADO + ean));

        EanEntity entityActualizado = mapearEan(eanRequest);
        entityActualizado.setEan(eanRequest);
        eanRepository.save(entityActualizado);
        return mapper.toDTO(entityActualizado);
    }

    @Override
    public EanResponseDTO actualizarEan(String eanOriginal, EanRequestDTO request) {
        validarEan(request.getEan());

        EanEntity entityExistente = eanRepository.findById(eanOriginal)
                .orElseThrow(() -> new EanInvalidoException(EanConstants.EAN_NO_ENCONTRADO + eanOriginal));

        // Eliminamos el registro antiguo(falta de tiempo no he creado una tabla de historico)
        eanRepository.delete(entityExistente);

        EanEntity nuevoEntity = mapearEan(request.getEan());
        nuevoEntity.setEan(request.getEan());
        eanRepository.save(nuevoEntity);
        return mapper.toDTO(nuevoEntity);
    }


    @Override
    public void eliminarEan(String ean) {
        EanEntity entity = eanRepository.findById(ean)
                .orElseThrow(() -> new EanInvalidoException(EanConstants.EAN_NO_ENCONTRADO + ean));
        eanRepository.delete(entity);
    }

    @Override
    public List<EanResponseDTO> listarEans() {
        return eanRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validarEan(String ean) {
        if (ean == null || !ean.matches(EanConstants.EAN_REGEX)) {
            throw new EanInvalidoException(EanConstants.EAN_INVALIDO_LONGITUD_O_NUMERICO);
        }
    }

    private EanEntity mapearEan(String ean) {
        String proveedor = ean.substring(0, 7);
        String codigoProducto = ean.substring(7, 12);
        char destinoChar = ean.charAt(12);

        String destino;
        switch (destinoChar) {
            case '1', '2', '3', '4', '5' -> destino = EanConstants.DESTINO_TIENDAS_ESPANA;
            case '6' -> destino = EanConstants.DESTINO_TIENDAS_PORTUGAL;
            case '8' -> destino = EanConstants.DESTINO_ALMACENES;
            case '9' -> destino = EanConstants.DESTINO_OFICINAS;
            case '0' -> destino = EanConstants.DESTINO_COLMENAS;
            default -> throw new EanInvalidoException(EanConstants.DESTINO_INVALIDO + destinoChar);
        }

        return new EanEntity(ean, proveedor, codigoProducto, destino);
    }

}
