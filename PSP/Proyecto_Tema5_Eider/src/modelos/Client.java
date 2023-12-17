package modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase cliente
 */
public class Client implements Serializable {
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private Usuario user;
    private List<Cuenta> cuentas=new ArrayList<Cuenta>();

    /**
     * Constructor vacio
     */
    public Client() {
    }

    /**
     * Constructor en el que se le pasa el nombre, apellido, edad email y el usuario-> clase usuario
     * @param nombre nombre del cliente
     * @param apellido apellido del cliente
     * @param edad edad del cliente
     * @param email email del cliente
     * @param user usuario del cliente
     */
    public Client(String nombre, String apellido, Integer edad, String email, Usuario user) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.email = email;
        this.user = user;
    }

    /**
     * Retorna el nombre del cliente
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Poner el nombre del cliente
     * @param nombre nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * retorna el apellido del cliente
     * @return apellido del cliente
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Poner el apellido del cliente
     * @param apellido apellido del cliente
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Retorna la edad del cliente
     * @return edad del cliente
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * Poner la edad del cliente
     * @param edad edad del cliente
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * retorna el email del cliente
     * @return  email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Poner el email del cliente
     * @param email email del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * retorna el usuario del cliente
     * @return  usuario del cliente
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * poner el usuario del cliente
     * @param user  usuario del cliente
     */
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * retorna una lista de todas las cuentas del cliente
     * @return lista de todas las cuentas del cliente
     */
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * Poner la lista de cuentas del cliente
     * @param cuentas lista de cuentas del cliente
     */
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * Añadir una cuenta a la lista de cuentas que tiene el cliente
     * @param cuentas cuenta
     */
    public void addCuentas(Cuenta cuentas) {
        this.cuentas.add(cuentas);
    }

    /**
     * Retorna el String del cliente que incluye todos sus datos y las cuentas que tenga
     * @return String del cliente
     */
    @Override
    public String toString() {
        String testo="Client{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", email='" + email + '\'' +
                ", user=" + user.getUsuario() +
                ", cuentas= \n";
        if(cuentas.isEmpty()){
            testo+= "Sin cuentas \n";
        }else{
            for (Cuenta c:cuentas) {
                testo+="Numero cuenta: "+ c.getNumeroCuenta()+", Saldo: "+c.getSaldo()+"\n";
            }
        }
        testo+='}';
        return testo;


    }
}
