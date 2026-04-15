package com.example.cleanpedidos.adapter.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class PedidoJpaEntity {

    @Id
    private String id;

    private String clienteNombre;
    private String estado;

    @ElementCollection
    @CollectionTable(name = "lineas_pedido", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<LineaPedidoEmbeddable> lineas = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public PedidoJpaEntity() {}

    public PedidoJpaEntity(String id, String clienteNombre, String estado,
                           List<LineaPedidoEmbeddable> lineas) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.estado = estado;
        this.lineas = lineas;
    }

    // Getters
    public String getId() { return id; }
    public String getClienteNombre() { return clienteNombre; }
    public String getEstado() { return estado; }
    public List<LineaPedidoEmbeddable> getLineas() { return lineas; }

    @Embeddable
    public static class LineaPedidoEmbeddable {
        private String productoNombre;
        private int cantidad;
        private BigDecimal precioUnitario;

        public LineaPedidoEmbeddable() {}

        public LineaPedidoEmbeddable(String productoNombre, int cantidad,
                                     BigDecimal precioUnitario) {
            this.productoNombre = productoNombre;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        public String getProductoNombre() { return productoNombre; }
        public int getCantidad() { return cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
    }
}