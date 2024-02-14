var titulares=[]; // array con los datos de los titulares.
$(document).ready(function() {

    // acceder al localstorage. Si no hay datos los pongo.
    if ("titulares" in localStorage)
        console.log("Ya hay titulares, cuentas y movimientos");
    else
        {
            // Es la primera vez que se ejecuta en este navegador y no hay datos.
            generarDatos2();
            setTimeout(llenar,20000); // asincronia
           
        }

    // En cuanto se coloca el cursor por primera vez en la caja de la clave, salen los botones.
    $('#cclave').one('focus', fgenerarBotonesAleatorios);
    // En js addEventListener y luego removeEventListener
    // Tras validar el dni y clave se enviarán los datos al servidor.
    $('#bEntrar').click(fValidarEnviar);

});

function generarDatos(){
    
    // crear objetos y guardarlos en el array titulares
    let titular = new Titular("11111111A", "Pepe","123456");
    titulares.push(titular);
    // Primera cuenta del titular Pepe
    let cuenta = new Cuenta("11111111111111111111");
    // Movimientos de la cuenta.
    let movimiento = new Movimiento(100,new Date(2020,10,20),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(300,new Date(2020,10,11),"Reintegro");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(50,new Date(2020,10,3),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(250,new Date(2020,10,11),"Reintegro");
    cuenta.añadirMovimiento(movimiento);

    // Cuentas de un titular.
    let cuentas=[];
    cuentas.push(cuenta);

    // Segunda cuenta de Pepe
    cuenta = new Cuenta("11111111111111111112");

    movimiento = new Movimiento(102,new Date(2020,10,10),"Reintegro");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(92,new Date(2020,10,21),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(252,new Date(2020,10,11),"Reintegro");
    cuenta.añadirMovimiento(movimiento);
    cuentas.push(cuenta);

    // Añadir al titular las cuentas.
    titular.añadirCuentas(cuentas);

    // Segundo titular
    titular = new Titular("22222222A", "Nieves","123456");
    titulares.push(titular);
    // Primera cuenta de Nieves
    cuenta = new Cuenta("11111111111111111121");
    // Movimientos de la cuenta.
    movimiento = new Movimiento(100,new Date(2020,10,10),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(200,new Date(2020,10,11),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(250,new Date(2020,10,11),"Reintegro");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(103,new Date(2020,10,10),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(203,new Date(2020,10,11),"Imposición");
    cuenta.añadirMovimiento(movimiento);
    movimiento = new Movimiento(253,new Date(2020,10,11),"Reintegro");
    cuenta.añadirMovimiento(movimiento);

    cuentas=[];
    cuentas.push(cuenta);
    // Añadir al titular las cuentas.
    titular.añadirCuentas(cuentas);


}

function generarDatos1()
{
    $.get("./titulares.json", function(respuesta) {
        titulares = respuesta.titulares;
    })
    
}

function llenar(){
    // Guarda datos en el localstorage
    localStorage.setItem("titulares",JSON.stringify(titulares))
}

function generarDatos2(){
    var xhr = new XMLHttpRequest();
    xhr.overrideMimeType("application/json");
    xhr.open("GET", "./titulares.json", true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status == "200") {
            titulares = JSON.parse(this.responseText);
        }
    }
    xhr.send(null);
}

function generarDatos3()
{
    fetch('./titulares.json')
    .then((response) => response.json())
    .then((datos) => {
        titulares = datos.titulares;
    })
    .catch((err) => {
        alert("Problemas con fetch");
    });
}

function fgenerarBotonesAleatorios(){
        // array con los números que mostrarán los botones.
        // Falta algo de css
        let numeros = [];
        let x = 0;
        do {
            let nro = (Math.random() * 10);
            nro = Math.floor(nro);
            // Comprobar que no está ya
            if (numeros.filter(n => n == nro).length == 0) {
                numeros[x] = nro;
                x = x + 1;
            }
        }
        while (x < 10);

        // Crear los botones y asignarles el valor.
        numeros.forEach(n => $('div').append('<button name="button" class="bNumeros" value=' + n + '>' + n + '</button>'));
        // createElement  innerhtml

        // añadir el evento.
        $('div button').click(fanadirNumero);
}

function fanadirNumero() {
    if ($('#cclave').attr('value') != undefined) {
        if ($('#cclave').attr('value').length != 6) {
            let nuevoDigito = $(this).attr('value');
            let loQueYaEsta = $('#cclave').attr('value');
            $('#cclave').attr('value', loQueYaEsta + nuevoDigito);

        }
    }
    else
        {
            // this representa el botón sobre el que se ha producido el evento.
            let nuevoDigito = $(this).attr('value');
            $('#cclave').attr('value', nuevoDigito);
        }

}

function fValidarEnviar(){
    // Validaciones varias y sino hay errores envío de datos.
    try {

        // attr != prop
        let vdni = $('#cdni').prop('value');
        alert(vdni)

        //let vdni = document.getElementById("cdni").value;
        if (vdni == "")
            throw "La dni es un dato obligatorio";

        let expreg = new RegExp('^[0-9]{8}[A-Za-z]{1}$');

        if (!expreg.test(vdni))
            throw "El dni no tiene el formato correcto";

        /*let letras='TRWAGMYFPDXBNJZSQVHLCKET';
        let numero = parseInt(vdni.substring(0,8));
        if (vdni.charAt(8) != letras.charAt(numero%23))
            throw " El dni no es correcto";*/

        let vclave = $('#cclave').attr('value');
        if (vclave === undefined || vclave.length != 6) {
            $('#cclave').attr('value', "");
            throw "La clave debe estar formada por seis dígitos.";
        }


        // si todo va bien envio los datos al servidor
        /* let objeto=new Titular(vdni," ",vclave);
         $.ajax(
         {
             url: 'buscar.php',
             data: JSON.stringify(objeto),
             type: 'GET',
             dataType: 'json ',
             success : function(response) {
                 alert("Bienvenidx")
             // la respuesta podría ser el objeto con las cuentas.
             }
         })*/

        // A falta de servidor, busco en el localStorage
        let datos = JSON.parse(localStorage.getItem("titulares"));
        let titular = datos.filter(d => d.dni == vdni && d.clave == vclave);
        if (titular.length == 0) {
            document.getElementById("cdni").value="";
            $('#cclave').attr('value',"");
            throw "No hay ningún titular con ese dni o clave";
        }
        else {
            dato = JSON.stringify(titular[0]);
            // redundante??
            document.cookie = "titular=" + dato + "; max-age=3600; path=/";
            window.location.href = "cuentas.html";
        }

    }
    catch(e)
    {
        alert(e);

    }

}