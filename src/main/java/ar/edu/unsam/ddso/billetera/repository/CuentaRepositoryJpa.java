package ar.edu.unsam.ddso.billetera.repository;

import ar.edu.unsam.ddso.billetera.model.Cuenta;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile({"local", "prod"})
public interface CuentaRepositoryJpa extends CuentaRepository, JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByAlias(String alias);
}
