

public class Main {
    public static void main(String[] args) {
        Hilo hiloNum= new Hilo(); // esto es un hilo
        HiloLetra hiloL = new HiloLetra(); // esto es un hilo
        HiloRunnable hiloR = new HiloRunnable(); // no es un hilo
        Thread hiloRun = new Thread(hiloR); // esto si que es un hilo


        hiloNum.start();
        hiloL.start();
        hiloRun.start();

    }
}