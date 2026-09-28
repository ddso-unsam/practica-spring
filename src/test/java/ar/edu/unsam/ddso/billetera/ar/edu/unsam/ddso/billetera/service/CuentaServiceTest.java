package ar.edu.unsam.ddso.billetera.ar.edu.unsam.ddso.billetera.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ar.edu.unsam.ddso.billetera.dto.SaldoResponse;
import ar.edu.unsam.ddso.billetera.exception.CuentaNoEncontradaException;
import ar.edu.unsam.ddso.billetera.model.Cuenta;
import ar.edu.unsam.ddso.billetera.model.Movimiento;
import ar.edu.unsam.ddso.billetera.repository.CuentaRepository;
import ar.edu.unsam.ddso.billetera.repository.MovimientoRepository;
import ar.edu.unsam.ddso.billetera.service.CuentaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {

    @Mock CuentaRepository repoCuenta;
    @Mock MovimientoRepository movimientoRepository;

    @Test
    public void consultarSaldoOk() {
        String nombreCuenta = "Eze";
        Cuenta cuenta = new Cuenta();
        cuenta.setId(13L);
        cuenta.setSaldo(new BigDecimal(88));
        cuenta.setAlias(nombreCuenta);

        when(repoCuenta.findByAlias(nombreCuenta)).thenReturn(Optional.of(cuenta));

        CuentaService service = new CuentaService(repoCuenta, movimientoRepository);

        SaldoResponse saldoResponse = service.consultarSaldo(nombreCuenta);

        assertEquals(cuenta.getSaldo(), saldoResponse.saldo());
    }

    @Test
    public void consultarSaldoAliasInexistente() {
        String aliasInexistente = "no.existe";
        when(repoCuenta.findByAlias(aliasInexistente)).thenReturn(Optional.empty());

        CuentaService service = new CuentaService(repoCuenta, null);

        assertThrows(
                CuentaNoEncontradaException.class, () -> service.consultarSaldo(aliasInexistente));
    }

    @Test
    public void transferenciaOk() {
        String nombreCuenta1 = "Eze";
        Cuenta cuenta = new Cuenta();
        cuenta.setId(13L);
        cuenta.setSaldo(new BigDecimal(88));
        cuenta.setAlias(nombreCuenta1);
        when(repoCuenta.findByAlias(nombreCuenta1)).thenReturn(Optional.of(cuenta));

        String nombreCuenta2 = "Eze2";
        cuenta = new Cuenta();
        cuenta.setId(13L);
        cuenta.setSaldo(new BigDecimal(88));
        cuenta.setAlias(nombreCuenta2);
        when(repoCuenta.findByAlias(nombreCuenta2)).thenReturn(Optional.of(cuenta));

        CuentaService service = new CuentaService(repoCuenta, movimientoRepository);

        service.transferir(nombreCuenta1, nombreCuenta2, new BigDecimal(10), "holoa que tal");

        verify(repoCuenta).guardar(cuenta);

        verify(movimientoRepository).save(any(Movimiento.class));
    }
}
