package com.example.cleanpedidos.adapter.in.web.dto;

import com.example.cleanpedidos.usecase.LineaPedidoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CrearPedidoRequest(
        @NotBlank(message = "El nombre del cliente es obligatorio")
        String clienteNombre,

        @NotEmpty(message = "El pedido debe tener al menos una línea")
        List<LineaPedidoDto> lineas
) {}