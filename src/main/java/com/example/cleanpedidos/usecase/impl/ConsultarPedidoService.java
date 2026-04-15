package com.example.cleanpedidos.usecase.impl;

import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.ConsultarPedidoUseCase;
import com.example.cleanpedidos.usecase.port.PedidoRepositoryPort;

import java.util.List;

public class ConsultarPedidoService implements ConsultarPedidoUseCase {

    private final PedidoRepositoryPort repo;

    public ConsultarPedidoService(PedidoRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    public Pedido buscarPorId(PedidoId id) {
        return repo.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return repo.buscarTodos();
    }
}