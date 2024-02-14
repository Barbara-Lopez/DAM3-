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
<h2>Tema 3: Cookies y sesiones</h2>
<h3>Ejercicio 7</h3>
<h4>Enunciado:</h4>
<p>
    Desarrolla una tienda online con un catálogo de productos y un carrito de la compra.
    Cada producto tendrá la siguiente información: nombre, descripción y precio (puedes
    almacenar otra información extra que te sirva de ayuda, como un ID). Se podrán adquirir
    tantas unidades de cada producto como se desee (también podrá eliminarse un producto añadido
    o una unidad del mismo).
</p>
<h4>Solución:</h4>

<hr>

<h5>Detalles del producto:</h5>
<table class="table">
    <tr>
        <td>ID</td><td><?= $id?></td>
    </tr>
    <tr>
        <td>Nombre</td><td><?= $nombre ?></td>
    </tr>
    <tr>
        <td>Descripción</td><td><?= $descripcion ?></td>
    </tr>
    <tr>
        <td>Precio</td><td><?= $precio ?></td>
    </tr>
</table>

<p>
    <a class="btn btn-primary" href="ejercicio09.php">Volver</a>
</p>



</body>
</html>