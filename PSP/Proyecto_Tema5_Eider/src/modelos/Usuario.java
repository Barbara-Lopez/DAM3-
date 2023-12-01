package modelos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String usuario;
    private byte[] contrasena;

    public Usuario() {
    }

    public Usuario(String usuario, byte[] contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public byte[] getContrasena() {
        return contrasena;
    }

    public void setContrasena(byte[] contrasena) {
        this.contrasena = contrasena;
    }
}
