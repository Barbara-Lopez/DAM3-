public class compartida {
    public int contador;
    public synchronized void sumar(){
        contador = contador+1;
    }
}
