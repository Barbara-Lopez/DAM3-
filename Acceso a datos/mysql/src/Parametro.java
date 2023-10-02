import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Parametro {
    public void modSalario(Connection c) throws SQLException {
        Statement sentencia =c.createStatement();
        String apellido = null;
        ResultSet resul = sentencia.executeQuery("select emp_no,apellido,salario from empleados where apellido='"+apellido+"'");
        System.out.println ("Empleado:");
        int id=0;
        while (resul.next())
        {
            System.out.println(resul.getString(2) + " "+resul.getFloat(3));
            id = resul.getInt(1);
        }
        Statement sentencia2 =c.createStatement();
        int numero = sentencia2.executeUpdate("update empleados set salario = salario+(salario*10/100) where emp_no="+ id);

        if (numero==1){
            System.out.println("Actualización de la persona "+ apellido +" hecha ");
        }
        sentencia2.close();
        resul.close();// Cerrar ResultSet
        sentencia.close();// Cerrar Statementconexion.close();//Cerrar conexion
    }
}
