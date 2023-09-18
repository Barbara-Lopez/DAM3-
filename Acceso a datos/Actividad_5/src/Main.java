import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    final static String BDPer = "EMPLEDEP.yap";
    public static void main(String[] args) {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        Scanner lectura = new Scanner (System.in);
        System.out.println("Elija que quiere gestionar (1-2): \n 1.Departamentos");

        String op = lectura.next();
        System.out.println(op);
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Mayús+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Mayús+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }

    }
}