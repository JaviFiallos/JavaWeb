<?php
include_once("controlador.php");

$controlador = new Controlador;
$opcion = $_SERVER["REQUEST_METHOD"];

switch ($opcion) {
    case 'GET':

        if (isset($_GET['producto'])) { //que sea like
            $nombre = $_GET['producto'];
            $controlador->buscarPorProducto($nombre);
            return;
        } else if (isset($_GET['id_bodega'])) {
            $id_bodega = $_GET['id_bodega'];
            $controlador->buscarPorBodega($id_bodega);
            return;
        } else if (isset($_GET['id_producto'])) {
            $id_producto = $_GET['id_producto'];
            $controlador->buscarPorIdProducto($id_producto);
            return;
        } else if (isset($_GET['producto']) && isset($_GET['ciudad'])) {
            $producto = $_GET['producto'];
            $ciudad = $_GET['ciudad'];

            $controlador->buscarPorProductoYCiudad($producto, $ciudad);
            return;
        } else if (isset($_GET['detalles']) && $_GET['detalles'] == 'true') {
            // Si el parámetro detalles es 'true', obtenemos los productos con detalles (bodega, ciudad, etc.)
            $controlador->obtenerProductos();
        } else if (isset($_GET['bodegas']) && $_GET['bodegas'] == 'true') {
            // Si el parámetro detalles es 'true', obtenemos los productos con detalles (bodega, ciudad, etc.)
            $controlador->obtenerBodegas();
        } else {
            // Si no se pasa ningún parámetro, solo obtenemos los productos básicos (sin detalles)
            $controlador->obtenerSoloProductos();
        }
    case 'POST':
        // Verificamos si el parámetro 'accion' está presente en la solicitud
        if (isset($_POST['accion'])) {
            $controlador->agregarProductoABodega(); // Llamamos a agregarProductoABodega()
        } else {
            // Si no se especifica una acción, asumimos que se debe insertar el producto
            $controlador->insertarProducto(); // Llamamos a insertarProducto()
        }
        return;
}
