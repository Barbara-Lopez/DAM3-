<?php
session_start();
// Datos de acceso de los usuarios:
$usuarios = array(
    "user1" => array(
        "nombre" => 'Ane',
        "apellidos" => 'López',
        "password" => '123Abc'
    ),
    "user2" => array(
        "nombre" => 'Amaia',
        "apellidos" => 'Otsoa',
        "password" => '456Xyz'
    )
);

// Codigos de los errores. En función del error se muestra su mensaje correspondiente.
$ERROR_TYPES = [
    1 => "La contraseña no es correcta.",
    2 => "El usuario no existe."
];

/**
 * Devuelve 0 si el login es correcto, 1 si la contraseña es incorrecta o 2 si el usuario no existe.
 *
 * @param $usuario
 * @param $password
 * @param $usuarios
 * @return int
 */
function comprobarLogin($usuario, $password, $usuarios) {
    if (array_key_exists($usuario, $usuarios)) {
        if ($usuarios[$usuario]["password"] == $password) {
            $_SESSION["usuario"] = $usuario;
            return 0;
        } else {
            return 1;
        }
    } else {
        return 2;
    }
}

// Si no está logueado o nos envía la accion de salir:
if(!isset($_SESSION["login"]) || isset($_GET["accion"])) {
    $_SESSION["login"] = -1; // Utilizamos esta variable para almacenar el estado (login correcto, error)
    $_SESSION["usuario"] = "";
}

// Comprobamos el intento de login
if (isset($_POST["usuario"]) && isset($_POST["password"])) {
    $_SESSION["login"] = comprobarLogin($_POST["usuario"], $_POST["password"], $usuarios);
}

// Si el usuario ha accedido correctamente, únicamente mostramos el mensaje de bienvenida:
if ($_SESSION["login"] == 0) {
    $nombre = $usuarios[$_SESSION['usuario']]['nombre'];
    $apellidos = $usuarios[$_SESSION['usuario']]['apellidos'];
    // Cargar la vista
    require "ejercicio05.view.php";
} else {
    if($_SESSION["login"] != -1) {
        // Si ha habido un error, guardamos el mensaje de error para mostrarlo en la vista.
        $mensaje_error = $ERROR_TYPES[$_SESSION["login"]];
        // Cargar la vista
        require "ejercicio05login.view.php";
    }
    else {
        // Cargar la vista con el formulario por primera vez
        require "ejercicio05login.view.php";
    }
}