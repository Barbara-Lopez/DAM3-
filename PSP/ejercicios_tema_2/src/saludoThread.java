public class saludoThread extends Thread{
    @Override
    public void run() {
        System.out.println("Se ejecuta desde el hilo: "+Thread.currentThread().getName());
        System.out.println("hola mundo");
    }
}
