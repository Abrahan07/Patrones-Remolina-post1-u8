package com.example.cleanpedidos.adapter.out.persistence;

import com.example.cleanpedidos.adapter.out.persistence.PedidoJpaEntity.LineaPedidoEmbeddable;
import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.Dinero;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.port.PedidoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final PedidoJpaRepository jpa;

    public PedidoRepositoryAdapter(PedidoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void guardar(Pedido pedido) {
        jpa.save(toEntity(pedido));
    }

    @Override
    public Optional<Pedido> buscarPorId(PedidoId id) {
        return jpa.findById(id.toString()).map(this::toDomain);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    // --- Mappers ---

    private PedidoJpaEntity toEntity(Pedido p) {
        List<LineaPedidoEmbeddable> lineas = p.getLineas().stream()
                .map(l -> new LineaPedidoEmbeddable(
                        l.productoNombre(),
                        l.cantidad(),
                        l.precioUnitario().cantidad()
                ))
                .toList();
        return new PedidoJpaEntity(
                p.getId().toString(),
                p.getClienteNombre(),
                p.getEstado().name(),
                lineas
        );
    }

    private Pedido toDomain(PedidoJpaEntity e) {
        Pedido pedido = new Pedido(
                new PedidoId(java.util.UUID.fromString(e.getId())),
                e.getClienteNombre()
        );
        e.getLineas().forEach(l -> pedido.agregarLinea(
                l.getProductoNombre(),
                l.getCantidad(),
                new Dinero(l.getPrecioUnitario())
        ));
        // Si estaba CONFIRMADO, lo confirmamos de nuevo
        if ("CONFIRMADO".equals(e.getEstado())) {
            pedido.confirmar();
        }
        return pedido;
    }
}