package com.example.cleanpedidos.adapter.in.web;

import com.example.cleanpedidos.adapter.in.web.dto.CrearPedidoRequest;
import com.example.cleanpedidos.adapter.in.web.dto.PedidoResponse;
import com.example.cleanpedidos.domain.entity.Pedido;
import com.example.cleanpedidos.domain.valueobject.PedidoId;
import com.example.cleanpedidos.usecase.ConsultarPedidoUseCase;
import com.example.cleanpedidos.usecase.CrearPedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final CrearPedidoUseCase crearUseCase;
    private final ConsultarPedidoUseCase consultarUseCase;

    public PedidoController(CrearPedidoUseCase crearUseCase,
                            ConsultarPedidoUseCase consultarUseCase) {
        this.crearUseCase = crearUseCase;
        this.consultarUseCase = consultarUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> crear(@Valid @RequestBody CrearPedidoRequest req) {
        PedidoId id = crearUseCase.ejecutar(req.clienteNombre(), req.lineas());
        return Map.of("pedidoId", id.toString());
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable String id) {
        Pedido pedido = consultarUseCase.buscarPorId(new PedidoId(UUID.fromString(id)));
        return toResponse(pedido);
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return consultarUseCase.listarTodos().stream()
                .map(this::toResponse)
                .toList();
    }

    // --- Mapper ---
    private PedidoResponse toResponse(Pedido p) {
        List<PedidoResponse.LineaResponse> lineas = p.getLineas().stream()
                .map(l -> new PedidoResponse.LineaResponse(
                        l.productoNombre(),
                        l.cantidad(),
                        l.precioUnitario().cantidad(),
                        l.subtotal().cantidad()
                ))
                .toList();
        return new PedidoResponse(
                p.getId().toString(),
                p.getClienteNombre(),
                p.getEstado().name(),
                p.calcularTotal().cantidad(),
                lineas
        );
    }
}