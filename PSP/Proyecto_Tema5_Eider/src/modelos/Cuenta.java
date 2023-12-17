package modelos;

import java.io.Serializable;

/**
 * Clase de la cuenta del cliente
 */
public class Cuenta implements Serializable {
    private String numeroCuenta;
    private Float saldo;

    /**
     * Constructor vacio
     */
    public Cuenta() {
    }

    /**
     * Constructor en el que le pasas el número y el saldo
     * @param numeroCuenta número
     * @param saldo saldo
     */
    public Cuenta(String numeroCuenta, Float saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    /**
     * Retorna el número de la cuenta
     * @return número de la cuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Poner el número de la cuenta
     * @param numeroCuenta  número de la cuenta
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * retorna el saldo de la cuenta
     * @return saldo de la cuenta
     */
    public Float getSaldo() {
        return saldo;
    }

    /**
     * Pone el saldo de la cuenta
     * @param saldo saldo de la cuenta
     */
    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    /**
     * Retorna el String de la cuenta
     * @return String de la cuenta
     */
    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
