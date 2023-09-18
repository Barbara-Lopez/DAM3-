import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;


public class Main {
    final static String BDPer = "DBPersonas.yap";
    public static void main(String[] args) {

        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);
       /* Persona p1 = new Persona("Juan", "Guadalajara",0,0.00);
        Persona p2 = new Persona("Ana", "Madrid",60,0.50);
        Persona p3 = new Persona("Luis", "Granada",0,0.50);
        Persona p4 = new Persona("Pedro", "Asturias",0,0.00);
        db.store(p1);
        db.store(p2);
        db.store(p3);
        db.store(p4);

        Persona per = new Persona(null,null, 0,null);
        ObjectSet<Persona> result = db.queryByExample(per);
        if (result.size() == 0){
            System.out.println( "No existe hay personas en la BD");
        } else {
            while (result.hasNext()) {
                Persona p = result.next();
                System.out.println("\tNombre: " + p.getNombre());
                System.out.println("\tCiudad:" + p.getCiudad());
                System.out.println("\tAltura:" + p.getAltura());
                System.out.println("\tEdad:" + p.getEdad());
            }
        }

        Persona per2 = new Persona(null,null, 0,null);
        ObjectSet<Persona> result2 =db.queryByExample(per2);
        if (result2.size() == 0){
            System.out.println( "No existe los de cero años");}
        else{
            while (result2.hasNext()) {
                Persona p = result2.next();
                db.delete(p);
            //consultar los datosSystem.out.println("Eliminado..");}
            }
        }
        System.out.println("Personas:");
        Persona per3 = new Persona(null,null, 0,null);
        ObjectSet<Persona> result3 = db.queryByExample(per3);
        if (result3.size() == 0){
            System.out.println( "No existe hay personas en la BD");
        } else {
            while (result3.hasNext()) {
                Persona p = result3.next();
                System.out.println("\tNombre: " + p.getNombre());
                System.out.println("\tCiudad:" + p.getCiudad());
                System.out.println("\tAltura:" + p.getAltura());
                System.out.println("\tEdad:" + p.getEdad());
            }
        }*/
        db.close();
        Scanner lectura = new Scanner (System.in);
        System.out.println("Ingrese su nombre: ");

        String nombre = lectura.next();
        System.out.println(nombre);
    }
}