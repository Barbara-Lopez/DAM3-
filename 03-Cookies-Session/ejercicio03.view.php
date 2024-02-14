<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ejercicios PHP</title>
</head>
<body>
<h1>Soluciones de ejercicios PHP</h1>
<h2>Tema 3: Cookies y sesiones</h2>
<h3>Ejercicio 3</h3>
<h4>Enunciado:</h4>
<p>
    03. Crea una página que tenga un 'select' con dos opciones: “Euskera” y “Castellano”. La aplicación guardará el valor indicado en una cookie de nombre “idioma” (tendrá 1 minuto de duración) y mostrará el último valor almacenado siempre que la cookie contenga algún valor.</p>
<h4>Solución:</h4>

<?php
if(isset($idioma)) {
?>
    <p>Idioma: <?= $idioma ?></p>
<?php
} else {
?>
    <p>No hay ningún idioma almacenado.</p>
<?php
}
?>

<form action="ejercicio03.php" method="GET">
    <P>
        <label for="texto">Elige tu idioma </label>
        <select name="idioma">
            <option value="Euskera">Euskera</option>
            <option value="Castellano">Castellano</option>
        </select>
    </P>
    <input type="submit" value="Guardar">
</form>

</body>
</html>