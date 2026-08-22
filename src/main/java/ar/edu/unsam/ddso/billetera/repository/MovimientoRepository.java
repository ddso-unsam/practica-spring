package ar.edu.unsam.ddso.billetera.repository;

import ar.edu.unsam.ddso.billetera.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaOrigenIdOrCuentaDestinoIdOrderByFechaDesc(
            Long cuentaOrigenId, Long cuentaDestinoId);
}
