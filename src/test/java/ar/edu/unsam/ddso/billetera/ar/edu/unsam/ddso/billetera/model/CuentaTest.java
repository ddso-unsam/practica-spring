package ar.edu.unsam.ddso.billetera.ar.edu.unsam.ddso.billetera.model;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.unsam.ddso.billetera.model.Cuenta;
import ar.edu.unsam.ddso.billetera.model.exceptions.TransferenciaException;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CuentaTest {

    @Test
    public void transaferenciaOk() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));

        // Accion
        origen.transferir(destino, new BigDecimal(1));

        // Post condidicon
        assertEquals(new BigDecimal(3), destino.getSaldo());
        assertEquals(new BigDecimal(13), origen.getSaldo());
    }

    @Test
    public void transaferenciaOkSaldoJusto() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));

        // Accion
        origen.transferir(destino, new BigDecimal(14));

        // Post condidicon
        assertEquals(new BigDecimal(0), origen.getSaldo());
    }

    @Test
    public void transaferenciaFondosInsufPoquito() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));

        // Accion // Post condidicon
        assertThrows(
                TransferenciaException.class,
                () -> origen.transferir(destino, new BigDecimal(14.0001)));
    }

    @Test
    public void transaferenciaFondosInsuf() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));

        // Accion // Post condidicon
        assertThrows(
                TransferenciaException.class,
                () -> origen.transferir(destino, new BigDecimal(100)));
    }

    @Test
    public void transaferenciaConCuentaOrigenInactivaFalla() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        origen.setActiva(false);
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));

        // Accion // Post condidicon
        assertThrows(
                TransferenciaException.class, () -> origen.transferir(destino, new BigDecimal(1)));
    }

    @Test
    public void transaferenciaConCuentaDestinoInactivaFalla() {
        // Pre condicion
        Cuenta origen = new Cuenta();
        origen.setSaldo(new BigDecimal(14));
        Cuenta destino = new Cuenta();
        destino.setSaldo(new BigDecimal(2));
        destino.setActiva(false);

        // Accion // Post condidicon
        assertThrows(
                TransferenciaException.class, () -> origen.transferir(destino, new BigDecimal(1)));
    }
}
