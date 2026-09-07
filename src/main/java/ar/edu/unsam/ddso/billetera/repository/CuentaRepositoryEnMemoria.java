package ar.edu.unsam.ddso.billetera.repository;

import ar.edu.unsam.ddso.billetera.model.Cuenta;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("test")
public class CuentaRepositoryEnMemoria implements CuentaRepository {

    private final Map<String, Cuenta> cuentasPorAlias = new ConcurrentHashMap<>();

    public CuentaRepositoryEnMemoria() {
        guardar(new Cuenta(1L, "Juan Pérez", "juan.perez", new BigDecimal("15000.00")));
        guardar(new Cuenta(2L, "María Gómez", "maria.gomez", new BigDecimal("8500.50")));
        guardar(new Cuenta(3L, "Carlos Ruiz", "carlos.ruiz", new BigDecimal("3200.00")));
        guardar(new Cuenta(4L, "Ana Torres", "ana.torres", new BigDecimal("50000.00")));
    }

    @Override
    public Optional<Cuenta> findByAlias(String alias) {
        return Optional.ofNullable(cuentasPorAlias.get(alias));
    }

    private void guardar(Cuenta cuenta) {
        cuentasPorAlias.put(cuenta.getAlias(), cuenta);
    }
}
