class Titular{
    constructor(dni,nombre,clave)
    {
        this.dni = dni;
        this.nombre = nombre;
        this.clave = clave; // Guardar cifrada https://yosoy.dev/cifrar-contrasenas-en-formulario-de-html/
        this.cuentas=[];
    }
    añadirCuentas(cuentas)
    {
        this.cuentas = cuentas;
    }
    añadirCuenta(cuenta)
    {
        this.cuentas.push(cuenta);
    }
}