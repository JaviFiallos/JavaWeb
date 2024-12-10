<?php

require_once "./controllerPractica.php";

$controlador = new Controlador;
$opcion = $_SERVER["REQUEST_METHOD"];

switch ($opcion) {
    case 'GET':
        $controlador->obtenerConteoProductosPorBodegas();
        break;
    default:
        break;
}