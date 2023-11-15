import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.*;


public class Main {
    public static void main(String[] args) {
        Pattern pat= Pattern.compile("[0-9]{4}-[A-Z]{3}");
        Matcher mat=pat.matcher("123ZAS");
       if(mat.find()){
           System.out.println("El texto escrito coincide con el de una matricula");
       }else{
           System.out.println("El texto escrito NO coincide con el de una matricula");
       }
        if("123ADC".matches("[0-9]{4}-[A-Z]{3}")){
            System.out.println("El texto escrito coincide con el de una matricula");
        }else{
            System.out.println("El texto escrito NO coincide con el de una matricula");
        }
        Pattern pat2= Pattern.compile("[0-9]{8}-[a-zA-Z]{1}");
        Matcher mat2=pat2.matcher("12345678Ñ");
        if(mat2.find()){
            System.out.println("El texto escrito coincide con el de un DNI");
        }else{
            System.out.println("El texto escrito NO coincide con el de un DNI");
        }
        if("12345678Ñ".matches("[0-9]{8}-[a-zA-Z]{1}")){
            System.out.println("El texto escrito coincide con el de un DNI");
        }else{
            System.out.println("El texto escrito NO coincide con el de un DNI");
        }
    }
}