<?php
// Iniciar o cargar sesión
session_start();

/*** FUNCIONES ***/
function inicializarLista(){
    if(!isset($_SESSION["listaPersonas"])){
        $_SESSION["listaPersonas"] = array();
    }
    return $_SESSION["listaPersonas"];
}

function realizarAccion($accion){
    switch ($accion) {
        case "insertar":
            if(isset($_GET["persona"])) {
                $nombrePersona = $_GET["persona"];
                inicializarLista();
                array_push($_SESSION["listaPersonas"], $nombrePersona);
            }
            break;
        case "vaciar":
            unset($_SESSION["listaPersonas"]);
            break;
    }
}

/*** Las siguientes funciones son opcionales. Es preferible crear la lista en la propia vista. ***/
function generarListaPersonas($personas){
    if(count($personas) > 0) {
        echo "<ul>";
        foreach ($personas as $persona) {
            crearElementoLista($persona);
        }
        echo "</ul>";
    } else {
        echo "<p>No hay asistentes</p>";
    }
}

function crearElementoLista($texto){
    echo "<li>{$texto}</li>";
}

/*** INICIO DE LA APLICACION ***/

if(isset($_GET["accion"])) {
    $accion = $_GET["accion"];
    realizarAccion($accion);
}
$personas = inicializarLista();
require "ejercicio04.view.php";