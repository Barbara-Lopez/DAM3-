import ejercicio1.Banos;
import ejercicio1.irBano;
import ejercicio1.salirBano;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Banos b = new Banos(0,0);
        irBano s[]=new irBano[10];
        for (int i = 0; i < 10; i++) {
            s[i]= new irBano(b);
            s[i].start();
            s[i].setName("persona"+i);
        }

        salirBano b=new salirBano(b);
        b.setName("bajar");
        b.start();
    }
}