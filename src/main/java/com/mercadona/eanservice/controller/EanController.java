package com.mercadona.eanservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadona.eanservice.dto.EanRequestDTO;
import com.mercadona.eanservice.dto.EanResponseDTO;
import com.mercadona.eanservice.service.EanService;

@RestController
@RequestMapping("/api/ean")
public class EanController {

    @Autowired
    private EanService eanService;

    @PostMapping
    public ResponseEntity<EanResponseDTO> crear(@RequestBody EanRequestDTO request) {
        return ResponseEntity.ok(eanService.crearEan(request));
    }

    @GetMapping("/{ean}")
    public ResponseEntity<EanResponseDTO> consultar(@PathVariable String ean) {
        return ResponseEntity.ok(eanService.consultarEan(ean));
    }

    @PutMapping("/{ean}")
    public ResponseEntity<EanResponseDTO> actualizar(@PathVariable String ean, @RequestBody EanRequestDTO request) {
        return ResponseEntity.ok(eanService.actualizarEan(ean, request));
    }

    @DeleteMapping("/{ean}")
    public ResponseEntity<Void> eliminar(@PathVariable String ean) {
        eanService.eliminarEan(ean);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EanResponseDTO>> listar() {
        return ResponseEntity.ok(eanService.listarEans());
    }
}