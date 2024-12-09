<?php 

class Conexion {

    public function conectar(){
        $host= "localhost:4040";
        $database = "materias";
        $usuario = "root";
        $password = "";
        try {
            $conexion = new PDO("mysql:host=".$host.";dbname=".$database, $usuario, $password);
            //echo("se conecto");
            return $conexion;
        } catch (Exception $e) {
            die("Error en la conexion:".$e->getMessage());
        }
    }

}
//$c = new Conexion;
//$c->conectar(); 
?>