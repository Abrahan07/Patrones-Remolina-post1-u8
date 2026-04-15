package com.example.cleanpedidos.usecase;

import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.PedidoId;

import java.util.List;

public interface ConsultarPedidoUseCase {
    Pedido buscarPorId(PedidoId id);
    List<Pedido> listarTodos();
}