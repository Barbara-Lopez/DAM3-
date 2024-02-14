<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ejercicios PHP</title>
</head>
<body>
<h1>Soluciones de ejercicios PHP</h1>
<h2>Tema 3: Cookies y sesiones</h2>
<h3>Ejercicio 5</h3>


<?php
// Si hay algún mensaje de error lo mostramos:
if(isset($mensaje_error)) {
    ?>
    <p style='color:red;'><?= $mensaje_error ?></p>
<?php } ?>

<form action='./ejercicio05.php' method='post'>
    <fieldset>
        <legend>Login</legend>
        <p>Introduce tu usuario y contraseña:</p>
        <p>
            <label for='usuario'>Introduce el usuario:</label>
            <input type='text' id='usuario' name='usuario' required>
        </p>
        <p>
            <label for='password'>Introduce la contraseña:</label>
            <input type='password' id='password' name='password' required>
        <p>
        <p>
            <input type='submit' value='Enviar'>
        </p>
    </fieldset>
</form>

</body>
</html>