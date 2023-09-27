public class hilo implements Runnable{
    String text;

    public hilo(String testo) {text = testo; }


    public void run() {
        Thread.currentThread().setName("Hilo1");
        for (int i = 0; i < 10; i++) {
            System.out.println(text);
        }
    }

}
