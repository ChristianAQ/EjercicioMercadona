package com.mercadona.eanservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.mercadona.eanservice.constants.EanConstants;
import com.mercadona.eanservice.dto.EanRequestDTO;
import com.mercadona.eanservice.dto.EanResponseDTO;
import com.mercadona.eanservice.entity.EanEntity;
import com.mercadona.eanservice.exception.EanInvalidoException;
import com.mercadona.eanservice.mapper.EanMapper;
import com.mercadona.eanservice.repository.EanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EanServiceImplTest {

    @Mock
    private EanRepository eanRepository;

    private EanMapper mapper;

    private EanServiceImpl eanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = Mappers.getMapper(EanMapper.class);
        eanService = new EanServiceImpl(eanRepository, mapper);
    }

    @Test
    void testCrearEan_Correcto() {
        String eanCodigo = "1234567123451";
        EanRequestDTO request = new EanRequestDTO(eanCodigo);

        when(eanRepository.save(any(EanEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EanResponseDTO result = eanService.crearEan(request);

        assertEquals(eanCodigo, result.getEan());
        assertEquals("1234567", result.getProveedor());
        assertEquals("12345", result.getCodigoProducto());
        assertEquals(EanConstants.DESTINO_TIENDAS_ESPANA, result.getDestino());
        verify(eanRepository, times(1)).save(any(EanEntity.class));
    }

    @Test
    void testCrearEan_EanInvalido() {
        EanRequestDTO request = new EanRequestDTO("123"); // Código inválido

        EanInvalidoException exception = assertThrows(EanInvalidoException.class, () -> {
            eanService.crearEan(request);
        });

        assertEquals(EanConstants.EAN_INVALIDO_LONGITUD_O_NUMERICO, exception.getMessage());
    }

    @Test
    void testConsultarEan_Existente() {
        String eanCodigo = "1234567123451";
        EanEntity entity = new EanEntity(eanCodigo, "1234567", "12345", EanConstants.DESTINO_TIENDAS_ESPANA);

        when(eanRepository.findById(eanCodigo)).thenReturn(Optional.of(entity));

        EanResponseDTO result = eanService.consultarEan(eanCodigo);

        assertEquals(eanCodigo, result.getEan());
        assertEquals("1234567", result.getProveedor());
        assertEquals("12345", result.getCodigoProducto());
        assertEquals(EanConstants.DESTINO_TIENDAS_ESPANA, result.getDestino());
    }

    @Test
    void testConsultarEan_NoExistente() {
        String eanCodigo = "1234567123451";

        when(eanRepository.findById(eanCodigo)).thenReturn(Optional.empty());

        assertThrows(EanInvalidoException.class, () -> eanService.consultarEan(eanCodigo));
    }

    @Test
    void testActualizarEan_Correcto() {
        String eanCodigoOriginal = "1234567123451";
        String eanCodigoNuevo = "1234567123461";

        EanRequestDTO request = new EanRequestDTO(eanCodigoNuevo);
        EanEntity entityExistente = new EanEntity(eanCodigoOriginal, "1234567", "12345", EanConstants.DESTINO_TIENDAS_ESPANA);

        when(eanRepository.findById(eanCodigoOriginal)).thenReturn(Optional.of(entityExistente));
        when(eanRepository.save(any(EanEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EanResponseDTO result = eanService.actualizarEan(eanCodigoOriginal, request);

        assertEquals(eanCodigoNuevo, result.getEan());
        verify(eanRepository, times(1)).save(any(EanEntity.class));
    }

    @Test
    void testActualizarEan_NoExistente() {
        String eanCodigo = "1234567123451";
        EanRequestDTO request = new EanRequestDTO("1234567123461");

        when(eanRepository.findById(eanCodigo)).thenReturn(Optional.empty());

        assertThrows(EanInvalidoException.class, () -> eanService.actualizarEan(eanCodigo, request));
    }

    @Test
    void testEliminarEan_Correcto() {
        String eanCodigo = "1234567123451";
        EanEntity entity = new EanEntity(eanCodigo, "1234567", "12345", EanConstants.DESTINO_TIENDAS_ESPANA);

        when(eanRepository.findById(eanCodigo)).thenReturn(Optional.of(entity));

        eanService.eliminarEan(eanCodigo);

        verify(eanRepository, times(1)).delete(entity);
    }

    @Test
    void testEliminarEan_NoExistente() {
        String eanCodigo = "1234567123451";

        when(eanRepository.findById(eanCodigo)).thenReturn(Optional.empty());

        assertThrows(EanInvalidoException.class, () -> eanService.eliminarEan(eanCodigo));
    }

    @Test
    void testListarEans() {
        EanEntity entity = new EanEntity("1234567123451", "1234567", "12345", EanConstants.DESTINO_TIENDAS_ESPANA);

        when(eanRepository.findAll()).thenReturn(List.of(entity));

        List<EanResponseDTO> result = eanService.listarEans();

        assertEquals(1, result.size());
        assertEquals("1234567123451", result.get(0).getEan());
    }
}
