<?php
include_once("./conexionPractica.php");

class Controlador
{

    private $conexion;

    public function __construct()
    {
        $this->conexion = new Conexion();
    }

    public function obtenerConteoProductosPorBodegas()
    {
        $sql = "SELECT 
    b.nombre AS bodega, 
    p.nombre AS producto, 
        SUM(bp.cantidad) AS total
    FROM 
        bodegas b
    JOIN 
    bodegaproductos bp ON b.id = bp.bodega
    JOIN 
        productos p ON p.id = bp.producto
    GROUP BY 
        b.id, p.id
    ORDER BY 
        p.id, b.id;";
        $result = $this->conexion->conectar()->prepare($sql);
        $result->execute();
        $data = $result->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($data);
    }
}
