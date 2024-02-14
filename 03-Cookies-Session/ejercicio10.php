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

function guardarFavorito($id){
    if(isset($_COOKIE["favoritos"])){
        $favoritos = explode(",",$_COOKIE["favoritos"]);
    } else {
        $favoritos = [];
    }
    array_push($favoritos, $id);
    setcookie("favoritos", implode(",",$favoritos), time() + 7*24*60*60);
}

function esFavorito($id){
    if(isset($_COOKIE["favoritos"])){
        $array_favoritos = explode(",",$_COOKIE["favoritos"]);
        return in_array($id, $array_favoritos);
    }
    return false;
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
        case "favorito":
            guardarFavorito($id = $_GET["idProducto"]);
            break;
        case "detalle":
            $id = $_GET["idProducto"];
            // Cargar los datos
            require_once 'ejercicio09-datos.php';
            // Dejar disponible los datos a la vista:
            $nombre = $productos[$id]["nombre"];
            $descripcion = $productos[$id]["descripción"];
            $precio = $productos[$id]["precio"];

            require "ejercicio10detalle.view.php";
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
require "ejercicio10.view.php";