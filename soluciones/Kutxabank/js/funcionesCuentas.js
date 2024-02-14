
var titular;
$(document).ready(function(){
    fLlenarLista();
    $("#lCuentas").change(fMostrarMovimientos);
    $('input[type=checkbox]').on('change',fMostrarMovimientos);

});

function fLlenarLista(){
    // Rellenar la lista desplegable con el número de las cuentas.
    titular = document.cookie;
    // quito el nombre titular=
    titular=JSON.parse(titular.substring(8,titular.length));
    let lista = $("#lCuentas");
    titular.cuentas.forEach(c=>lista.append('<option>' + c.numero + '</option>'));
}

function fMostrarMovimientos(){
    // Borro el textarea por si acaso existe
    $('#dMovimientos textarea').remove();

    // Movimientos ordenados según fecha y/o importe
    //https://cybmeta.com/comprobar-sin-un-checkbox-esta-seleccionado-con-jquery
    if ($('#chFecha').prop('checked') ) {
        if ($('#chImporte').prop('checked')) {
            fOrdenarFechaImporte();
        } else
            fOrdenarFecha();
    }
    else
        if ($('#chImporte').prop('checked')) {
            fOrdenarImporte();
        }
        else
            // No hay ninguna marcado. Por defecto por fecha.
            fOrdenarFecha();


    // Mostrar
    let datos="";
    let saldo = 0;
    let x = $('#lCuentas option:selected').index() - 1; // posición cero elige opción
    titular.cuentas[x].movimientos.forEach(m => {
        datos += m.fecha + " " + m.tipo + " " + m.importe + " \n";
        if (m.tipo == "Reintegro")
            saldo -= m.importe;
        else
            saldo += m.importe;
    });
    datos += "\n\n El saldo asciende a " + saldo;
    $('div').append('<textarea id="taMovimientos" rows="10" cols="50">Listado de movimientos' + "\n" + datos + '</textarea>');
}

function fOrdenarFecha(){
    let x = $('#lCuentas option:selected').index() - 1; // posición cero elige opción
    titular.cuentas[x].movimientos.sort(function(a,b){
        if (a.fecha > b.fecha) {
            return 1;
        }
        if (a.fecha < b.fecha) {
            return -1;
        }

        return 0;
    } )
}

function fOrdenarImporte(){
    let x = $('#lCuentas option:selected').index() - 1; // posición cero elige opción
    titular.cuentas[x].movimientos.sort(function(a,b){
        if (a.importe > b.importe) {
            return 1;
        }
        if (a.importe < b.importe) {
            return -1;
        }

        return 0;
    } )
}

function fOrdenarFechaImporte(){
    let x = $('#lCuentas option:selected').index() - 1; // posición cero elige opción
    titular.cuentas[x].movimientos.sort(function(a,b){
        if (a.fecha > b.fecha) {
            return 1;
        }
        if (a.fecha < b.fecha) {
            return -1;
        }

        // si las fechas son iguales miro importes
        if (a.importe > b.importe) {
            return 1;
        }
        if (a.importe < b.importe) {
            return -1;
        }
        return 0;
    } )
}

