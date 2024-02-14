<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ejercicios PHP</title>
</head>
<body>
<h1>Soluciones de ejercicios PHP</h1>
<h2>Tema 3: Cookies y sesiones</h2>
<h3>Ejercicio 1</h3>
<h4>Enunciado:</h4>
<p>
    01. Crea una página que tenga un formulario con un campo de texto en el que poder almacenar un nombre de usuario.
    La aplicación guardará el texto introducido en una cookie (sin especificar la duración) de nombre “usuario” y mostrará
    el último valor almacenado siempre que la cookie contenga algún valor.</p>
<h4>Solución:</h4>

<?php
if(isset($usuario)) {
?>
<p>Usuario almacenado: <?= $usuario ?></p>
<?php
} else {
?>
<p>No hay ningún usuario almacenado</p>
<?php
}
?>

<form action="ejercicio01.php" method="GET">
    <label for="texto">Introduce el texto que deseas almacenar: </label>
    <input type="text" name="texto"></input>
    <input type="submit" value="Guardar">
</form>


</body>
</html>