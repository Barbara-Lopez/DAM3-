import java.io.Serializable;

/**
 *  Objeto que mando desde el cliente al servidor
 */
public class Mensaje implements Serializable {

    /**
     * nombre de quien envia el mensaje
      */
    private String nombre;
    /**
     * nombre de a que ip se conectara para recibir informacion
     */
    private String ip;
    /**
     * texto del mensaje
      */
    private String texto;

    /**
     * Inicializar el objeto mensage vacío
     */
    public Mensaje() {
    }

    /**
     * Inicializar el objeto mensage con toda la información
     * @param nombre nombre de la persona que envia el mensaje
     * @param ip a que grupo se ha unido
     * @param texto texto que se quiere enviar
     */
    public Mensaje(String nombre, String ip, String texto) {
        this.nombre = nombre;
        this.ip = ip;
        this.texto = texto;
    }

    /**
     *
     * @return retorna el nombre de la persona que envia el mensaje
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre poner el nombre de la persona que envia el mensaje
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return retorna el ip, en nuestro caso es el nombre de la ip que va ha utilizar
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param ip poner el grupo al que se a unido
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @return retorna el texto del mensaje
     */
    public String getTexto() {
        return texto;
    }

    /**
     *
      * @param texto poner el texto que se quiere enviar
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

}
