package ar.edu.unsam.ddso.billetera.repository;

import ar.edu.unsam.ddso.billetera.model.Cuenta;

import java.util.Optional;

public interface CuentaRepository {
    Optional<Cuenta> findByAlias(String alias);
}
