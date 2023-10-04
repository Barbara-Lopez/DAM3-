// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

        // ejercicio2
        int n=0;
        compartir c=new compartir(0);
        hilo hilo1= new hilo(c,"suma");
        hilo hilo2= new hilo(c,"suma");
        hilo hilo3= new hilo(c,"resta");

        hilo1.start();
        hilo1.setName("hilo1");

        hilo2.start();
        hilo2.setName("hilo2");

        hilo3.start();
        hilo3.setName("hilo3");

        hilo1.join();
        hilo2.join();
        hilo3.join();

        hilo1.resultadoContador();/**/

        // ejercicio1
       /* Integer n[]=new Integer[5];
        compartir2 c=new compartir2(0);
        hilo1 hilo1= new hilo(c,"suma");
        hilo hilo2= new hilo(c,"suma");
        hilo hilo3= new hilo(c,"resta");

        hilo1.start();
        hilo1.setName("hilo1");

        hilo2.start();
        hilo2.setName("hilo2");

        hilo3.start();
        hilo3.setName("hilo3");

        hilo1.join();
        hilo2.join();
        hilo3.join();

        hilo1.resultadoContador();*/

        // ejercicio3


        /*compartir c=new compartir(20);
        hilo3 h1= new hilo3(c,"entrar");
        hilo3 h2= new hilo3(c,"entrar");
        hilo3 h3= new hilo3(c,"entrar");
        hilo3 h4= new hilo3(c,"entrar");
        hilo3 h5= new hilo3(c,"entrar");
        hilo3 h6= new hilo3(c,"entrar");
        hilo3 h7= new hilo3(c,"entrar");
        hilo3 h8= new hilo3(c,"entrar");
        hilo3 h9= new hilo3(c,"entrar");
        hilo3 h10= new hilo3(c,"entrar");
        hilo3 s1= new hilo3(c,"salir");
        hilo3 s2= new hilo3(c,"salir");
        hilo3 s3= new hilo3(c,"salir");
        hilo3 s4= new hilo3(c,"salir");
        hilo3 s5= new hilo3(c,"salir");
        hilo3 s6= new hilo3(c,"salir");
        hilo3 s7= new hilo3(c,"salir");
        hilo3 s8= new hilo3(c,"salir");
        hilo3 s9= new hilo3(c,"salir");
        hilo3 s10= new hilo3(c,"salir");
        hilo3 s11= new hilo3(c,"salir");
        hilo3 s12= new hilo3(c,"salir");
        hilo3 s13= new hilo3(c,"salir");
        hilo3 s14= new hilo3(c,"salir");
        hilo3 s15= new hilo3(c,"salir");




        h1.start();
        h1.setName("hilo1");

        h2.start();
        h2.setName("hilo2");

        h3.start();
        h3.setName("hilo3");

        h4.start();
        h4.setName("hilo4");

        h5.start();
        h5.setName("hilo5");

        h6.start();
        h6.setName("hilo6");

        h7.start();
        h7.setName("hilo7");

        h8.start();
        h8.setName("hilo8");

        h9.start();
        h9.setName("hilo9");

        h10.start();
        h10.setName("hilo10");

        s1.start();
        s1.setName("salir1");

        s2.start();
        s2.setName("salir2");

        s3.start();
        s3.setName("salir3");

        s4.start();
        s4.setName("salir4");

        s5.start();
        s5.setName("salir5");

        s6.start();
        s6.setName("salir6");

        s7.start();
        s7.setName("salir7");

        s8.start();
        s8.setName("salir8");

        s9.start();
        s9.setName("salir9");

        s10.start();
        s10.setName("salir10");

        s11.start();
        s11.setName("salir11");

        s12.start();
        s12.setName("salir12");

        s13.start();
        s13.setName("salir13");

        s14.start();
        s14.setName("salir14");

        s15.start();
        s15.setName("salir15");



        System.out.println("Al final del dia hay "+c.getContador()+" personas") ;
    */}
}
