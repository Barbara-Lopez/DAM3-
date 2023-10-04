// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        cestos c=new cestos(0,0);
        mangas m1=new mangas(c);
        mangas m2=new mangas(c);
        mangas m3=new mangas(c);
        mangas m4=new mangas(c);
        cuerpos c1=new cuerpos(c);
        cuerpos c2=new cuerpos(c);
        jerseis j=new jerseis(c);
        jerseis j1=new jerseis(c);

        m1.start();
        m2.start();
        m3.start();
        m4.start();
        c1.start();
        c2.start();
        j.start();
        j.setName("jersei 1");
        j1.start();
        j1.setName("jersei 2");
    }
}