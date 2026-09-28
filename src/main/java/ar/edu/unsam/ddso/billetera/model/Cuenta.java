package ar.edu.unsam.ddso.billetera.model;

import ar.edu.unsam.ddso.billetera.model.exceptions.TransferenciaException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titular;

    @Column(nullable = false, unique = true)
    private String alias;

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false)
    private boolean activa = true;

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public void transferir(Cuenta destino, BigDecimal monto) {

        if (!this.activa) {
            throw new TransferenciaException("La cuenta de origen no esta activa");
        }

        if (!destino.activa) {
            throw new TransferenciaException("La cuenta de destino no esta activa");
        }

        if (this.getSaldo().compareTo(monto) < 0) {
            throw new TransferenciaException("Fondos insuficioentes");
        }

        destino.setSaldo(destino.getSaldo().add(monto));
        this.saldo = this.saldo.subtract(monto);
    }
}
