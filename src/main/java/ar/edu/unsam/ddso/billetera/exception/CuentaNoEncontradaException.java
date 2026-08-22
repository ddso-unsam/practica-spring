package ar.edu.unsam.ddso.billetera.exception;

public class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(String alias) {
        super("No existe una cuenta con alias: " + alias);
    }
}