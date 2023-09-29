import java.sql.*;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try
        {
            // punto 1
            //Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecemos la conexion con la BD
            String user="barbara";
            String contrasena="12345Abcde";
            Connection conexion =
            DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/prueba" ,user, contrasena);
            // Preparamos la consulta
            Statement sentencia =conexion.createStatement();
            ResultSet resul = sentencia.executeQuery("select apellido,oficio,salario from empleados where dept_no=10");
            System.out.println ("Empleados del departamento 10: ");
            while (resul.next())
            {
                System.out.println(resul.getString(1) + " "+resul.getString(2)+ " " + resul.getFloat(3));
            }
            resul.close();// Cerrar ResultSet
            sentencia.close();// Cerrar Statementconexion.close();//Cerrar conexion

            // punto 2
           System.out.println ("Empleados con el maximo salario:");

            Statement sentencia2 =conexion.createStatement();
            ResultSet resul2 = sentencia2.executeQuery("select emp_no,apellido,salario from empleados where salario=(select max(salario) from empleados)");
            ArrayList<String> id = new ArrayList<>();
            ArrayList<String> apellido = new ArrayList<>();
            while (resul2.next())
            {
                System.out.println(resul2.getString(2) + " "+resul2.getFloat(3));
                id.add(resul2.getString(1));
                apellido.add(resul2.getString(2));
            }
            resul2.close();// Cerrar ResultSet
            sentencia2.close();
            for (int i = 0; i < id.size(); i++) {
                Statement sentencia3 =conexion.createStatement();
                int numero = sentencia3.executeUpdate("update empleados set salario = salario+(salario*10/100) where emp_no="+ id.get(i));

                if (numero==1){
                    System.out.println("Actualización de la persona "+ apellido.get(i) +" hecha ");
                }
                sentencia3.close();
            }


            System.out.println ("Empleados con el maximo salario con el 10% aumentado:");
            /* */

            Statement sentencia4 =conexion.createStatement();
            ResultSet resul4 = sentencia4.executeQuery("select apellido,salario from empleados where salario=(select max(salario) from empleados)");
            while (resul4.next())
            {
                System.out.println(resul4.getString(1) + " "+resul4.getFloat(2));
            }
            resul4.close();// Cerrar ResultSet
            sentencia4.close();
        }
        catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}