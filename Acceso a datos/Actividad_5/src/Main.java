import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Objects;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    final static String BDPer = "EMPLEDEP.yap";

    public static void main(String[] args) {

        Integer op = 0;

        do {
            try {
                Scanner lectura = new Scanner(System.in);
                System.out.println("Elija que quiere gestionar (1-3): \n 1.Departamentos \n 2.Empleados \n 3.salir");
                op = Integer.parseInt(lectura.next()) ;

                switch (op) {
                    case 1:
                        // Departamentos
                        System.out.println("Elija que quiere hacer con los Departamentos (1-4): \n 1.Generar departamentos automaticamente \n 2.Eliminarlos \n 3.Modificarlos \n 4.Añadir departamentos");
                        Integer op1 = Integer.parseInt(lectura.next()) ;
                        switch(op1) {
                            case 1:
                                generarDepartAuto();
                                break;
                            case 2:
                                eliminarDepart();
                                break;
                            case 3:
                                modificarDepart();
                                break;
                            case 4:
                                anadirDepart();
                                break;
                            default:
                                throw new Exception("Tiene que escribir del '1' al '4'");
                        }
                        break;
                    case 2:
                        // Empleados
                        System.out.println("Elija que quiere hacer con los Empleados (1-4): \n 1.Generar empleados automaticamente \n 2.Eliminarlos \n 3.Modificar \n 4.Añadir empleados");
                        Integer op2 = Integer.parseInt(lectura.next()) ;
                        switch(op2) {
                            case 1:
                                generarEmpleAuto();
                                break;
                            case 2:
                                eliminarEmple();
                                break;
                            case 3:
                                modificarEmple();
                                break;
                            case 4:
                                anadirEmple();
                                break;
                            default:
                                throw new Exception("Tiene que escribir del '1' al '4'");
                        }
                        break;
                    case 3:
                        System.out.println("Acaba de salir de la aplicación");
                        break;
                    default:
                        throw new Exception("Tiene que escribir del '1' al '3'");
                }
            } catch (NumberFormatException ex){
                System.out.println("Tienes que escribir un numero");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        } while (op!=3);
        

    }

    private static void anadirEmple() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        Integer idEmple;
        Scanner lectura = new Scanner(System.in);
        while(true){
            try{
                System.out.println("Escriba el id del nuevo empleado: ");
                idEmple = Integer.parseInt(lectura.next()) ;
                break;
            }catch (NumberFormatException e){
                System.out.println("Tienes que escribir un numero");
            }
        }
        Boolean existeEmple = buscarEmple(db,idEmple);
        if(existeEmple){
            System.out.println("Empleado con ese id ya existe");
        }else{
            System.out.println("Escriba el monbre del nuevo empleado: ");
            String nombreEmple = lectura.next() ;
            System.out.println("Escriba el puesto del nuevo empleado: ");
            String puestoEmple = lectura.next() ;
            Integer idDepEmple;
            while(true){
                try{
                    String text=visualizarDepart(db);
                    System.out.println("Escriba el id del departamento al que pertenece el nuevo empleado: \n"+text);
                    idDepEmple = Integer.parseInt(lectura.next()) ;
                    Boolean existeDept =buscarDepart(db, idDepEmple);
                    if(existeDept){
                        break;
                    }
                }catch (NumberFormatException e){
                    System.out.println("Tienes que escribir un numero");
                }
            }

            db.store(new Empleado(idEmple,nombreEmple ,puestoEmple,idDepEmple));
            System.out.println("Empleado con el nombre "+nombreEmple +" añadido" );
        }

        db.close();
    }

    private static void anadirDepart() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);
        Integer id = generarIdDepart(db);

        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el nombre del nuevo departamento: ");
        String depNombre = lectura.next();
        Boolean existeNombre = buscarNombreDepart(db,depNombre.toUpperCase());

        if(existeNombre){
            System.out.println("Departamento con ese nombre ya existe");
        }else{
            System.out.println("Escriba el nombre de la ciudad: ");
            String depCiudad = lectura.next();
            db.store(new Departamento(id, depNombre.toUpperCase(), depCiudad.toUpperCase()));
            System.out.println("Departamento con el nombre "+depNombre +" añadido" );
        }


        db.close();
    }

    private static void modificarEmple() {
        ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);

        String text=visualizarEmple(db);
        if(text==""){
            System.out.println("NO hay empleados");
        }else {
            Scanner lectura = new Scanner(System.in);
            Integer empleId;
            while(true){
                try{
                    System.out.println("Escriba el id del empleado que quiere modificar : \n" + text);
                    empleId = Integer.parseInt(lectura.next());
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Tienes que escribir un numero");
                }
            }

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
            Integer depId;
            while(true){
                try{
                    System.out.println("Escriba el id del departamento que quiere modificar : \n" + text);
                    depId = Integer.parseInt(lectura.next());
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Tienes que escribir un numero");
                }
            }
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
            Integer empleId;
            while(true){
                try{
                    System.out.println("Escriba el id del empleado que quiere eliminar: \n" + text);
                    empleId = Integer.parseInt(lectura.next());
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Tienes que escribir un numero");
                }
            }

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
            Integer depId;
            while(true){
                try{
                    System.out.println("Escriba el id del departamento que quiere eliminar (los empleados tambien se eliminarán): \n" + text);
                    depId = Integer.parseInt(lectura.next());
                    break;
                }catch (NumberFormatException e){
                    System.out.println("Tienes que escribir un numero");
                }
            }

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


    public static int generarIdDepart(ObjectContainer db){
        Departamento dep = new Departamento(null,null,null);
        ObjectSet<Departamento> result = db.queryByExample(dep);
        if (result.size() == 0){
            return 10;
        } else {
            Integer id = 0;
            while (result.hasNext()) {
                Departamento d = result.next();
                id = d.getId_depart();
            }
            return id+10;
        }
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
    public static boolean buscarNombreDepart(ObjectContainer db, String nombre){
        Departamento dep = new Departamento(null,nombre,null);
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

