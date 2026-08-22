package ar.edu.unsam.ddso.billetera.service;

import ar.edu.unsam.ddso.billetera.dto.MovimientoResponse;
import ar.edu.unsam.ddso.billetera.dto.SaldoResponse;
import ar.edu.unsam.ddso.billetera.exception.*;
import ar.edu.unsam.ddso.billetera.dto.*;
import ar.edu.unsam.ddso.billetera.exception.*;
import ar.edu.unsam.ddso.billetera.model.Cuenta;
import ar.edu.unsam.ddso.billetera.repository.CuentaRepository;
import ar.edu.unsam.ddso.billetera.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public CuentaService(CuentaRepository cuentaRepository,
                         MovimientoRepository movimientoRepository) {
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
                .findByCuentaOrigenIdOrCuentaDestinoIdOrderByFechaDesc(cuenta.getId(), cuenta.getId())
                .stream()
                .limit(limite)
                .map(m -> new MovimientoResponse(m.getId(), m.getTipo().name(), m.getMonto(), m.getFecha(), m.getDescripcion()))
                .toList();
    }

    // ---------- Privados ----------

    private Cuenta buscarCuentaPorAlias(String alias) {
        return cuentaRepository.findByAlias(alias)
                .orElseThrow(() -> new CuentaNoEncontradaException(alias));
    }
}