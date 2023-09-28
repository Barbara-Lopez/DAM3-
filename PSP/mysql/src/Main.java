import java.sql.*;

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
            Connection conexion =
            DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/prueba" ,"clase", "12345Abcde");
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
            ResultSet resul2 = sentencia2.executeQuery("select apellido,salario from empleados where salario=(select max(salario) from empleados)");
            float salario=0;
            while (resul2.next())
            {
                System.out.println(resul2.getString(1) + " "+resul2.getFloat(2));
                salario = resul2.getFloat(2);
            }
            resul2.close();// Cerrar ResultSet
            sentencia2.close();

            Statement sentencia3 =conexion.createStatement();
            int numero = sentencia3.executeUpdate("update empleados set salario = " +salario+(salario*10/100)+" where salario="+salario);


            if (numero==2){
                System.out.println("Actualización hecha ");
            }
            sentencia3.close(); /**/

            System.out.println ("Empleados con el maximo salario con el 10% aumentado:");

        }
        catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
}