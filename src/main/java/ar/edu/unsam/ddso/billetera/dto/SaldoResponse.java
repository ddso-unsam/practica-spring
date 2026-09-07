package ar.edu.unsam.ddso.billetera.dto;

import java.math.BigDecimal;

public record SaldoResponse(String alias, String titular, BigDecimal saldo) {}
