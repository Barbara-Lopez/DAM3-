import Coches.Bajar;
import Coches.Puente;
import Coches.Subir;
import cuenta.Ahorrar;
import cuenta.cuenta;
import cuenta.Gastar;
import museo.Museo;
import museo.Personas;
import museo.Temperatura;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        /*jerseis.cestos c=new jerseis.cestos(0,0);
        jerseis.mangas m1=new jerseis.mangas(c);
        jerseis.mangas m2=new jerseis.mangas(c);
        jerseis.mangas m3=new jerseis.mangas(c);
        jerseis.mangas m4=new jerseis.mangas(c);
        jerseis.cuerpos c1=new jerseis.cuerpos(c);
        jerseis.cuerpos c2=new jerseis.cuerpos(c);
        jerseis.jerseis j=new jerseis.jerseis(c);
        jerseis.jerseis j1=new jerseis.jerseis(c);

        m1.start();
        m2.start();
        m3.start();
        m4.start();
        c1.start();
        c2.start();
        j.start();
        j.setName("jersei 1");
        j1.start();
        j1.setName("jersei 2");*/

        /*cuenta c = new cuenta(250);
        Ahorrar a1= new Ahorrar(c);
        Ahorrar a2 = new Ahorrar(c);
        Gastar g1=new Gastar(c);
        Gastar g2=new Gastar(c);

        a1.start();
        a2.start();
        g1.start();
        g2.start();

        a1.join();
        a2.join();
        g1.join();
        g2.join();

        c.saldoFinal();*/

        /*Personas personas[]=new Personas[50];
        Museo m=new Museo(20,0);
        for (int i = 0; i<50; i++){
            personas[i] = new Personas(m);
        }
        Temperatura t1=new Temperatura(m);
        Temperatura t2=new Temperatura(m);
        Temperatura t3=new Temperatura(m);

        for (int i = 0; i< 50; i++){
            personas[i].start();
        }
        t1.start();
        t2.start();
        t3.start();*/
        Puente p = new Puente(0,0);
        Subir s[]=new Subir[10];
        for (int i = 0; i < 10; i++) {
            s[i]= new Subir(p);
            s[i].start();
            s[i].setName("coche"+i);
        }

        Bajar b=new Bajar(p);
        b.setName("bajar");
        b.start();

    }
}