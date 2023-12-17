package modelos;

import java.io.Serializable;

/**
 * Clase Usuario del cliente
 */
public class Usuario implements Serializable {
    private String usuario;
    private byte[] contrasena;

    /**
     * Constructor vacio
     */
    public Usuario() {
    }

    /**
     * Constructor con el usuario y contraseña
     * @param usuario nombre del usuario
     * @param contrasena contraseña
     */
    public Usuario(String usuario, byte[] contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    /**
     * retorna el nombre del usuario
     * @return nombre del usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Poner el nombre del usuario
     * @param usuario  nombre del usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * retorna la contraseña del usuario haseada
     * @return  contraseña del usuario haseada
     */
    public byte[] getContrasena() {
        return contrasena;
    }

    /**
     * Poner la contraseña haseada
     * @param contrasena  contraseña haseada
     */
    public void setContrasena(byte[] contrasena) {
        this.contrasena = contrasena;
    }
}
