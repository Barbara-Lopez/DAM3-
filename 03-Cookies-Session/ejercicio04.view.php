<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ejercicios PHP</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            padding: 15px;
        }
    </style>
</head>
<body>
<h1>Soluciones de ejercicios PHP</h1>
<h2>Tema 2: Formularios</h2>
<h3>Ejercicio 4</h3>
<h4>Enunciado:</h4>
<p>
    04. Crea  una  aplicación  de  control  de  asistencia para un evento.
    La aplicación tendrá que mostrar la lista de personas (el nombre de cada una) que
    han acudido al evento. Tendrá un campo de texto para escribir  el  nombre  de  la
    persona  y  así  añadir personas a la lista. La lista se almacenará en sesión, y
    existirá un botón que borre la lista completa
</p>
<h4>Solución:</h4>
<hr>

<h4>Lista de asistentes</h4>

<ul>
<?php
if(count($personas) > 0) {
    foreach ($personas as $persona) {
?>
        <li><?= $persona ?></li>
<?php
    }
}
else {
?>
    <p>No hay asistentes</p>
<?php
}
?>
</ul>
<br>
<h5>Añadir asistente</h5>
<form action="ejercicio04.php" method="get">
    <input type="text" name="persona">
    <input type="hidden" name="accion" value="insertar">
    <input type="submit" value="Añadir">
</form>
<br>
<a href="ejercicio04.php?accion=vaciar">Vaciar lista</a>
</body>
</html>