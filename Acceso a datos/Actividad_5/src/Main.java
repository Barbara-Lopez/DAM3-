import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    final static String BDPer = "EMPLEDEP.yap";

    public static void main(String[] args) {

        // Texto inicial
        Integer op;
        do {
            Scanner lectura = new Scanner(System.in);
            System.out.println("Elija que quiere gestionar (1-3): \n 1.Departamentos \n 2.Empleados \n 3.salir");
            op = Integer.parseInt(lectura.next());

            if (op == 1) {
                // Departamentos
                System.out.println("Elija que quiere hacer con los Departamentos (1-2): \n 1.Generar departamentos automaticamente \n 2.Eliminarlos \n 3.Modificarlos");
                Integer op1 = Integer.parseInt(lectura.next());
                if (op1 == 1) {
                    generarDepartAuto();
                }
                if(op1 == 2){
                    eliminarDepart();
                }
                if(op1 == 3){
                    modificarDepart();
                }
            }
            if (op == 2) {
                // Empleados
                System.out.println("Elija que quiere hacer con los Empleados (1-2): \n 1.Generar empleados automaticamente \n 2.Eliminarlos \n 3.Modificar");
                Integer op2 = Integer.parseInt(lectura.next());
                if (op2 == 1) {
                    generarEmpleAuto();
                }
                if(op2 == 2){
                    eliminarEmple();
                }
                if(op2 == 3){
                    modificarEmple();
                }
            }
            if (op == 3) {
                System.out.println("Acaba de salir de la aplicación");
            }
        } while (op != 3);


    }

    private static void modificarEmple() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        String text=visualizarEmple(db);
        if(text==""){
            System.out.println("NO hay empleados");
        }else {
            Scanner lectura = new Scanner(System.in);
            System.out.println("Escriba el id del empleado que quiere modificar : \n" + text);
            Integer empleId = Integer.parseInt(lectura.next());
            modEmple(db,empleId);
        }
        db.close();
    }

    private static void modEmple(ObjectContainer db, Integer empleId) {
        Empleado emple = new Empleado(empleId, null,null,null);
        ObjectSet<Empleado> result = db.queryByExample(emple);
        if (result.size() == 0){
            System.out.println( "No existe el empleado con el id " + empleId);
        }else{
            while (result.hasNext()) {
                Empleado existe = result.next();

                Scanner lectura = new Scanner(System.in);
                System.out.println("¿Quiere modificar el nombre? si/no");
                String empNombre = lectura.next();
                if (empNombre.equalsIgnoreCase("si")){
                    System.out.println("Escriba el nuevo nombre del departamento");
                    empNombre = lectura.next();
                    existe.setNombre(empNombre);
                }
                System.out.println("¿Quiere modificar el puesto? si/no");
                String empPuesto = lectura.next();
                if (empPuesto.equalsIgnoreCase("si")){
                    System.out.println("Escriba escriba el nuevo puesto al que pertenece el empleado "+ existe.getNombre()+": ");
                    empPuesto = lectura.next();
                    existe.setPuesto(empPuesto);
                }
                System.out.println("¿Quiere modificar el departamentoal que pertenece? si/no");
                String empDep= lectura.next();
                if (empDep.equalsIgnoreCase("si")){
                    String text=visualizarDepart(db);
                    System.out.println("Escriba escriba el nuevo puesto al que pertenece el empleado "+ existe.getNombre()+": \n"+ text);
                    Integer empDep_id = Integer.parseInt(lectura.next());
                    Boolean existeDep = buscarDepart(db,empDep_id);
                    if(existeDep=true){
                        existe.setDepart(empDep_id);
                    }else{
                        System.out.println("El departamento con id " + empDep_id + " no existe");
                    }
                }

                db.store(existe);
                System.out.println("Id: "+existe.getId_emp()+", Nombre del empleado: "+ existe.getNombre()+ ", Puesto:" + existe.getPuesto()+", Departamento: "+existe.getDepart());
            }
        }
    }

    private static void modificarDepart() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        String text=visualizarDepart(db);
        if(text==""){
            System.out.println("NO hay departamentos");
        }else {
            Scanner lectura = new Scanner(System.in);
            System.out.println("Escriba el id del departamento que quiere modificar : \n" + text);
            Integer depId = Integer.parseInt(lectura.next());
            modDepart(db,depId);
        }
        db.close();
    }

    private static void modDepart(ObjectContainer db, Integer depId) {
        Departamento dep = new Departamento(depId, null,null);
        ObjectSet<Departamento> result = db.queryByExample(dep);
        if (result.size() == 0){
            System.out.println( "No existe el departamento con el id " + depId);
        }else{
            while (result.hasNext()) {
                Departamento existe = result.next();

                Scanner lectura = new Scanner(System.in);
                System.out.println("¿Quiere modificar el nombre? si/no");
                String depNombre = lectura.next();
                if (depNombre.equalsIgnoreCase("si")){
                    System.out.println("Escriba el nuevo nombre del departamento");
                    depNombre = lectura.next();
                    existe.setNombre(depNombre);
                }
                System.out.println("¿Quiere modificar la ciudad? si/no");
                String depCiudad = lectura.next();
                if (depCiudad.equalsIgnoreCase("si")){
                    System.out.println("Escriba escriba la nueva ciudad de departamento "+ existe.getNombre()+": ");
                    depCiudad = lectura.next();
                    existe.setCiudad(depCiudad);
                }

                db.store(existe);
                System.out.println("Id: "+existe.getId_depart()+", Nombre del departamento: "+ existe.getNombre()+ ", Ciudad:" + existe.getCiudad());
            }
        }
    }

    private static void eliminarEmple() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);
        Scanner lectura = new Scanner(System.in);
        String text=visualizarEmple(db);
        if(text==""){
            System.out.println("NO hay empleados");
        }else {
            System.out.println("Escriba el id del empleado que quiere eliminar: \n" + text);
            Integer empleId = Integer.parseInt(lectura.next());
            elimEmple(db, empleId, "emple");
        }
        db.close();
    }

    private static void eliminarDepart() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        Scanner lectura = new Scanner(System.in);
        String text=visualizarDepart(db);
        if(text==""){
            System.out.println("NO hay departamentos");
        }else {
            System.out.println("Escriba el id del departamento que quiere eliminar (los empleados tambien se eliminarán): \n" + text);
            Integer depId = Integer.parseInt(lectura.next());
            Boolean eliminado = elimDepart(db, depId);
            if (eliminado) {
                elimEmple(db, depId, "depart");
            }
        }
        db.close();
    }

    private static void elimEmple(ObjectContainer db, Integer id, String como) {
        Empleado emple;
        if (como == "depart"){
            emple= new Empleado(null,null,null,id);
        }else{
            emple= new Empleado(id,null,null,null);
        }
        ObjectSet<Empleado> result = db.queryByExample(emple);
        if (result.size() == 0){
            if(como == "depart"){
                System.out.println( "No existen empleados con el id del departamento "+ id);
            }else {
                System.out.println( "No existe el empleado con el id "+ id);
            }
        }else{
            while (result.hasNext()) {
                Empleado e = result.next();
                db.delete(e);
            }
            if(como == "depart"){
                System.out.println( "Empleados con el id del departamento "+ id + " eliminados");
            }else {
                System.out.println( "Empleado con el id "+ id + " eliminado");
            }
        }
    }

    private static boolean elimDepart(ObjectContainer db, Integer depId) {
        Departamento dep = new Departamento(depId,null, null);
        ObjectSet<Departamento> result = db.queryByExample(dep);
        if (result.size() == 0){
            System.out.println( "No existe el departamento con el id "+ depId);
            return false;
        }else{
            while (result.hasNext()) {
                Departamento d = result.next();
                db.delete(d);
            }
            System.out.println( "Departamento con el id "+ depId + " eliminado");
            return true;
        }
    }
    private static String visualizarEmple(ObjectContainer db){
        String text = "";
        Empleado emple= new Empleado(null,null,null,null);
        ObjectSet<Empleado> result = db.queryByExample(emple);
        if (result.size() == 0){
            text = "";
        } else {
            while (result.hasNext()) {
                Empleado e = result.next();
                text+="Id: "+ e.getId_emp()+", Nombre: " + e.getNombre()+", Puesto: "+ e.getPuesto()+", Departamento: "+e.getDepart()+"\n";
            }
        }
        return text;
    }
    private static String visualizarDepart(ObjectContainer db){
        String text = "";
        Departamento dep = new Departamento(null,null,null);
        ObjectSet<Departamento> result = db.queryByExample(dep);
        if (result.size() == 0){
            text = "";
        } else {
            while (result.hasNext()) {
                Departamento d = result.next();
                text+="Id: "+ d.getId_depart()+", Nombre: " + d.getNombre()+", Ciudad: "+ d.getCiudad()+"\n";
            }
        }
        return text;
    }

    private static void guardarEmple(ObjectContainer db, Empleado emple, Boolean existeEmp, Boolean existeDept) {
        if (existeEmp){
            System.out.println("El empleado con el id "+emple.getId_emp()+" YA ESTÁ añadido" );
        }else{
            if (existeDept){
                db.store(emple);
                System.out.println("El empleado con el id "+emple.getId_emp()+" A SIDO añadido" );
            }else{
                System.out.println("El empleado con el id "+emple.getId_emp()+" no se puede meter en un departamento que no existe" );
            }
        }
    }

    private static void generarEmpleAuto() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        Empleado emple = new Empleado(7369, "SÁNCHEZ", "EMPLEADO", 20);
        Boolean existeEmp =buscarEmple(db,emple.getId_emp());
        Boolean existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        emple = new Empleado(7499, "ARROYO", "VENDEDOR", 30);
        existeEmp =buscarEmple(db,emple.getId_emp());
        existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        emple = new Empleado(7521, "SALA", "VENDEDOR", 30);
        existeEmp =buscarEmple(db,emple.getId_emp());
        existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        emple = new Empleado(7566, "JIMÉNEZ", "DIRECTOR", 20);
        existeEmp =buscarEmple(db,emple.getId_emp());
        existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        emple = new Empleado(7782, "CEREZO", "DIRECTOR", 10);
        existeEmp =buscarEmple(db,emple.getId_emp());
        existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        emple = new Empleado(7839, "REY", "PRESIDENTE", 10);
        existeEmp =buscarEmple(db,emple.getId_emp());
        existeDept =buscarDepart(db, emple.getDepart());
        guardarEmple(db,emple,existeEmp,existeDept);

        db.close();
    }
    private static void guardarDepart(ObjectContainer db, Departamento dep, Boolean existe) {
        if (existe){
            System.out.println("El departamento con el id "+dep.getId_depart()+" YA ESTÁ añadido" );
        }else{
            db.store(dep);
            System.out.println("El departamento con el id "+dep.getId_depart()+" A SIDO añadido" );
        }
    }

    public static void generarDepartAuto(){
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        Departamento dep = new Departamento(10, "CONTABILIDAD", "SEVILLA");
        Boolean existe =buscarDepart(db,dep.getId_depart());
        guardarDepart(db,dep,existe);

        dep = new Departamento(20, "INVESTIGACIÓN", "MADRID");
        existe =buscarDepart(db,dep.getId_depart());
        guardarDepart(db,dep,existe);

        dep = new Departamento(30, "VENTAS", "BARCELONA");
        existe =buscarDepart(db,dep.getId_depart());
        guardarDepart(db,dep,existe);

        db.close();

    }



    public static boolean buscarDepart(ObjectContainer db, Integer idDepart){
        Departamento dep = new Departamento(idDepart,null,null);
        ObjectSet<Departamento> result = db.queryByExample(dep);
        if (result.size() == 0){
            return false;
        } else {
            return true;
        }
    }
    public static boolean buscarEmple(ObjectContainer db, Integer idEmple){
        Empleado emple = new Empleado(idEmple,null,null,null);
        ObjectSet<Empleado> result = db.queryByExample(emple);
        if (result.size() == 0){
            return false;
        } else {
            return true;
        }
    }
}

