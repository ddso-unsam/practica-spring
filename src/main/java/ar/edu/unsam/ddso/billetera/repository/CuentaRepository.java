package ar.edu.unsam.ddso.billetera.repository;

import ar.edu.unsam.ddso.billetera.model.Cuenta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByAlias(String alias);
}
