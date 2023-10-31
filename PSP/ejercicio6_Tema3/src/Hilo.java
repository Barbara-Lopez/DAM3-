import java.net.Socket;

public class Hilo extends Thread{
    Socket s;

    public Hilo(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        
    }
}
