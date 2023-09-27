public class hilocontador extends Thread{
    private int num;
    private compartida c;

    public hilocontador(compartida c) {
        this.c = c;
    }

    @Override
    public void run() {
        int numero = 0;
        for (int i = 0; i < 5000; i++) {

               c.sumar();


        }
        num += numero;
    }
    public void resultadoContador(){
        System.out.println("El resultado del contador es: "+ c.contador);
    }
}
