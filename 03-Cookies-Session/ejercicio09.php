<?php

// Si no hay ninguna cesta en sesión la crea
function inicializarCesta(){
    if(!isset($_SESSION["productosCesta"])){
        $_SESSION["productosCesta"] = array();
    }
}

function calcularPrecioTotal($productosComprados, $catalogoProductos){
    $precioTotal = 0;
    foreach ($productosComprados as $idProducto) {
        $precioTotal += $catalogoProductos[$idProducto]['precio'];
    }
    return $precioTotal;
}

function realizarAccion($accion){
    switch ($accion) {
        case "insertar":
            if(isset($_GET["idProducto"])) {
                $idProductoComprado = $_GET["idProducto"];
                array_push($_SESSION["productosCesta"], $idProductoComprado);
            }
            break;
        case "comprar":
            unset($_SESSION["productosCesta"]);
            break;
        case "detalle":
            $id = $_GET["idProducto"];
            // Cargar los datos
            require_once 'ejercicio09-datos.php';
            // Dejar disponible los datos a la vista:
            $nombre = $productos[$id]["nombre"];
            $descripcion = $productos[$id]["descripción"];
            $precio = $productos[$id]["precio"];

            require "ejercicio09detalle.view.php";
            die();
    }
}

/*** INICIO DE LA APLICACION ***/



// 1. Cargar/Iniciar la sesión
session_start();

// 2. Crear la cesta vacía si no está ya creada
inicializarCesta();

// 3. Realizar la accion indicada por el usuario (insertar producto o comprar los productos de la cesta
if(isset($_GET["accion"])) {
    $accion = $_GET["accion"];
    realizarAccion($accion);
}

// 4. Preparar los datos para la vista
require_once 'ejercicio09-datos.php';

if(isset($_SESSION["productosCesta"])) {
    // Preparamos los datos que necesitaremos desde la vista
    $productosComprados = $_SESSION["productosCesta"];
    $precioTotal = calcularPrecioTotal($productosComprados, $productos);
}

// 5. Cargar la vista
require "ejercicio09.view.php";