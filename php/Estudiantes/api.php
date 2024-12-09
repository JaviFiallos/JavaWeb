<?php
include_once("controlador.php");

$controlador = new Controlador;
$opcion = $_SERVER["REQUEST_METHOD"];

switch ($opcion) {
    case 'GET':
        
        if(isset($_GET['cedula'])){
            $cedula = $_GET['cedula'];
            $controlador->buscarPorCedula($cedula);
            return;
        }else if(isset($_GET['id_materia'])){
            $id_materia = $_GET['id_materia'];
            $controlador->buscarPorMateria($id_materia);
            return;
        }else if(isset($_GET['nombre'])){
            $nombre = $_GET['nombre'];
            $controlador->buscarPorNombre($nombre);
            return;
        }else{
            $controlador->obtenerEstudiantes();
            return;   
        }
    case 'POST':
        $controlador->crearEstudiante();
        return;
}

?>