package com.example.cleanpedidos.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        String id,
        String clienteNombre,
        String estado,
        BigDecimal total,
        List<LineaResponse> lineas
) {
    public record LineaResponse(
            String productoNombre,
            int cantidad,
            BigDecimal precioUnitario,
            BigDecimal subtotal
    ) {}
}