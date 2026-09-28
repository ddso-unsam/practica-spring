package ar.edu.unsam.ddso.billetera.service;

import ar.edu.unsam.ddso.billetera.dto.MovimientoResponse;
import ar.edu.unsam.ddso.billetera.dto.SaldoResponse;
import ar.edu.unsam.ddso.billetera.exception.CuentaNoEncontradaException;
import ar.edu.unsam.ddso.billetera.model.Cuenta;
import ar.edu.unsam.ddso.billetera.model.Movimiento;
import ar.edu.unsam.ddso.billetera.model.enums.TipoMovimiento;
import ar.edu.unsam.ddso.billetera.repository.CuentaRepository;
import ar.edu.unsam.ddso.billetera.repository.MovimientoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public CuentaService(
            CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    // ---------- Consultas de Cuenta----------

    public SaldoResponse consultarSaldo(String alias) {
        Cuenta cuenta = buscarCuentaPorAlias(alias);
        return new SaldoResponse(cuenta.getAlias(), cuenta.getTitular(), cuenta.getSaldo());
    }

    public List<MovimientoResponse> listarMovimientos(String alias, int limite) {
        Cuenta cuenta = buscarCuentaPorAlias(alias);
        return movimientoRepository
                .findByCuentaOrigenIdOrCuentaDestinoIdOrderByFechaDesc(
                        cuenta.getId(), cuenta.getId())
                .stream()
                .limit(limite)
                .map(
                        m ->
                                new MovimientoResponse(
                                        m.getId(),
                                        m.getTipo().name(),
                                        m.getMonto(),
                                        m.getFecha(),
                                        m.getDescripcion()))
                .toList();
    }

    // ---------- Operaciones sobre Cuenta ----------

    @Transactional
    public MovimientoResponse transferir(
            String aliasOrigen, String aliasDestino, BigDecimal monto, String descripcion) {
        Cuenta origen = buscarCuentaPorAlias(aliasOrigen);
        Cuenta destino = buscarCuentaPorAlias(aliasDestino);

        origen.transferir(destino, monto);

        cuentaRepository.guardar(origen);
        cuentaRepository.guardar(destino);

        Movimiento movimiento =
                new Movimiento(
                        null,
                        origen.getId(),
                        destino.getId(),
                        monto,
                        TipoMovimiento.TRANSFERENCIA,
                        LocalDateTime.now(),
                        descripcion);
        movimientoRepository.save(movimiento);

        return new MovimientoResponse(
                movimiento.getId(),
                movimiento.getTipo().name(),
                movimiento.getMonto(),
                movimiento.getFecha(),
                movimiento.getDescripcion());
    }

    // ---------- Privados ----------

    private Cuenta buscarCuentaPorAlias(String alias) {
        return cuentaRepository
                .findByAlias(alias)
                .orElseThrow(() -> new CuentaNoEncontradaException(alias));
    }
}
