class Cuenta{
    constructor(numero)
    {
        this.numero = numero;
        this.movimientos = [];
    }
    añadirMovimientos(movimientos)
    {
        this.movimientos = movimientos;
    }
    añadirMovimiento(movimiento){
        this.movimientos.push(movimiento);
    }
}